package bonpay.mail.sender_core.sender;

import java.sql.Connection;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import database.IConnectionAware;

import bonpay.mail.sender_core.manager.ILetterAwareFactory;
import bonpay.mail.sender_core.manager.ISenderCreator;
import bonpay.mail.sender_core.manager.SenderIdentifier;
import bonpay.mail.sender_core.sender.ISenderSettingsAware;
import bonpay.mail.sender_core.sender.SenderSettings;
import bonpay.mail.sender_core.sender.SenderThread;

/** ��������� ��� ������������ �������� ���������  */
public class SenderThreadContainer implements ISenderCreator, ISenderSettingsAware, INotifyNewLetter, INotifySettingsChange{
	private Logger logger=Logger.getLogger(this.getClass());
	/** ���������� � ����� ������ */
	private IConnectionAware connectWrapAware;
	/** �������, ������������ �������, �������/�������� � ������� �� ������� */
	private ILetterAwareFactory letterAwareFactory;
	/** ���������� �������������� ������������ */
	private ArrayList<SenderIdentifier> listOfIdentifier=new ArrayList<SenderIdentifier>();
	/** ������ �������-������������ */
	private ArrayList<SenderThread> listOfSenderThread=new ArrayList<SenderThread>();
	/** ���� ����, ��� ��� ������ ��� �������� */
	private boolean flagThreadIsStarted=false;
	
	/** ��������� ��� ������������ �������� ���������  */
	public SenderThreadContainer(IConnectionAware connectWrapAware, ILetterAwareFactory letterAwareFactory){
		this.connectWrapAware=connectWrapAware;
		this.letterAwareFactory=letterAwareFactory;
		logger.debug("�������� ��� ��������������");
		this.readAllIdentifiers();
		logger.debug("������� ��� SenderThread");
		this.createAllSenderThread();
	}
	
	/** ��������� ��� ������-����������� */
	public void startAllSenderThread(){
		logger.debug("��������� ��� ������-�����������");
		if(this.flagThreadIsStarted==false){
			for(int index=0;index<this.listOfSenderThread.size();index++){
				this.listOfSenderThread.get(index).start();
			}
			this.flagThreadIsStarted=true;
		}else{
			logger.debug("������ ��� ��������");
		}
	}
	
	/** �� ��������� ���������� ��������������� ������� ������������ */
	private void createAllSenderThread(){
		logger.debug("�� ��������� ���������� ��������������� ������� ������������");
		for(int index=0;index<this.listOfIdentifier.size();index++){
			this.listOfSenderThread.add(new SenderThread(this.listOfIdentifier.get(index), 
														 letterAwareFactory.createNewLetterAware(), 
														 this));
		}
	}
	
	/** �������� ��� ���������� �������������� ������������ */
	private void readAllIdentifiers(){
		logger.debug("�������� ��� ���������� �������������� ������������");
		Connection connection=this.connectWrapAware.getConnection();
		try{
			ResultSet rs=connection.createStatement().executeQuery("select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL");
			logger.debug("select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL");
			while(rs.next()){
				listOfIdentifier.add(new SenderIdentifier(rs.getInt("ID_EMAIL_PROFILE")));
			}
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("SenderThreadContainer#getAllSenders Exception: "+ex.getMessage());
			logger.error("SenderThreadContainer#getAllSenders Exception: "+ex.getMessage(),ex);
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
	}
	
	@Override
	public ArrayList<SenderIdentifier> getAllSendersIdentifier() {
		return this.listOfIdentifier;
	}

	@Override
	public SenderThread getSender(SenderIdentifier senderIdentifier) {
		int index=this.listOfIdentifier.indexOf(senderIdentifier);
		if(index>=0){
			return this.listOfSenderThread.get(index);
		}else{
			return null;
		}
	}

	@Override
	public SenderSettings getSenderSettingsByIdentifier(SenderIdentifier identifier) {
		logger.debug("�������� ��������� Sender-a ");
		SenderSettings returnValue=null;
		Connection connection=this.connectWrapAware.getConnection();
		try{
			String query="select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL where ID_EMAIL_PROFILE="+identifier.getId();
			logger.debug("select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL where ID_EMAIL_PROFILE="+identifier.getId());
			ResultSet rs=connection.createStatement().executeQuery(query);
			if(rs.next()){
				returnValue=new SenderSettings(rs.getString("SENDER_EMAIL"),
											   rs.getString("SMTP_SERVER"),
											   rs.getInt("SMTP_PORT"),
											   rs.getString("SMTP_USER"),
											   rs.getString("SMTP_PASSWORD"),
											   rs.getString("NEED_AUTORIZATION").equals("Y"),
											   rs.getLong("DELAY_NEXT_LETTER"),
											   rs.getInt("max_error_messages"));
			}
			rs.getStatement().close();
		}catch(Exception ex){
			logger.error("Test#getAllSenders Exception: "+ex.getMessage(), ex);
			System.err.println("Test#getAllSenders Exception: "+ex.getMessage());
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}

	@Override
	public void notifyAboutNewLetter() {
		logger.debug("���������� ��� ������ ����������� � ������� ����� ����� ");
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifyAboutNewLetter();
		}
	}

	@Override
	public void notifyAboutNewLetter(SenderIdentifier senderIdentifier) {
		logger.debug("���������� ����������� ��������� � ������� ����� ����� ��� ���� ");
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifyAboutNewLetter(senderIdentifier);
		}
	}

	@Override
	public void notifySenderSettingsChange() {
		logger.debug("���������� ���� ����������� � ������������� ���������� ���������� (��������) ");
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifySenderSettingsChange();
		}
	}

	@Override
	public void notifySenderSettingsChange(SenderIdentifier senderIdentifier) {
		logger.debug("���������� ����������� ���������� � ������������� ���������� ���������� (��������) ");
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifySenderSettingsChange(senderIdentifier);
		}
	}

	public void stopAllSenderThread() {
		logger.debug("���������� ��� ������ ");
		if(this.flagThreadIsStarted==true){
			for(int index=0;index<this.listOfSenderThread.size();index++){
				try{
					this.listOfSenderThread.get(index).stopThread();
					this.listOfSenderThread.get(index).interrupt();
				}catch(Exception ex){
					logger.info("������ ��������� ������ �"+index);
				}
			}
			this.flagThreadIsStarted=true;
		}else{
			logger.debug("������ ��� �����������");
		}
	}
	
}