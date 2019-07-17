package connector;

/** ����������, ������� �������� ������ ��� �������� � ��������� ����� ������������ �� ������*/
public interface IUserSession {
	/** ��������� ������ "User Name" - "Session Id"
	 * @return true - ������ ������� ��������� 
	 * */
	public boolean put(String user, String sessionId);
	
	/** �������� "User Name" �� "Session Id" 
	 * @return ������� User Name ��� null, ���� �� ������ ����� ����� ������ � ������ "������������"-"��� ������" 
	 * */
	public String getUser(String sessionId);
	
	/** ������� ������ ������ �� ������ "User" - "SessionId"
	 * @return ������� true ���� ������ ����� ��� ������ � ��������
	 * */
	public boolean clear(String sessionId);
	
	/** 
	 * ��������� ���������������� �� �� ������������ SessionId
	 * @return true - ���� �� ������� ������������ ���� ������ HttpSession, <br> 
	 * false - ��� �� ������� ������������ �� ����� ������
	 * 			 
	 */
	public boolean hasSessionsByUser(String user);
}
