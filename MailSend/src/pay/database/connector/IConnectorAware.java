package pay.database.connector;

import java.sql.Connection;

/** ���������, �������� ������������ � ����� ������ */
public interface IConnectorAware {
	/** �������� ���������� � ����� ������ */
	public Connection getConnection();
}
