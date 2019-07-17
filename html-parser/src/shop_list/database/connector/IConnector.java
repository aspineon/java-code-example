package shop_list.database.connector;
import java.sql.Connection;

public interface IConnector {
	/** �������� ���������� �� Pool */
	public Connection getConnection();
	/** �������� ���������� �� Pool, �� UserName � �� Password*/
	public Connection getConnection(String user, String password);
}
