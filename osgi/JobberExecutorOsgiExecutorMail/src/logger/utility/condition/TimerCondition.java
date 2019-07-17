package logger.utility.condition;

import java.util.Date;

import logger.utility.ILoggerCondition;

/** �����, ������� ����������� �������� ��������� ��������� */
public class TimerCondition implements ILoggerCondition{
	private Date currentDate=new Date();
	private int delay;
	
	/** �����, ������� ����������� �������� ��������� ��������� 
	 * @param delay - �������� � �� ����� ������� ����� ���������� ���� ��������� ������� 
	 * */
	public TimerCondition(int delay){
		this.delay=delay;
	}
	
	@Override
	public boolean condition() {
		boolean returnValue=false;
		Date tempDate=new Date();
		if((currentDate.getTime()+delay)<(tempDate.getTime())){
			this.currentDate=tempDate;
			returnValue=true;
		}
		return returnValue;
	}

}
