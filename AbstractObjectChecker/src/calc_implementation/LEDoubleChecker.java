package calc_implementation;

import calc.ADoubleChecker;

/** �������� �� ������ ��� ����� */
public class LEDoubleChecker extends ADoubleChecker{
	private final static long serialVersionUID=1L;
	
	/**  �������� �� ������ ��� ����� 
	 * @param delayMs - �������� ����� ��������� �������������� ������� 
	 * @param message - ���������, ������� ������� �������� 
	 * @param controlValue - ����������� �������� (�������� ��� ��������� )
	 * @param digit - ����� ������, �� ������� ����� ������ ����������  
	 */
	public LEDoubleChecker(int delayMs, String message, double controlValue, int digit) {
		super(delayMs, message,controlValue, digit);
	}

	@Override
	protected boolean isAlarm(double value) {
		try{
			if( this.round(value, this.getDigit())<=this.round(this.getControlValue(),this.getDigit())){
				return true;
			}else {
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

}
