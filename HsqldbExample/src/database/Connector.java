package database;
import java.sql.Connection;

public abstract class Connector {

	/** �������� ���������� � ����� ������ */
	public abstract Connection getConnection();
}
