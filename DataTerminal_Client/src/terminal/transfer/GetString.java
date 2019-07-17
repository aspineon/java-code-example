package terminal.transfer;

/** ������ ��������� ������������ ��� ��������� �������� ������ �� ������� � ���� ������ */
public interface GetString {
	/**
	 * ����� �� ������� ������� 
	 * @param taskName ��� ������, ������� �������� ������
	 * @param value ��������, ������� ������ ���� �������� ������ �������
	 */
	public void getString(String taskName, String value);

	/** 
	 * �� ������� ����� �� �������, ���� ������ ������ ��������� Task.STATE_ERROR
	 * @param taskName ��� ������, ������� �������� ������
	 * @param value ��������, ������� ������ ���� �������� ������ �������
	 */
	public void getString(String taskName);
	
}
