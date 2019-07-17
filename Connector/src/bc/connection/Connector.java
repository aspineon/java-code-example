package bc.connection;

import java.io.PrintStream;
import java.sql.*;


public class Connector {
	private static SessionPoolConnector poolConnector;
	static{
		// TODO set realize class here
		//poolConnector=new SessionPoolConnector(FirebirdConnect.class,new Integer(10));
		poolConnector=new SessionPoolConnector(HibernateOracleConnect.class,new Integer(10));
	}
	/** �������� Connection �� POOL �� ���������� ����������� �������������� 
	 * @param sessionId - ���������� ����� ������, �� �������� ����� �������� Connection
	 * */
	public synchronized static Connection getConnection(String sessionId){
		return poolConnector.getConnection(sessionId);
	}
	

	/** �������� Connection �� ��������� ��������� ������ 
	 * @param userName ��� ������������
	 * @param password ������
	 * @param sessionId ���������� ����� ������ 
	 * @return Connection 
	 * */
	public synchronized static Connection getConnection(String userName, 
										   String password, 
										   String sessionId){
		return poolConnector.getConnection(userName, 
										   password, 
										   sessionId);
	}

	/** ������� Connection � POOL */
	public synchronized static void closeConnection(Connection connection){
		poolConnector.closeConnection(connection);
	}
	
	/** ������� ����� ������ �� ������ sessionId=UserName*/
	public static void removeSessionId(String sessionId){
		poolConnector.removeSessionId(sessionId);
	}
	
	/** ������� � PrintStream ��������� ���� ���������� */
	public static void printAllConnectionCount(PrintStream out){
		poolConnector.printConnection(out);
	}
	
	/** ������� �� ���������� ������������ ��� ���������� � �����*/
	public static boolean dropUserConnection(String userName){
		return poolConnector.removeSessionByUser(userName);
	}
}
