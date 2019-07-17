package bonpay.mail.sender_core.manager;

import java.util.ArrayList;

import bonpay.mail.sender_core.sender.SenderThread;

/** ������, ������� ������� Sender �� ��������� ����������� �������������� */
public interface ISenderCreator {
	/** �������� ���������� �������������� �� ���� Sender */
	public ArrayList<SenderIdentifier> getAllSendersIdentifier();
	/** �������� Sender �� ��������� ����������� �������������� */
	public SenderThread getSender(SenderIdentifier senderIdentifier);
}
