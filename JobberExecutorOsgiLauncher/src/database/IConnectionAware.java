package database;

import java.sql.Connection;

/** ������ Connection � ���� ������ */
public interface IConnectionAware {
	/** ��������� ���������� � ����� ������ */
	public Connection getConnection();
}
