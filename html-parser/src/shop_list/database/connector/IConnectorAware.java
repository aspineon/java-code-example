package shop_list.database.connector;

/** �������, ����������� ������ ��������� ������� ����������� � ����� ������*/
public interface IConnectorAware {
	/** �������� ���������� � ����� ������ */
	public ConnectWrap getConnector();
}
