package database;

import java.sql.Connection;
import java.sql.SQLException;

import org.hsqldb.jdbc.jdbcDataSource;

public class HsqldbConnection extends Connector{
	/** ���������� ��� ���� ������ */
	private String name;
	/** ���������� � ����� ������ HsqlDb*/
	private jdbcDataSource dataSource;
	/** current connection */
	private Connection connection;
	
	/** ������� ���������� � ����� ������ �� ��������� HSQLDB ���� ������ 
	 * @param ���������� ��� ��� ���� ������ 
	 * @throws SQLException ���� �� ������� ������� ���������� � ����� ������ �� ���������� ����  
	 * */
	public HsqldbConnection(String databaseName) throws SQLException {
		this.name=databaseName;
        dataSource = new jdbcDataSource();

        dataSource.setDatabase("jdbc:hsqldb:" + this.name);

	}
	
	
	@Override
	public Connection getConnection() {
		try{
			if((this.connection==null)||(this.connection.isClosed())){
				this.connection=this.dataSource.getConnection("sa","");
			}
		}catch(Exception ex){
			return this.connection;
		}
		return connection;
	}

}
