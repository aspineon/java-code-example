package database;

import java.sql.Connection;
import java.sql.SQLException;

public class FirebirdConnectionAware implements IConnectionAware{
	private String pathToServer;
	private String pathToDatabase;
	private String userName;
	private String password;
	
	/** ������, ������� ���������� Connection � ���� ������ Firebird
	 * @param pathToServer - ���� � ������� 
	 * @param pathToDatabase - ���� � ���� ������ 
	 * @param userName - ��� ������������ 
	 * @param password - ������
	 */
	public FirebirdConnectionAware(String pathToServer, String pathToDatabase, String userName, String password){
		this.pathToServer=pathToServer;
		this.pathToDatabase=pathToDatabase;
		this.userName=userName;
		this.password=password;
	}
	
	@Override
	public Connection getConnection(){
		return this.getConnectionToDatabase();
	}
	
	private Connection getConnectionToDatabase() {
        java.sql.Connection connection=null;

        String driverName = "org.firebirdsql.jdbc.FBDriver";
        String database_protocol="jdbc:firebirdsql://";
        String database_dialect="?sql_dialect=3";
        String database_server=null;
        String database_port="3050";
        //String databaseURL = "jdbc:firebirdsql://localhost:3050/d:/work/sadik/sadik.gdb?sql_dialect=3";
        if((pathToServer=="")||(pathToServer==null)){
            database_server="localhost";
        }else{
            database_server=pathToServer;
        }
        String databaseURL=database_protocol+database_server+":"+database_port+"/"+this.pathToDatabase+database_dialect;
        
        try{
            //System.out.println("������� ��������� �������");
            Class.forName(driverName);
            //System.out.println("������� �����������="+databaseURL);
            connection=java.sql.DriverManager.getConnection(databaseURL,userName,password);
            connection.setAutoCommit(false);
        }catch(SQLException sqlexception){
            System.err.println("�� ������� ������������ � ���� ������");
        }catch(ClassNotFoundException classnotfoundexception){
            System.err.println("�� ������ �����");
        }
        return connection;
	}

}
