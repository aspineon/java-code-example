package calc_implementation;

import calc.AIntegerChecker;

/** �������� �� ������ */
public class LTIntegerChecker extends AIntegerChecker{
	private final static long serialVersionUID=1L;
	
	/**  �������� �� ������    
	 * @param delayMs - �������� ����� ��������� �������������� ������� 
	 * @param message - ���������, ������� ������� �������� 
	 * @param controlValue - ����������� �������� (�������� ��� ��������� )
	 */
	public LTIntegerChecker(int delayMs, String message, int controlValue) {
		super(delayMs, message,controlValue);
	}

	@Override
	protected boolean isAlarm(int value) {
		if(value<controlValue){
			return true;
		}else return false;
	}

}
