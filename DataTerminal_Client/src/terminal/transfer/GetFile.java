package terminal.transfer;

/** ���������, ������� �������� �� �������������� ���������� ������ �� ������� � ����� */
public interface GetFile {
	/** 
	 * ��������� ������ � ���� byte[] � �� �������������� � ����� �� �����
	 * @param taskName - ��� ������, ������� ���������� ������ �������
	 * @param pathName - ���� � ��������, � ������� ������ ��������� ����������
	 * @param fileName - ��� �����, � ������� ������ ����������� ������
	 * @param data - ������ � ���� ������� ����
	 * */
	public void getFile(String taskName, 
						String pathName, 
						String fileName, 
						byte[] data);

	/** 
	 * ��������� ������ � ���� byte[] � �� �������������� � ����� �� �����
	 * @param taskName - ��� ������, ������� ���������� ������ �������
	 * @param fileName - ��� �����, � ������� ������ ����������� ������
	 * @param data - ������ � ���� ������� ����
	 * */
	public void getFile(String taskName, String fileName, byte[] data);
}
