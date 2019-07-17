package pay.database.connector;

import java.sql.Connection;

import org.hibernate.Session;

/** ������� ��� ���������� � ����� ������ */
public class ConnectWrap {
	 private Connection connection;
	 private Session session;
	 
	 /** ������� ��� ���������� � ����� ������ */
	 public ConnectWrap(Connection connection, Session session){
		 this.connection=connection;
		 this.session=session;
	 }

	 /** ������� ���������� � ����� ������ */
	 public void close(){
		 try{
			 session.close();
		 }catch(Exception ex){};
		 try{
			 connection.close();
		 }catch(Exception ex){};
	 }
	 
	/** �������� Connection */
	public Connection getConnection() {
		return connection;
	}

	/** @return �������� Session */
	public Session getSession() {
		return session;
	}

}
