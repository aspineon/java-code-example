package database.connector;

import java.sql.DriverManager;

abstract class Connector {
	/** �������� ��� ������-�������� */
	protected abstract String getFullClassName();
	/** �������� ������ URL � �����  */
	protected abstract String getUrl();
	/** �������� Login*/
	protected abstract String getLogin();
	/** �������� Password*/
	protected abstract String getPassword();
	
	private java.sql.Connection currentConnection=null;
	
	/** �������� ���������� � ����� ������, �� ��������� ���������������� � �������� �������
	 * ��������� ����� ������ � ������� URL
	 * */
	public java.sql.Connection getConnection() throws Exception {
		if((currentConnection==null)||(currentConnection.isClosed())){
			Class.forName(this.getFullClassName());
			currentConnection=DriverManager.getConnection(this.getUrl(),this.getLogin(),this.getPassword());
			currentConnection.setAutoCommit(false);
		}
		return currentConnection;
	}
}
