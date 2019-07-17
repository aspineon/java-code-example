package bonpay.mail.sender_core.sender;

import bonpay.mail.sender_core.manager.SenderIdentifier;

/** ���������� � ������������� �������� ������ ������ */
public interface INotifyNewLetter {
	/** ���������� ���� �������� � ������������� ��������� ������ ������ �� ���� ������ */
	public void notifyAboutNewLetter();
	/** ���������� ������������� ������� ({@link SenderIdentifier}) � ������������� ��������� ������ ������ �� ���� ������ */ 
	public void notifyAboutNewLetter(SenderIdentifier senderIdentifier);
}
