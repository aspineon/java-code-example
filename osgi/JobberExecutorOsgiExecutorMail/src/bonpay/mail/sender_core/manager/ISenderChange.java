package bonpay.mail.sender_core.manager;

/** ���������, ������� �������� ����������� ������ �� ��������� ����������� �������� �������� */
public interface ISenderChange {
	/** ���������� � ������������� ��������� � ���������� Sender */
	public void notifySenderChange(SenderIdentifier senderIdentifier);
	/** ���������� � �������� Sender */
	public void notifySenderCreate(SenderIdentifier senderIdentifier);
	/** ���������� �� �������� Sender */
	public void notifySenderRemove(SenderIdentifier senderIdentifier);
}
