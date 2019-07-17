package calc;

import java.io.Serializable;
import java.util.Date;

import message.AlarmMessage;


/** ����������� ����� ��� ���������� */
public abstract class AbstractChecker implements Serializable{
	private final static long serialVersionUID=1L;
	
	/** �����, ����� ����������� �������� ����� ��������� � ������ ����������� �� ������� */
	private Date nextEnabledTime=null;
	/** ����� �������� � ������������ */
	protected int delayMiliseconds=0;
	/** ��������� ���������, ������� ����� ������ */
	protected String alarmDescription;
	
	/** ����������� ����� ��� ���������� 
	 * @param delayMiliseconds - ����� ��������, ����� ����������� ������� �� ������� �� ������� "������" Checker � ����� ������������ ������� ������ ����� ��������� ������� ������� 
	 * @param alarmDescription - ������, ������� ����� ������ � {@link AlarmMessage#getDescription()}
	 */
	public AbstractChecker(int delayMiliseconds, String alarmDescription){
		this.delayMiliseconds=delayMiliseconds;
		this.alarmDescription=alarmDescription;
	}

	public AlarmMessage checkForAlarmMessage(String value){
		// �������� �� ��������� ������� �������� ��� ������� 
		if(nextEnabledTime!=null){
			Date currentDate=new Date();
			if(currentDate.after(this.nextEnabledTime)){
				// �������� ����� ��������
				this.clearDateEvent();
				// �������� ��������� �������� 
				return calculate(value);
			}else{
				// ����� �������� ��� �� �����  
				return null;
			}
		}else{
			return calculate(value);
		}
	}
	
	/** �� ��������� ����������� ��������� ������� AlarmMessage, ���� �� ������� null */
	protected abstract AlarmMessage calculate(String value);
	
	
	/** �������� ������, ������� ��������� �� ����������� ������� */
	protected void clearDateEvent(){
		this.nextEnabledTime=null;
	}

	/** ���������� ��������� ������� �� ������� ����� ����������� �������, ���������� � {@link #delayMiliseconds}*/
	protected void setDateEvent(){
		this.nextEnabledTime=new Date((new Date()).getTime()+delayMiliseconds);
	}
}
