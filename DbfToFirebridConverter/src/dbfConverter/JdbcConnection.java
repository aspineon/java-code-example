package dbfConverter;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.File;
/** �����, ������� ���������� ��������� � ����� ������ �� ��������� ���������� */
public class JdbcConnection {
	private File file;
	
	public JdbcConnection(File file){
		this.file=file;
	}
	
	/** ���������� ���������� � ����� ������ �� ��������� Connection 
	 * @return Connection ���� null, ���� �� ���������� �������� Connection
	 * */
	public Connection getConnection(File file){
		if(this.getExtension(file).equalsIgnoreCase("gdb")||this.getExtension(file).equalsIgnoreCase("fdb")){
			String path=file.getAbsolutePath().replace('\\', '/');
			return this.getConnectionToFirebird("", path, 0, "SYSDBA", "masterkey");
		}else{
			return null;
		}
		
	}
	
	/** ���������� ���������� � ����� ������ �� ��������� Connection */
	public Connection getConnection(){
		return getConnection(this.file);
	}
	
	
    private Connection getConnectionToFirebird(
    		String path_to_server, 
    		String path_to_database, 
    		Integer port,
			String user, String password) {
		// java.sql.Driver driver=null;
		java.sql.Connection connection = null;

		String driverName = "org.firebirdsql.jdbc.FBDriver";
		String database_protocol = "jdbc:firebirdsql://";
		String database_dialect = "?sql_dialect=3";
		String database_server = null;
		String database_port = null;
		// String databaseURL =
		// "jdbc:firebirdsql://localhost:3050/d:/work/sadik/sadik.gdb?sql_dialect=3"
		// ;
		if ((path_to_server == "") || (path_to_server == null)) {
			database_server = "localhost";
		} else {
			database_server = path_to_server;
		}
		if (port == 0) {
			database_port = "3050";
		} else {
			database_port = Integer.toString(port);
		}
		String databaseURL = database_protocol + database_server + ":"
				+ database_port + "/" + path_to_database + database_dialect;

		try {
			// System.out.println("������� ��������� �������");
			Class.forName(driverName);
			// System.out.println("������� �����������="+databaseURL);
			connection = java.sql.DriverManager.getConnection(databaseURL,
					user, password);
		} catch (SQLException sqlexception) {
			System.out.println("�� ������� ������������ � ���� ������");
		} catch (ClassNotFoundException classnotfoundexception) {
			System.out.println("�� ������ �����");
		}
		return connection;
	}
	
    
	/** �������� ���������� ����� */
	private String getExtension(File file){
		String fileName=file.getName();
		int dotPosition=fileName.lastIndexOf(".");
		if(dotPosition>0){
			return fileName.substring(dotPosition+1);
		}else{
			return "";
		}
	}
    
}
