package bonpay.mail.sender_one_core.interfaces;

import java.sql.Connection;

public interface IConfirmSendLetter {
	/** �������� ������ ��� ��������� */
	public boolean setLetterAsSended(int id, Connection connection);
	/** �������� ������ ��� �� ������������  */
	public boolean setLetterAsSendedError(int id, Connection connection);
}
