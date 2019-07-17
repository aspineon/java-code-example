package bonpay.mail.sender_core.manager;

import java.util.ArrayList;

import bonpay.mail.sender_core.letter.Letter;
import bonpay.mail.sender_core.letter.LetterIdentifier;

/** ��������� ����������� � �������� �� ���� ������ 
 * <li> ��������� ��� �������� </li>
 * <li> �������� ��� ������������  </li>
 * */
public interface ILetterAware {
	/** �������� ��� �������������� ����� ��� �������� �� ���� ������ */
	public ArrayList<LetterIdentifier> getNewLetters(SenderIdentifier senderIdentifier);
	
	/** �������� ������ �� ����������� �������������� 
	 * @param letterIdentifier - ���������� ������������� ������ 
	 * @return
	 */
	public Letter getLetter(LetterIdentifier letterIdentifier);

	/** ���������� ��� ������ ���� "���������" */
	public boolean setLetterAsSended(LetterIdentifier letterIdentifier);

	/** ���������� ��� ������ ���� "������ �������� " */
	public boolean setLetterAsSendedError(LetterIdentifier letterIdentifier, String errorMessage);

	/** ��� ���������� ������ �������� �������� ����� ��������� �������� */
	public boolean incLetterErrorCounter(LetterIdentifier letterIdentifier);
	
}
