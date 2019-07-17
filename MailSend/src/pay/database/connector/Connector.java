package pay.database.connector;
import org.hibernate.Session;

import pay.database.oracle.OracleConnection;
//import pay.database.wrap.*;
import java.sql.Connection;

/** �����, ������� ���������� ���������� � ����� ������, ���������� Hibernate � Connection */
public class Connector {
	private static IConnector connector=null;
	private static HibernateConnection hibernateConnection=null;
	
	//private static String userName="SYSDBA";
	//private static String password="masterkey";
	
	// INFO - CONNECTOR ����� ������������� ���� ������� ��� ��������� ����������� �������� ���� ������ �� ������� ���������
	private static Class<?>[] classOfDatabase=new Class[]{};
	
	static {
		try{
			//String dataBaseName="bonpay";
			//connector=new FirebirdConnection(null,dataBaseName,userName,password,20);
			connector=new OracleConnection(null,null,null,20);
			hibernateConnection=new HibernateConnection(connector,
													    "org.hibernate.dialect.OracleDialect",//"org.hibernate.dialect.FirebirdDialect",
													    classOfDatabase);
			
		}catch(Exception ex){
			System.err.println("Connector is not created ");
		}
	}
	
	/** �������� Hibernate Session */
	public static Session openSession(Connection connection){
		synchronized(hibernateConnection){
			return hibernateConnection.openSession(connection);
		}
	}
	
	/** �������� ���������� � ����� ������ */
	public static Connection getConnection(){
		synchronized(connector){
			return connector.getConnection();
		}
	}
	
	/** ������� Hibernate ������ */
/*	public static void closeSession(Session session){
		try{
			session.disconnect();
			session.close();
		}catch(Exception ex){
			System.out.println("Connector#closeSession Exception: "+ex.getMessage());
		};
	}
*/	
	public static void closeSession(Session session, Connection connection){
		try{
			session.disconnect();
		}catch(Exception ex){
			System.out.println("Connector#disconnectSession Exception: "+ex.getMessage());
		};
		try{
			session.close();
		}catch(Exception ex){
			System.out.println("Connector#closeSession Exception: "+ex.getMessage());
		};
		try{
			connection.close();
		}catch(Exception ex){
			System.out.println("Connector#closeConnection Exception: "+ex.getMessage());
		};
	}

	/** �������� ConnectWrap ������� ��� ���������� � ����� ������ */
	public static ConnectWrap getConnectWrap(){
		Connection connection=getConnection();
		Session session=openSession(connection);
		return new ConnectWrap(connection,session);
	}
}
