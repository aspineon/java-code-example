package logger.utility.condition;

import java.util.Calendar;

import logger.utility.ILoggerCondition;

/** �����, ������� ���������� ���� ��������� ������� � ������ ��������� ���� (������� ������ - ��������� ��� � ����)*/
public class DateChanger implements ILoggerCondition{
	/** ������� ����, �� �������� ���������� ��� */
	private int day;
	
	/** �����, ������� ���������� ���� ��������� ������� � ������ ��������� ���� (������� ������ - ��������� ��� � ����)*/
	public DateChanger(){
		this.day=this.getCurrentDay();
	}
	
	private int getCurrentDay(){
		 Calendar calendar=Calendar.getInstance();
		 return calendar.get(Calendar.DAY_OF_YEAR);
	}
	
	@Override
	public boolean condition() {
		int currentDay=this.getCurrentDay();
		if(day!=currentDay){
			this.day=currentDay;
			return true;
		}else{
			return false;
		}
	}

}
