package connector;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Set;

public class SessionPoolConnector {

	private void outError(Object information){
		System.out.print("SessionPoolConnector");
		System.out.print("  ERROR  ");
		System.out.println(information);
	}
	
	/** pool of Connection for user's [User Name, HibernateOracleConnect] (1:1) */
	private HashMap <String,PoolConnect> connectionUserPool=new HashMap<String,PoolConnect>();
	private UserSessionManager userSession=new UserSessionManager();
	private Class<? extends PoolConnect> connectorClass;
	private Integer poolSize=new Integer(10);
	
	/** ������, ������� ������� Pool ���������� �� ���������� �������, � ���������� Connection �� SessionId
	 * @param ���������� �����, ������� ����� ����������� ���������� ���������� 
	 * (String UserName, String Password, Integer poolSize)
	 * 
	 * 
	 * */
	public SessionPoolConnector(Class<? extends PoolConnect> connectorClass, Integer poolSize){
		this.connectorClass=connectorClass;
		if(poolSize!=null){
			this.poolSize=poolSize;
		}
	}
	

	/** �������� Connection �� ��������� ��������� ������ 
	 * @param userName ��� ������������
	 * @param password ������
	 * @param sessionId ���������� ����� ������ 
	 * @return Connection 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * */
	public Connection getConnection(String userName, 
								    String password, 
									String sessionId) {
		Connection returnValue;
		// �������� ������� ������ - "��� ������������"-"POOL"
		if(connectionUserPool.containsKey(userName)==true){
			// Pool ������ �� ������� ������������ 
			if(connectionUserPool.get(userName).isPasswordEquals(password)){
				returnValue=connectionUserPool.get(userName).getConnection();
				this.userSession.put(userName, sessionId);
			}else{
				// Password is incorrect
				returnValue=null;
			}
		}else{
			// Pool �� ������ �� ������� �������
			try{
				// ������� ������ � �������� ��� � ���������
				Constructor<? extends PoolConnect> constructor=this.connectorClass.getConstructor(String.class,String.class,Integer.class);
				PoolConnect newConnection=constructor.newInstance(userName, password, this.poolSize);
				// �������� Connection �� ���������� Pool
				returnValue=newConnection.getConnection();
				if(returnValue==null){
					throw new Exception();
				}
				this.connectionUserPool.put(userName, newConnection);
				this.userSession.put(userName, sessionId);
			}catch(Exception ex){
				returnValue=null;
				System.out.println("SessionPoolConnector Exception: "+ex.getMessage());
			}
		}
		return returnValue;
	}

	/** ������� Connection � POOL */
	public void closeConnection(Connection connection){
		try{
			connection.close();
		}catch(Exception ex){
			outError("closeConnection dropped:"+ex.getMessage());
		}
	}

	/** �������� Connection �� POOL �� ���������� ����������� �������������� 
	 * @param sessionId - ���������� ����� ������, �� �������� ����� �������� Connection
	 * */
	public Connection getConnection(String sessionId){
		String user=this.userSession.getUser(sessionId);
		
		if(user==null){
			return null;
		}else{
			return this.connectionUserPool.get(user).getConnection();
		}
	}

	public void removeSessionId(String sessionId){
		String user=this.userSession.getUser(sessionId);
		this.userSession.clear(sessionId);
		if(this.userSession.hasSessionsByUser(user)==false){
			try{
				this.connectionUserPool.get(user).reset();
			}catch(Exception ex){
				// user:Pool is not finded
			}
		}
	}
	
	/** ������� �� ���������� ������������ ��� ���������� */
	public boolean removeSessionByUser(String userName){
		boolean returnValue=true;
		try{
			this.connectionUserPool.get(userName).reset();
		}catch(Exception ex){
			System.err.println("SessionPoolConnector#removeSessionByUser UserName:"+userName+"  close Exception:"+ex.getMessage());
			returnValue=false;
		}
		return returnValue;
	}
	
	/** �������� ���-�� �������������, �� ������� ������ PoolConnection */
	public int getUserPoolCount(){
		return this.connectionUserPool.size();
	}
	
	/** �������� ����� �������������, �� ������� ������� ���� */
	public Set<String> getUsersIntoPool(){
		return this.connectionUserPool.keySet();
	}
	
	/** �������� �� ���������� ������������ ���-�� �������� ���������� */
	public int getConnectionCountByUser(String user){
		return this.connectionUserPool.get(user).getConnectionCount();
	}
	
	public void printConnection(PrintStream out){
		if(out==null){
			out=System.out;
		}
		out.println("Connection:");
		Set<String> users=getUsersIntoPool();
		for(String user:users){
			out.println("User:"+user+"    ConnectionCount:"+getConnectionCountByUser(user));
		}
		out.println("-----------");
	}
}
