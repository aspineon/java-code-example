package bonpay.mail.sender_one_core;

import java.util.Properties;


import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import bonpay.mail.sender_one_core.interfaces.ILetterBodyAware;
import bonpay.mail.sender_one_core.interfaces.ILetterSettingsAware;

/** ����������� E-mail*/
public class SenderOneLetter {
	private Logger logger=Logger.getLogger(this.getClass());
	private ILetterSettingsAware letterSettingsAware;
	
	/** ����������� E-mail*/
	public SenderOneLetter(ILetterSettingsAware letterSettingsAware){
		this.updateSettingsAware(letterSettingsAware);
	}
	
	/** �������� ������, ������� �������� �� ��������� ���������� ��� E-mail */
	public void updateSettingsAware(ILetterSettingsAware letterSettingsAware){
		logger.debug("�������� ������, ������� �������� �� ��������� ���������� ��� E-mail");
		this.letterSettingsAware=letterSettingsAware;
	}
	
	/** ��������� ������ 
	 * @param letterAware - ������, ������� "������" ������� ������ 
	 * @throws Exception - ���� ��������� �����-���� ������ �� ����� �������� (��� �� SMTP ������ ������ ��������� ��� ����������)  
	 */
	public void sendMail(ILetterBodyAware letterAware) throws Exception {
		logger.debug("��������� ������");
		Properties props = new Properties();
		
        props.put("mail.smtp.host", this.letterSettingsAware.getSmtp());
        props.put("mail.smtp.port", "" + this.letterSettingsAware.getPort());
		
        /*Session session = Session.getDefaultInstance(props,new Authenticator(){
        	@Override
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(login, password);
        	}
        });*/
        Session session=Session.getDefaultInstance(props,null);
        if(this.letterSettingsAware.isAuth()){
        	session.setPasswordAuthentication(new URLName(this.letterSettingsAware.getSmtp()), new PasswordAuthentication(this.letterSettingsAware.getLogin(), this.letterSettingsAware.getPassword()));
        }
        // session.setDebug(true);
        logger.debug("������� ��������� ��� �������� ");
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(letterAware.getFrom()));
        String[] recipients=letterAware.getRecipients();
        for(int counter=0;counter<recipients.length;counter++){
        	msg.addRecipient(Message.RecipientType.TO,new InternetAddress(recipients[counter]));
        }
        msg.setSubject(letterAware.getSubject());
        msg.setText(letterAware.getText());
        
      //Transport.send(msg);
        logger.debug("�������� ��������� ��� ��������");
        Transport transport=session.getTransport("smtp");
        logger.debug("���������� ���������� � SMTP ");
        transport.connect(this.letterSettingsAware.getSmtp(),
        				  this.letterSettingsAware.getPort(),
        				  this.letterSettingsAware.getLogin(), 
        				  this.letterSettingsAware.getPassword());
        logger.debug("������� ��������� ");
        transport.sendMessage(msg, msg.getAllRecipients());
        logger.debug("SMTP ������� ������������ �� ��������� ������ � ������� ");
        transport.close();
        logger.debug(" ������� ��������� ��� �������� ������ ");
        // System.out.println("Send is OK: ");
	}
	
	
	
}
