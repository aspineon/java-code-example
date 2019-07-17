package bonpay.mail.sender_core.sender;

import bonpay.mail.sender_core.manager.SenderIdentifier;

/** ��������� ���������� � ������������� ���������� ��������  */
public interface INotifySettingsChange {
	/** ���������� ���� ������������ {@link SenderThread} � ������������� ���������� ����� ���������� */
	public void notifySenderSettingsChange();
	/** ���������� ����������� ����������� ({@link SenderIdentifer}) {@link SenderThread} � ������������� ���������� ����� ���������� */ 
	public void notifySenderSettingsChange(SenderIdentifier senderIdentifier);
}
