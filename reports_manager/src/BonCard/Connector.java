package BonCard;

import java.sql.*;

import org.apache.log4j.*;

/** ����� ��� �������� � ��������� ���������� � �����
 * ������������ �� ����� ������� 
 * */
public class Connector {
	private static String field_path="jdbc:oracle:thin:@192.168.15.254:1521:demo";
	private static String field_login="bc_reports";
	private static String field_password="bc_reports";
	private java.sql.Connection field_connection;
	private static Logger field_logger=Logger.getLogger("BonCard.Connector");
	static{
		// configuration logger
		org.apache.log4j.BasicConfigurator.configure();
	}
	/** ���������� �� ���������� �� ���������*/
	public Connector(){
		this(field_path,
			 field_login,
			 field_password);
	}
	/**
	 * ���������� � ������� � �������
	 * @param login �����
	 * @param password ������
	 */
	public Connector(String login, String password){
		this(field_path,login,password);
	}
	/**
	 * ���������� � ����� ������, ���������  
	 * @param path ������ URL
	 * @param login �����
	 * @param password ������
	 */
	public Connector(String path, String login, String password){
		
		this.field_logger.setLevel(Level.DEBUG);
		try{
			field_logger.debug("try connection with database");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.field_connection=DriverManager.getConnection(path,login,password);
			field_logger.debug("connection successful");
		}catch(SQLException ex){
			SQLException temp_exception=null;
			int exception_counter=0;
			field_logger.error("Connection SQLException:");
			while((temp_exception=ex.getNextException())!=null){
				field_logger.error((++exception_counter)+":"+temp_exception.getMessage());
			}
		}catch(Exception e){
			field_logger.error("Connection Exception:"+e.getMessage());
		}
		this.field_logger.debug("Constructor END");
	}
	
	/**
	 * get connection to DataBase
	 */
	public Connection getConnection(){
		return this.field_connection;
	}
}
