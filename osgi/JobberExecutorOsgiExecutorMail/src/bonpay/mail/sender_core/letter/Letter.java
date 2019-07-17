package bonpay.mail.sender_core.letter;

import java.io.File;
import java.util.ArrayList;

/** ������, ������� ������ ���� ���������� */
public class Letter {
	/** ���� ������ */
	private String subject="";
	/** ����� ������ */
	private String text="";
	/** ��������� ��������� ������ */
	private String state="";
	/** ������ ���� � ����� HTML � �������  */
	private LetterAttachment textAttachment;
	/** ���� � ������������� ������  */
	private ArrayList<LetterAttachment> pathToAttachment=new ArrayList<LetterAttachment>();
	/** ��������  */
	private ArrayList<String> recipient=new ArrayList<String>();
	/** ���� ��������� ������� */
	private int errorCounter=0;
	/** �������� �� ������ HTML �������  */
	private boolean htmlFormat=true;
	
	/** ������, ������� ������ ���� ���������� */
	public Letter(){
	}

	/** �������� ���������� */
	public void addRecipient(String recipient){
		this.recipient.add(recipient);
	}
	
	/** �������� ����� ���-�� ����������� */
	public int getRecipientSize(){
		return this.recipient.size();
	}
	
	/** �������� ���������� �� ������� */
	public String getRecipient(int index){
		try{
			return this.recipient.get(index);
		}catch(Exception ex){
			return null;
		}
	}
	
	/** ������� ���������� �� ������ */
	public void removeRecipient(int index){
		try{
			this.recipient.remove(index);
		}catch(IndexOutOfBoundsException ex){
		}
	}
	
	/** �������� ����, ������� ����� ���������� � ������ �� �������� 
	 * @param attachment - �������������� ����  
	 * @throws ���� �� ������ ���� �� �����, ���� �� ��� ������������� ����� ����� ��� ���������� 
	 */
	public void addAttachmentFile(LetterAttachment attachment) throws Exception {
		File f=new File(attachment.getPathToFile());
		if(f.exists()==false){
			throw  new Exception("file not found");
		}
		if(attachment.getOriginalFileName()==null){
			attachment.setOriginalFileName(f.getName());
		}
		this.pathToAttachment.add(attachment);
	}
	
	/** �������� ���-�� ������������� ������ */
	public int getAttachmentSize(){
		return this.pathToAttachment.size();
	}
	
	/** �������� ���� � �������������� ����� �� �������  */
	public LetterAttachment getAttachment(int index){
		try{
			return this.pathToAttachment.get(index);
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}
	
	/** ������� ������������� ���� �� ������� */
	public void removeAttachment(int index){
		try{
			this.pathToAttachment.remove(index);
		}catch(IndexOutOfBoundsException ex){
		}
	}
	
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/** ���������� ��������� ������ 
	 * <li> <b>NEW</b> ����� </li>
	 * */
	public void setState(String state) {
		this.state=state;
	}
	
	/** �������� ��������� ������ */
	public String getString(){
		return this.state;
	}

	/** �������� ����, ������� ���������� ���-�� ������� � �������� */
	public int getErrorCounter() {
		return errorCounter;
	}

	/** ���������� ����, ������� ���������� ���-�� ������� � �������� */
	public void setErrorCounter(int errorCounter) {
		this.errorCounter = errorCounter;
	}

	/** 
	 * <ul>
	 * <li><b>true</b> - ���������� ������ ������ ��� HTML </li>
	 * <li><b>false</b> - (default) ���������� ������ ��� ����� </li>
	 */
	public void setHtml(boolean value){
		this.htmlFormat=value;
	}
	
	/** 
	 * <ul>
	 * <li><b>true</b> - ������ ������ ��� HTML </li>
	 * <li><b>false</b> - (default) ������ ��� ����� </li>
	 */
	public boolean isHtml(){
		return this.htmlFormat;
	}

	/** �������� ������ ���� � �����, ������� �������� HTML ���, ������� ����� �������� � �����   */
	public LetterAttachment getAttachmentHtmlText() {
		return this.textAttachment;
	}

	/** ���������� ������ ���� � ����� HTML ������� �������� HTML ��� ��� ��������  */
	public void setAttachmentHtmlText(LetterAttachment attachment) {
		this.textAttachment=attachment;
	}
	
}
