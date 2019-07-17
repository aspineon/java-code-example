package bonpay.mail.sender_core.sender;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

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
import bonpay.mail.sender_core.letter.LetterAttachment;
import bonpay.mail.sender_core.letter.LetterIdentifier;
import bonpay.mail.sender_core.letter.Translit;
import bonpay.mail.sender_core.manager.ILetterAware;
import bonpay.mail.sender_core.manager.SenderIdentifier;

/** ������, ������� ���������� �����, �������� ���������� �� ���� ������ */
public class SenderThread extends Thread implements INotifyNewLetter, INotifySettingsChange{
	
	Logger logger=Logger.getLogger(this.getClass());
	/** ���������� ������������� ������� Sender */
	private SenderIdentifier senderIdentifier;
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
	public SenderThread(SenderIdentifier senderIdentifier, 
					    ILetterAware letterAware, 
					    ISenderSettingsAware senderSettingsAware){
		this.senderIdentifier=senderIdentifier;
		this.letterAware=letterAware;
		this.senderSettingsAware=senderSettingsAware;
	}

	/** ���������� ������� � ������������� ��������� ������ ������ �� ���� ������ */
	@Override
	public void notifyAboutNewLetter(){
		logger.debug("���������� ������� � ������������� ��������� ������ ������ �� ���� ������");
		synchronized(this.controlObject){
			this.controlObject.notify();
		}
	}
	
	/** ���������� � ������������� ���������� �������� ������� ������-���������� ����� */
	@Override
	public void notifySenderSettingsChange(){
		logger.debug("���������� � ������������� ���������� �������� ������� ������-���������� �����");
		synchronized(this.isSettingsChanged){
			this.isSettingsChanged=true;
		}
	}
	
	/** ������ ��� �������� �������� ��������� */
	private Session mailSession;
	
	/** �������� ��������� ������� Sender */
	private void updateSenderSettings(SenderSettings senderSettings) {
		logger.debug("�������� ��������� �� ���� ������ �� ������� �����������");
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
			logger.error("updateSenderSettings Exception:"+npe.getMessage(), npe);
		}
	}
	
	/** ���������� ����� � ������������� ����������� ������ */
	public void stopThread(){
		this.flagRun=false;
	}
	
	

	public void run(){
		this.flagRun=true;
		logger.debug("���� ������� �� �������� �������� �� �������� ���������"); 
		this.isSettingsChanged=true;
		while(flagRun==true){
			while(isSettingsChanged==true){
				synchronized(this.isSettingsChanged){
					this.isSettingsChanged=false;
				}
				this.updateSenderSettings(this.senderSettingsAware.getSenderSettingsByIdentifier(this.senderIdentifier));
			}
			logger.debug("�������� ��������� ������������� ������"); 
			ArrayList<LetterIdentifier> letters=this.letterAware.getNewLetters(this.senderIdentifier);
			if((letters!=null)&&(letters.size()>0)){
				logger.debug("���������");
				String resultSend=this.sendLetterByIdentifier(letters.get(0)); 
				if(resultSend==null){
					logger.info("������ ������ ����������: "+letters.get(0).getId());
					this.letterAware.setLetterAsSended(letters.get(0));
				}else{
					logger.info("������ �� ���������� - �������� ������� ������ �������� ��� ��������� :"+letters.get(0).getId());
					this.letterAware.incLetterErrorCounter(letters.get(0));
					Letter letter=this.letterAware.getLetter(letters.get(0));
					if(letter.getErrorCounter()>=this.senderSettings.getMaxAttemptError()){
						logger.info("���-�� ��������� �������� �������� ����������� �������� ��� ������ :"+letters.get(0).getId()+" - �������� ������ ��������");
						this.letterAware.setLetterAsSendedError(letters.get(0),resultSend);
					}
				}
				logger.debug("������� �� ������������ �����");
				try{this.senderSettings.getTimeDelay();}catch(Exception ex){};
			}else{
				logger.debug("����� ��� - �������"); 
				synchronized(this.controlObject){
					/*
					letters=this.letterAware.getNewLetters(this.senderIdentifier);
					if((letters!=null)&&(letters.size()>0)){
						logger.debug("��������� ������"); 
						continue;
					}else{
						try{
							this.controlObject.wait();
						}catch(Exception ex){};
					}*/
					try{
						this.controlObject.wait();
					}catch(Exception ex){};
				}
			}
		}
	}

	/** �������� ���������� ������������� �� ������� Sender */
	public SenderIdentifier getIdentifier(){
		return this.senderIdentifier;
	}
	
	private Multipart getLetterContent(Letter letter, String from){
		MimeMultipart returnValue=new MimeMultipart("related"); // new MimeMultipart("related");// new MimeMultipart("alternative");// new MimeMultipart("mixed");
		HashMap<String, String> replaceNames=new HashMap<String, String>();

		ArrayList<MimeBodyPart> listOfParts=new ArrayList<MimeBodyPart>();
		
		// INFO letter add attachment 
		if(letter.getAttachmentSize()!=0){
			logger.error("�������� ����� � ������ "); 
			for(int counter=0;counter<letter.getAttachmentSize();counter++){
				LetterAttachment attachment=letter.getAttachment(counter);
				logger.debug(counter+" : "+attachment.getOriginalFileName());
				try{
					File file=new File(attachment.getPathToFile());
		            MimeBodyPart filePart=new MimeBodyPart();
		            filePart.addHeader("Content-Type", "multipart/related");
		            if(file.exists()==true){
			            DataSource fileDataSource=new FileDataSource(file);
			            filePart.setDataHandler(new DataHandler(fileDataSource));
			            filePart.setFileName(replaceRussianLetters(attachment.getOriginalFileName()));
			            // filePart.setHeader("MIME-Version", "1.0");
			            // filePart.setHeader("Content-Type", "image/jpeg; name=\""+file.getName()+"\"");
			            listOfParts.add(filePart);
			            // returnValue.addBodyPart(filePart);
			            String contentFileName=replaceRussianLetters(attachment.getOriginalFileName())+this.randomString(4)+"@"+getDomain(from);
			            filePart.setHeader("Content-ID", "<"+contentFileName+">");
			            replaceNames.put(attachment.getOriginalFileName(), contentFileName);
		            }else{
		            	logger.error("add attachment to file, File is not Exists: ");
		            }
				}catch(Exception ex){
					logger.error("add Attachment part to letter Exception: "+ex.getMessage(),ex);
				}
			}
		}else{
			logger.error("��� ������������� ������"); 
		}
		
		// INFO letter set Text
		if((letter.getAttachmentHtmlText()!=null)&&(letter.getAttachmentHtmlText().getPathToFile()!=null)){
			logger.info(" ���������� ������ HTML ���� ");
			try{
				// �������� ���� � ���������� ����� ����������
				// MimeBodyPart textPart=new MimeBodyPart();
				// textPart.setText("This is HTML content");
				// textPart.addHeader("Content-Type", "text/plain; charset=UTF-8; format=flowed");
				// returnValue.addBodyPart(textPart);

				MimeBodyPart htmlPart=new MimeBodyPart();
				// �������� ���� � ���������� ����� ����������
				// logger.debug("------------------------");
				// logger.debug(readTextFromFile(letter.getAttachmentHtmlText().getPathToFile(),"UTF-8"));
				// logger.debug("------------------------");
				htmlPart.setText(replaceAllValues(readTextFromFile(letter.getAttachmentHtmlText().getPathToFile(),
																   "UTF-8"),
												  replaceNames));
				// htmlPart.setContent(replaceAllValues(readTextFromFile(letter.getAttachmentHtmlText().getPathToFile(),"UTF-8"),replaceNames), "text/html");
				// htmlPart.addHeader("Content-Type", "multipart/related");
				htmlPart.addHeader("Content-Type", "text/html; charset=UTF-8"); // ;charset=UTF-8
				returnValue.addBodyPart(htmlPart);
				
			}catch(Exception ex){
				logger.error("get path to Html Exception:"+ex.getMessage());
			}
		}else{
			logger.debug("�������� ��������� ������"); 
			if(letter.getText()!=null){
				logger.info(" ���������� ������ - ����� ");
				MimeBodyPart textPart=new MimeBodyPart();
				try{
					if(letter.isHtml()==true){
						logger.debug("HTML �����");
						// textPart.setText(letter.getText(),"text/html");
						textPart.setText(replaceAllValues(letter.getText(),replaceNames));
						// textPart.setHeader("MIME-Version", "1.0");
						textPart.setHeader("Content-Type", "text/html; charset=UTF-8");
						// textPart.setHeader("Content-Type", textPart.getContentType());
					}else{
						logger.debug("Plain �����");
						textPart.setHeader("Content-Type", "text/plain; charset=UTF-8");
						textPart.setText(letter.getText());
					}
					returnValue.addBodyPart(textPart);
				}catch(Exception ex){
					logger.error("add text part to letter Exception: "+ex.getMessage(), ex);
				}
			}else{
				logger.info("������ �� ����� ������");
			}
		}
		// �������� ��� ��������� �������� �������� ����� ������ 
		for(int counter=0;counter<listOfParts.size();counter++){
			try{
				returnValue.addBodyPart(listOfParts.get(counter));
			}catch(Exception ex){
				logger.error("add attachment to Letter Body Exception:"+ex.getMessage());
			}
			
		}
		return returnValue;
	}

	/** �������� ��� ������ �� ��������� ����������� */
	private String getDomain(String value){
		int index=value.indexOf("@");
		if(index>0){
			return value.substring(index+1);
		}else{
			return value;
		}
	}
	
	/** ������ ������� ���� �� ���������� �������  */
	private String replaceRussianLetters(String russianFileNames){
		return Translit.toTranslit(russianFileNames);
	}
	
	/** �������� �� ����� ���� ����� � ������� ��� ������  */
	private String readTextFromFile(String pathToFile, String encoding){
		File file=new File(pathToFile);
		BufferedReader reader=null;
		StringBuffer returnValue=new StringBuffer();
		try{
			if(encoding!=null){
				reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));
			}else{
				reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			}
			String currentString=null;
			while( (currentString=reader.readLine())!=null ){
				System.out.println(currentString);
				returnValue.append(currentString);
			}
		}catch(Exception ex){
			System.err.println("readTextFromFile Exception:"+ex.getMessage());
		}finally{
			try{
				reader.close();
			}catch(Exception ex){};
		}
		return returnValue.toString();
	}
	
	
	/** �������� ��������� ������ ��� �������� ����������� �ontent-ID */
	private String randomString(int size){
		Random random=new Random();
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<size;counter++){
			returnValue.append(random.nextInt(16));
		}
		return returnValue.toString();
	}
	
	/** �������� � ��������� ������ ��� ��������� ���� ������ �� �� ��������, � ��������� "cid"
	 * @param value - ������ ����� ������
	 * @param replaceNames - ���� [������������ ���]=[��������]
	 * @return ������ ����� ������ 
	 */
	private String replaceAllValues(String value, HashMap<String,String> replaceNames){
		Iterator<String> keys=replaceNames.keySet().iterator();
		while(keys.hasNext()){
			String currentValue=keys.next();
			String replaceValue=replaceNames.get(currentValue);
			// System.out.println("Replace All: "+currentValue+"   >>> "+replaceValue);
			// INFO ��������, ����� ����� �������� ���������: "src=\""+currentValue, "src=\"cid:"+replaceValue 
			value=value.replaceAll("src=\""+currentValue, "src=\"cid:"+replaceValue);
			value=value.replaceAll("SRC=\""+currentValue, "SRC=\"cid:"+replaceValue);
		}
		return value;
	}
	
	
	/** ��������� ������ � ������� ������������� ����� � ������ �������� */
	private String sendLetterByIdentifier(LetterIdentifier letterIdentifier){
		// INFO �������� ������ 
		String returnValue=null;
		logger.debug("�������� ������ �� ���� ");
		Letter letter=this.letterAware.getLetter(letterIdentifier);
		if(letter!=null){
			logger.info("��������� �������� ������");
	        try {
	        	logger.debug("������� ��������� ������");
	            MimeMessage msg = new MimeMessage(mailSession);
	            logger.debug("from: ");
	            msg.setFrom(new InternetAddress(this.senderSettings.getFrom()));
	            for(int counter=0;counter<letter.getRecipientSize();counter++){
	            	logger.debug(counter+" : "+letter.getRecipient(counter));
	            	msg.addRecipient(Message.RecipientType.TO,new InternetAddress(letter.getRecipient(counter)));
	            }
	            logger.debug("Subject: "+letter.getSubject());
	            msg.setSubject(letter.getSubject());
	            // msg.setText(letter.getText());
	            logger.debug("Set content: "+letter.getSubject());
	            msg.setContent(this.getLetterContent(letter, this.senderSettings.getFrom()));
	            logger.debug("������� ���������");
	            //Transport.send(msg);
	            Transport transport=mailSession.getTransport("smtp");
	            logger.debug("����������� � ��������");
	            transport.connect(this.senderSettings.getSmtpServer(),this.senderSettings.getSmtpPort(),this.senderSettings.getLogin(), this.senderSettings.getPassword());
	            logger.debug("��������� ");
	            if(letter.getErrorCounter()>0){
	            	logger.debug("���� ��������� "+letter.getErrorCounter()+" �������, ��������� ������� �"+(letter.getErrorCounter()+1));
	            }
	            transport.sendMessage(msg, msg.getAllRecipients());
	            logger.debug("������� ���������� � �������� ");
	            transport.close();
	            returnValue=null;
	        } catch (AddressException e) {
	        	returnValue=e.getMessage();
	        	logger.error("AddressException: "+e.getMessage(),e);
	        	System.err.println("AddressException: "+e.getMessage());
	        } catch (MessagingException e) {
	        	returnValue=e.getMessage();
	        	System.err.println("MessagingException: "+e.getMessage());
	        	logger.error("MessagingException: "+e.getMessage(),e);
	        }		
		}else{
			logger.debug("������ ��������� ������ �� ���� �� ���� "+letterIdentifier.getId()+"- �������� ��� ���������"); 
			returnValue="GET_LETTER_ERROR";
			//this.letterAware.setLetterAsSendedError(letterIdentifier);
		}
		return returnValue;
	}

	@Override
	public void notifyAboutNewLetter(SenderIdentifier senderIdentifier) {
		logger.debug("���������� � ������� ����� ����� ");
		if(this.senderIdentifier.equals(senderIdentifier)){
			this.notifyAboutNewLetter();
		}
	}

	@Override
	public void notifySenderSettingsChange(SenderIdentifier senderIdentifier) {
		logger.debug("���������� � ������������� ����� Settings of Sender");
		if(this.senderIdentifier.equals(senderIdentifier)){
			this.notifySenderSettingsChange();
		}
	}

}
