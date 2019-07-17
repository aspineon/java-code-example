package order_class;

import java.sql.Connection;

public interface Model {
	/** ���� �� ��������� � ������ 
	 * @return true - ���� ���� ����������� �����-���� ����������� � ������� 
	 * */
	public boolean isChanged();
	/** ��������� �������� ������ �� ��������� ������ - */
	public void resetDataIntoDataBase(Connection connection);
	/** �������������� �������� ������ */
	public void load(Connection connection) throws Exception;
	/** ���������� ������ */
	public void save(Connection connection) throws Exception;
	/** �������� ����� ���-�� ���� ��������� */
	public int getSize();
	/** �������� ��������� ������� �� ������� */
	public Element getElement(int index);
	/** ����������� ������� � ����������� � ������� (��������) ������� */
	public void moveUpElement(int index);
	/** ����������� ������� � ����������� � �������� (��������) ������� */
	public void moveDownElement(int index);
	/** ����� ������ �������� �� ��������� ���������� */
	public int getIndex(Element element);
	/** ����������� ������ �� ���� ������ �����*/
	public void moveUpSection(Element element);
	/** ����������� ������ �� ���� ������ ����  */
	public void moveDownSection(Element element);
}
