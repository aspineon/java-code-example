package bonclub.office_private.gui.text_exchange;

import jms.sender.JMSSender;

/** ��������� ��������� ��������� */
public class JmsTextSender implements ITextOutput{
	private JMSSender sender;
	
	/** ��������� ��������� ��������� 
	 * @param pathToJms -������ ���� � JMS 
	 * @param queueName - ��� ������� 
	 * @param senderTitle - ������������ 
	 */
	public JmsTextSender(String pathToJms, String queueName, String senderTitle ){
		sender=new JMSSender(pathToJms, queueName,10, senderTitle);
		sender.start();
	}
	@Override
	public boolean sendText(String message) {
		return this.sender.addTextMessage(message);
	}

}
