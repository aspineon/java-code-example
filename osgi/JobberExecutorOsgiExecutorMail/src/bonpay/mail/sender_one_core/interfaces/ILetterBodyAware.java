package bonpay.mail.sender_one_core.interfaces;

/** ���������, ������� ������� ������������ ��� ��������/�������� ������ ����������� */
public interface ILetterBodyAware {
	/** �������� ���� ����������� ������� ����������� */
	public String[] getRecipients();
	/** �������� Subject ������ */
	public String getSubject(); 
	/** �������� ����� ������ */
	public String getText(); 
	/** ����� ����������� (�� ���� )*/
	public String getFrom();
}
