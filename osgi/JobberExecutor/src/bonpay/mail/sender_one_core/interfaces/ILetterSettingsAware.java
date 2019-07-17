package bonpay.mail.sender_one_core.interfaces;

public interface ILetterSettingsAware {
	/** ����� SMTP */
	public String getLogin();
	/** ������ SMTP */
	public String getPassword();
	/** ����� SMTP ������� */
	public String getSmtp();
	/** ���� SMTP ������� */
	public int getPort();
	/** ����� �� �������������� �� ������� ��� �������� ������ */
	public boolean isAuth();
}
