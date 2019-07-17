package bonpay.mail.sender_core.sender;

import bonpay.mail.sender_core.manager.SenderIdentifier;

/** ������ �� ��������� �������� ���  */
public interface ISenderSettingsAware {
	/** �������� �� ���������� {@link SenderIdentifier} ��������� */
	public SenderSettings getSenderSettingsByIdentifier(SenderIdentifier identifier);
}
