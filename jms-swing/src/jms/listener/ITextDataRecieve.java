package jms.listener;

/** ��������� ������������� ��������� ������ */
public interface ITextDataRecieve {
	
	/**  �������� ��������� ��������� ��������� �� �������
	 * @param sender - ����������� ���������  
	 * @param remoteTextMessage - ��������� ��������� �� ���������� ������� 
	 * */
	public void recieveTextData(String sender, String remoteTextMessage);
}
