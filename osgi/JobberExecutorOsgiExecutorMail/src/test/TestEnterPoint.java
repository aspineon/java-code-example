package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import bonpay.mail.sender_core.letter.Letter;
import bonpay.mail.sender_core.letter.LetterAttachment;
import bonpay.mail.sender_core.sender.SenderSettings;

public class TestEnterPoint {
	private static Logger logger=Logger.getRootLogger();
	static{
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args){
		System.out.println("begin");
		
		SenderSettings settings=new SenderSettings("technik7@mail.ru", 
												   "smtp.mail.ru", 
												   25, 
												   "technik7", // login 
												   "",   // password
												   true, 
												   1000, 
												   2);
		
		Properties props = new Properties();
        props.put("mail.smtp.host", settings.getSmtpServer());
        props.put("mail.smtp.port", "" + settings.getSmtpPort());

        /** ������ ��� �������� �������� ��������� */
    	Session mailSession;
        
        mailSession=Session.getDefaultInstance(props,null); 
        mailSession.setPasswordAuthentication(new URLName(settings.getSmtpServer()), new PasswordAuthentication(settings.getLogin(), settings.getPassword()));
        // INFO ��������� ������������ ���������� � SMTP ��������
        // mailSession.setDebug(true);
        // mailSession.setDebugOut(System.out);
        
        Letter letter=getLetterFromDirectory("d:\\temp_for_mail\\");
        letter.addRecipient("technik7job@gmail.com");
        // letter.addRecipient("Vitaliy.cherkashin@nmtg.com.ua");
        // letter.addRecipient("evgen.paliy@gmail.com");
        // letter.addRecipient("valeriy.kravchuk@nmtg.com.ua");
        
        
        /*try{
        	// letter.addAttachmentFile("D:\\image.jpg");
        }catch(Exception ex){};
        letter.setText("<html> <head> </head> <body> <table border=\"1\"> <tr><td>hello</td></tr> <tr><td> <img id=\"imgWait\" src=\"image.jpg\" /> </td></tr></table></body> </html>");
        letter.setHtml(false);
        */
        sendMessage(letter, settings, mailSession);
		System.out.println("-end-");
	}
	
	/** �������� ������ �� ���������� ��������  */
	private static Letter getLetterFromDirectory(String pathToDirectory){
		Letter letter=new Letter();
		try{
			File file=new File(pathToDirectory);
			if(file.isDirectory()){
				// �������� ���� ������ ������
				File[] fileList=file.listFiles();
				// �������� �� ���� ������ � �������� ��� �������������  
				for(int counter=0;counter<fileList.length;counter++){
					String fileName=fileList[counter].getName();
					if(fileName.equalsIgnoreCase("index.html")||fileName.equalsIgnoreCase("index.htm")){
						letter.setText(readTextFromFile(fileList[counter],"UTF-8"));
						letter.setHtml(true);
					}else{
						letter.addAttachmentFile(new LetterAttachment(fileList[counter].getName(),fileList[counter].getAbsolutePath() ));
					}
				}
				letter.setHtml(true);
			}else{
				// ��������� �� ���� 
				if(file.exists()){
					System.out.println("File founded: ");
					String fileName=file.getName();
					if(fileName.endsWith(".txt")||fileName.endsWith(".text")||fileName.endsWith(".msg")){
						letter.setText(readTextFromFile(file,null));
						letter.setHtml(false);
					}else if(fileName.endsWith(".html")||fileName.endsWith(".htm")){
						letter.setText(readTextFromFile(file,"UTF-8"));
						letter.setHtml(true);
					}
				}else{
					System.err.println("File does not found: "+pathToDirectory);
				}
			}
			// �������� index.html � ������� ������ 
			// �������� ��� ����� � �������� �� ��� �������� 
			
		}catch(Exception ex){
			System.err.println("TestEnterPoint#getLetterFromDirectory: "+ex.getMessage());
		}
		return letter;
	}
	
	/** �������� �� ����� ���� ����� � ������� ��� ������  */
	private static String readTextFromFile(File file, String encoding){
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
	
	/** ��������� ������ */
	private static void sendMessage(Letter letter, SenderSettings senderSettings, Session mailSession){
		try{
	    	logger.debug("������� ��������� ������");
	        Message msg = new MimeMessage(mailSession);
	        msg.setFrom(new InternetAddress(senderSettings.getFrom()));
	        for(int counter=0;counter<letter.getRecipientSize();counter++){
	        	msg.addRecipient(Message.RecipientType.TO,
	        					 new InternetAddress(letter.getRecipient(counter))
	        					 );
	        }
	        msg.setSubject(letter.getSubject());
	        // msg.setText(letter.getText());
	        msg.setContent(getLetterContent(letter));
	        logger.debug("������� ���������");
	        //Transport.send(msg);
	        Transport transport=mailSession.getTransport("smtp");
	        logger.debug("����������� � ��������");
	        transport.connect(senderSettings.getSmtpServer(),senderSettings.getSmtpPort(),senderSettings.getLogin(), senderSettings.getPassword());
	        logger.debug("��������� ");
	        /*if(letter.getErrorCounter()>0){
	        	logger.debug("���� ��������� "+letter.getErrorCounter()+" �������, ��������� ������� �"+(letter.getErrorCounter()+1));
	        }*/
	        transport.sendMessage(msg, msg.getAllRecipients());
	        logger.debug("������� ���������� � �������� ");
	        transport.close();
		}catch(Exception ex){
			System.err.println("sendMessage:"+ex.getMessage());
		}
	}
	
	/** �������� �������� ������ */
	private static Multipart getLetterContent(Letter letter){
		// Multipart returnValue=new MimeMultipart("alternative");
		Multipart returnValue=new MimeMultipart("mixed");

		HashMap<String, String> replaceNames=new HashMap<String, String>();
		// add attachment files  
		if(letter.getAttachmentSize()!=0){
			logger.error("�������� ����� � ������ "); 
			for(int counter=0;counter<letter.getAttachmentSize();counter++){
				LetterAttachment attachment=letter.getAttachment(counter);
				logger.debug(counter+" : "+attachment.getOriginalFileName());
				try{
					File file=new File(attachment.getPathToFile());
		            MimeBodyPart filePart=new MimeBodyPart();
		            if(file.exists()==true){
			            DataSource fileDataSource=new FileDataSource(file);
			            filePart.setDataHandler(new DataHandler(fileDataSource));
			            filePart.setFileName(attachment.getOriginalFileName());
			            // filePart.setHeader("MIME-Version", "1.0");
			            // filePart.setHeader("Content-Type", "image/jpeg; name=\""+file.getName()+"\"");
			            returnValue.addBodyPart(filePart);
			            String contentFileName=attachment.getOriginalFileName()+randomString(4);
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
		
		// INFO letter content
		if(letter.getText()!=null){
			MimeBodyPart textPart=new MimeBodyPart();
			try{
				if(letter.isHtml()==true){
					// textPart.setText(letter.getText(),"text/html");
					textPart.setText(replaceAllValues(letter.getText(),replaceNames));
					textPart.setHeader("Content-Type", "text/html; charset=utf-8");
					// textPart.setHeader("MIME-Version", "1.0");
					// textPart.setHeader("Content-Type", textPart.getContentType());
				}else{
					textPart.setText(letter.getText());
				}
				returnValue.addBodyPart(textPart);
			}catch(Exception ex){
				logger.error("add text part to letter Exception: "+ex.getMessage(), ex);
			}
		}
		
		return returnValue;
	}
	
	private static String randomString(int size){
		Random random=new Random();
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<size;counter++){
			returnValue.append(random.nextInt(16));
		}
		return returnValue.toString();
	}
	
	private static String replaceAllValues(String value, HashMap<String,String> replaceNames){
		Iterator<String> keys=replaceNames.keySet().iterator();
		while(keys.hasNext()){
			String currentValue=keys.next();
			String replaceValue=replaceNames.get(currentValue);
			System.out.println("Replace All: "+currentValue+"   >>> "+replaceValue);
			value=value.replaceAll(currentValue, "cid:"+replaceValue);
		}
		return value;
	}
}
