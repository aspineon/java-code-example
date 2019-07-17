package calc;


import java.util.Date;

import message.AlarmMessage;

/** ����������� ��� String �������� */
public abstract class AStringChecker extends AbstractChecker{
	private final static long serialVersionUID=1L;
	
	/** �������� � ������� ����� ��������� ��������� */
	private String controlValue;
	/** ����, ������� ������� � ������������� ������������� Case � �������  */
	private boolean ignoreCase;
	
	/** ����������� ��� String �������� 
	 * @param delayMs - ����� �������� ��� ��������� �������� 
	 * @param message - ���������, ������� ����� ���� ������
	 * @param controlValue - ����������� ��������, � ������� ���������� ��������� ��������� 
	 * @param ignoreCase - ����� �� ������������ Case ("one"=="OnE")
	 */
	public AStringChecker(int delayMs, String message, String controlValue, boolean ignoreCase) {
		super(delayMs, message);
		this.controlValue=controlValue;
		this.ignoreCase=ignoreCase;
	}

	/** �������� ����������� �������� */
	protected String getControlValue(){
		return this.controlValue;
	}
	
	/** ����� �� ������������ case � ������� */
	protected boolean isIgnoreCase(){
		return this.ignoreCase;
	}
	
	/**
	 * @param value - ��������, ������� ������ ������
	 * @return 
	 * */
	protected abstract boolean isAlarm(String value); 
	
	@Override
	protected AlarmMessage calculate(String value) {
		try{
			if(isAlarm(value)){
				this.setDateEvent();
				return new AlarmMessage(new Date(),this.alarmDescription,value); 
			}else{
				return null;
			}
		}catch(Exception ex){
			System.err.println("AStringChecker Exception: "+ex.getMessage());
			return null;
		}
	} 

	
	
}
