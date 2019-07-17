package calc;


import java.math.BigDecimal;
import java.util.Date;

import message.AlarmMessage;

/** ����������� ��� Double �������� */
public abstract class ADoubleChecker extends AbstractChecker{
	private final static long serialVersionUID=1L;
	/** ����������� �������� */
	private double controlValue;
	/** ����, �� �������� ������� ��������� */
	private int digitForRound;
	
	/** ����������� ��� Double �������� 
	 * @param delayMs - ����� �������� ��� ��������� �������� 
	 * @param message - ���������, ������� ����� ���� ������
	 * @param controlValue - ����������� �������� ��� ���������
	 * @param digit - ������, �� �������� ������� ��������� �������� (-1 - ���������� �� ���������)
	 * <li><b>-1</b>���������� �� ��������� </li>
	 * <li><b>>=0</b>��������� �� ���������� ����� </li>
	 */
	public ADoubleChecker(int delayMs, String message,double controlValue, int digit) {
		super(delayMs, message);
		this.controlValue=controlValue;
		this.digitForRound=digit;
	}

	/** �������� ����������� ��������, � ������� ����� ���������� �������� */
	protected double getControlValue(){
		return this.controlValue;
	}
	
	/** �������� ���-�� ������, �� ������� ����� ��������� �������� */
	protected int getDigit(){
		return this.digitForRound;
	}
	
	/** ��������� �������� �� ���������� �����  */
	protected double round(double d, int decimalPlace){
		if(decimalPlace<0){
			return d;
		}else{
		    // see the Javadoc about why we use a String in the constructor
		    // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
		    BigDecimal bd = new BigDecimal(Double.toString(d));
		    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
		    return bd.doubleValue();
		}
	  }	/**
	 * @param value - ��������, ������� ������ ������
	 * @return 
	 * */
	protected abstract boolean isAlarm(double value); 
	
	@Override
	protected AlarmMessage calculate(String value) {
		try{
			double intValue=Double.parseDouble(value.trim());
			if(isAlarm(intValue)){
				this.setDateEvent();
				return new AlarmMessage(new Date(),this.alarmDescription,value); 
			}else{
				return null;
			}
		}catch(Exception ex){
			System.err.println("ADoubleChecker Exception: "+ex.getMessage());
			return null;
		}
	} 

	
	
}
