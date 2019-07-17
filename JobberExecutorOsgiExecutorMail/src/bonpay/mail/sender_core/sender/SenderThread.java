package bonpay.mail.sender_core.sender;

import java.io.File;
import java.util.ArrayList;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import bonpay.mail.sender_core.letter.Letter;
import bonpay.mail.sender_core.letter.LetterIdentifier;
import bonpay.mail.sender_core.manager.ILetterAware;
import bonpay.mail.sender_core.manager.SenderIdentifier;

/** ������, ������� ���������� �����, �������� ���������� �� ���� ������ */
public class SenderThread extends Thread implements INotifyNewLetter, INotifySettingsChange{
	/** ���������� ������������� ������� Sender */
	private SenderIdentifier identifier;
	/** ��������� ������� Sender */
	private SenderSettings senderSettings;
	/** ����, ������� ������� � ������������� ����������� ���������� ����� ������ */
	private volatile boolean flagRun=false;
	/** ������, ������� �������� ����� �������� ��� ������� */
	private Object controlObject=new Object();
	/** ������, �� �������� ����� �������� */
	private ILetterAware letterAware;
	/** ������, ������� "������"  ����������� Sender */
	private ISenderSettingsAware senderSettingsAware;
	/** ������, ������� ������� � ������������� ��������� � ���������� ������� */
	private Boolean isSettingsChanged=false;
	
	
	/** ������, ������� ���������� �����, �������� ���������� �� ���� ������
	 * @param senderIdentifier - unique Sender Identifier 
	 * @param letterAware - ������-���������� ����� �� ���� ������ 
	 * @param senderSettingsAware - ������, ������� ������� ����������� Sender
	 */
	public SenderThread(SenderIdentifier senderIdentifier, ILetterAware letterAware, ISenderSettingsAware senderSettingsAware){
		this.identifier=senderIdentifier;
		this.letterAware=letterAware;
		this.senderSettingsAware=senderSettingsAware;
	}

	/** ���������� ������� � ������������� ��������� ������ ������ �� ���� ������ */
	@Override
	public void notifyAboutNewLetter(){
		synchronized(this.controlObject){
			this.controlObject.notify();
		}
	}
	
	/** ���������� � ������������� ���������� �������� ������� ������-���������� ����� */
	@Override
	public void notifySenderSettingsChange(){
		synchronized(this.isSettingsChanged){
			this.isSettingsChanged=true;
		}
	}
	
	/** ������ ��� �������� �������� ��������� */
	private Session mailSession;
	
	/** �������� ��������� ������� Sender */
	private void updateSenderSettings(SenderSettings senderSettings) {
		// �������� ��������� �� ���� ������ �� ������� �����������
		this.senderSettings=senderSettings;
		try{
			Properties props = new Properties();
	        props.put("mail.smtp.host", this.senderSettings.getSmtpServer());
	        props.put("mail.smtp.port", "" + this.senderSettings.getSmtpPort());
	        
	        mailSession=Session.getDefaultInstance(props,null); 
	        mailSession.setPasswordAuthentication(new URLName(this.senderSettings.getSmtpServer()), new PasswordAuthentication(this.senderSettings.getLogin(), this.senderSettings.getPassword()));
	        // INFO ��������� ������������ ���������� � SMTP ��������
	        mailSession.setDebug(true);
	        mailSession.setDebugOut(System.out);
		}catch(NullPointerException npe){
			System.err.println("updateSenderSettings Exception:"+npe.getMessage());
		}
	}
	
	/** ���������� ����� � ������������� ����������� ������ */
	public void stopThread(){
		this.flagRun=false;
	}
	
	Logger logger=Logger.getLogger(this.getClass());
	private void debug(String value){
		logger.debug(value);
	}
	private void error(String value){
		logger.error(value);
	}
	
	public void run(){
		this.flagRun=true;
		debug("���� ������� �� �������� �������� �� �������� ���������"); 
		this.isSettingsChanged=true;
		while(flagRun==true){
			while(isSettingsChanged==true){
				synchronized(this.isSettingsChanged){
					this.isSettingsChanged=false;
				}
				this.updateSenderSettings(this.senderSettingsAware.getSenderSettingsByIdentifier(this.identifier));
			}
			debug("�������� ��������� ������������� ������"); 
			ArrayList<LetterIdentifier> letters=this.letterAware.getNewLetters(this.identifier);
			if((letters!=null)&&(letters.size()>0)){
				debug("���������");
				if(this.sendLetterByIdentifier(letters.get(0))){
					debug("������ ������ ����������");
					this.letterAware.setLetterAsSended(letters.get(0));
				}else{
					debug("������ �� ���������� - ���������� ���� ��������� �������� ");
					this.letterAware.setLetterAsSendedError(letters.get(0));
				}
				debug("������� �� ������������ �����");
				try{this.senderSettings.getTimeDelay();}catch(Exception ex){};
			}else{
				debug("����� ��� - �������"); 
				synchronized(this.controlObject){
					if((letters!=null)&&(letters.size()>0)){
						debug("��������� ������"); 
						continue;
					}else{
						try{
							this.controlObject.wait();
						}catch(Exception ex){};
					}
				}
			}
		}
	}

	/** �������� ���������� ������������� �� ������� Sender */
	public SenderIdentifier getIdentifier(){
		return this.identifier;
	}
	
	private Multipart getLetterContent(Letter letter){
		Multipart returnValue=new MimeMultipart();
		// add text part
		if(letter.getText()!=null){
			MimeBodyPart textPart=new MimeBodyPart();
			try{
				textPart.setText(letter.getText());
				returnValue.addBodyPart(textPart);
			}catch(Exception ex){
				logger.error("add text part to letter Exception: "+ex.getMessage());
			}
		}
		// add 
		if(letter.getAttachmentSize()!=0){
			// �������� ����� 
			for(int counter=0;counter<letter.getAttachmentSize();counter++){
				String filePath=letter.getAttachment(counter);
				try{
					File file=new File(filePath);
		            MimeBodyPart filePart=new MimeBodyPart();
		            if(file.exists()==true){
			            DataSource fileDataSource=new FileDataSource(file);
			            filePart.setDataHandler(new DataHandler(fileDataSource));
			            filePart.setFileName(file.getName());
			            returnValue.addBodyPart(filePart);
		            }else{
		            	logger.error("add attachment to file, File is not Exists: ");
		            }
				}catch(Exception ex){
					logger.error("add Attachment part to letter Exception: "+ex.getMessage());
				}
			}
		}else{
			// ��� ������ ��� ���������� 
		}
		return returnValue;
	}
	
	/** ��������� ������ � ������� ������������� ����� � ������ �������� */
	private boolean sendLetterByIdentifier(LetterIdentifier letterIdentifier){
		boolean returnValue=false;
		debug("�������� ������ �� ���� ");
		Letter letter=this.letterAware.getLetter(letterIdentifier);
		if(letter!=null){
			// ��������� ������
	        try {
	        	debug("������� ��������� ������");
	            Message msg = new MimeMessage(mailSession);
	            msg.setFrom(new InternetAddress(this.senderSettings.getFrom()));
	            for(int counter=0;counter<letter.getRecipientSize();counter++){
	            	msg.addRecipient(Message.RecipientType.TO,new InternetAddress(letter.getRecipient(counter)));
	            }
	            msg.setSubject(letter.getSubject());
	            // msg.setText(letter.getText());
	            msg.setContent(this.getLetterContent(letter));
	            debug("������� ���������");
	            //Transport.send(msg);
	            Transport transport=mailSession.getTransport("smtp");
	            debug("����������� � ��������");
	            transport.connect(this.senderSettings.getSmtpServer(),this.senderSettings.getSmtpPort(),this.senderSettings.getLogin(), this.senderSettings.getPassword());
	            debug("��������� ");
	            transport.sendMessage(msg, msg.getAllRecipients());
	            debug("������� ���������� � �������� ");
	            transport.close();
	            returnValue=true;
	        } catch (AddressException e) {
	        	returnValue=false;
	        	error("AddressException: "+e.getMessage());
	        	System.err.println("AddressException: "+e.getMessage());
	        } catch (MessagingException e) {
	        	returnValue=false;
	        	System.err.println("MessagingException: "+e.getMessage());
	        	error("MessagingException: "+e.getMessage());
	        }		
		}else{
			debug("������ ��������� ������ �� ���� �� ���� "+letterIdentifier.getId()+"- �������� ��� ���������"); 
			returnValue=false;
			//this.letterAware.setLetterAsSendedError(letterIdentifier);
		}
		return returnValue;
	}

	@Override
	public void notifyAboutNewLetter(SenderIdentifier senderIdentifier) {
		if(this.identifier.equals(senderIdentifier)){
			this.notifyAboutNewLetter();
		}
	}

	@Override
	public void notifySenderSettingsChange(SenderIdentifier senderIdentifier) {
		if(this.identifier.equals(senderIdentifier)){
			this.notifySenderSettingsChange();
		}
	}

}
