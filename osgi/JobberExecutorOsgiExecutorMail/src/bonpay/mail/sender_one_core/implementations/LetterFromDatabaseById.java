package bonpay.mail.sender_one_core.implementations;

import java.sql.ResultSet;
import java.sql.Connection;

import org.apache.log4j.Logger;

import bonpay.mail.sender_one_core.interfaces.ILetterSettingsAware;
import bonpay.mail.sender_one_core.interfaces.ILetterBodyAware;

/** ������ �� ���� ������ �� ��������� ����������� ��������������  */
public class LetterFromDatabaseById implements ILetterBodyAware, ILetterSettingsAware{
	private Logger logger=Logger.getLogger(this.getClass());
	/** ���������� */
	private String[] recipients;
	/** ������� ������ */
	private String subject;
	/** ����� ������ */
	private String text;
	/** �� ���� */
	private String from;
	/** ����� */
	private String login;
	/** ������ */
	private String password;
	/** SMTP ������ */
	private String smtp;
	/** ���� */
	private int port;
	/** ������������� � �������������� */
	private boolean auth=false;
	
	/** �������� ����������� ���� �� ���� ������ ( �� ������� bc_admin.VC_EMAIL_ALL ) 
	 * @param connection - ���������� � ����� ������ 
	 * @param letterId - ���������� ������������� ������ 
	 * @throws Exception - ���� �� ������� �������� ������ 
	 */
	public LetterFromDatabaseById(Connection connection, int letterId) throws Exception{
		logger.debug("�������� ����������� ���� �� ���� ������ ");
		String query="select * from bc_admin.VC_EMAIL_ALL where id_message="+letterId;
		ResultSet rs=connection.createStatement().executeQuery(query);
		logger.debug(query);
		if(rs.next()){
			this.loadFromResultSet(rs);
			rs.getStatement().close();
			logger.debug("������ ������� � ��������� � ������ ");
		}else{
			logger.warn("������ �� �������: "+letterId);
			rs.getStatement().close();
			throw new Exception("Letter was not found ");
		}
	}

	/** ��������� ������ ������ �� ResultSet  
	 * @param rs - ����� ������ 
	 * @throws Exception ���� ��������� ������ ��� ������� ��������� ������ �/��� ����������  
	 */
	private void loadFromResultSet(ResultSet rs) throws Exception{
		this.recipients=new String[]{rs.getString("RECEIVER_EMAIL")};
		this.subject=rs.getString("TITLE_MESSAGE");
		this.text=rs.getString("TEXT_MESSAGE");
		this.from=rs.getString("SENDER_EMAIL");
		this.login=rs.getString("SMTP_USER");
		this.password=rs.getString("SMTP_PASSWORD");
		this.smtp=rs.getString("SMTP_SERVER");
		this.port=rs.getInt("SMTP_PORT");
		auth=rs.getString("NEED_AUTORIZATION").equals("Y");
	}

	
	
	/** �������� ����������� ���� �� ���� ������ ( �� ������� bc_admin.VC_EMAIL_ALL )
	 * @param rs - ����� ������ (������ ������ ������ �� ������ )
	 * @throws Exception (����������, ���� �� ������� �������� ������ )
	 */
	public LetterFromDatabaseById(ResultSet rs) throws Exception{
		this.loadFromResultSet(rs);
	}
	

	@Override
	public String getLogin() {
		return this.login;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public int getPort() {
		return this.port;
	}

	@Override
	public String getSmtp() {
		return this.smtp;
	}

	@Override
	public boolean isAuth() {
		return this.auth;
	}


	@Override
	public String getFrom() {
		return this.from;
	}


	@Override
	public String[] getRecipients() {
		return this.recipients;
	}


	@Override
	public String getSubject() {
		return this.subject;
	}


	@Override
	public String getText() {
		return this.text;
	}
	
}
