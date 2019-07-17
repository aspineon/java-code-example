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
	/** ���� � ������������� ������  */
	private ArrayList<String> pathToAttachment=new ArrayList<String>();
	/** ��������  */
	private ArrayList<String> recipient=new ArrayList<String>();
	
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
	 * @param pathToFile - ���� � ����� 
	 */
	public void addAttachmentFile(String pathToFile) throws Exception {
		File f=new File(pathToFile);
		if(f.exists()==false){
			throw  new Exception("file not found");
		}
		this.pathToAttachment.add(pathToFile);
	}
	
	/** �������� ���-�� ������������� ������ */
	public int getAttachmentSize(){
		return this.pathToAttachment.size();
	}
	
	/** �������� ������������� ���� �� �������  */
	public String getAttachment(int index){
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
}
