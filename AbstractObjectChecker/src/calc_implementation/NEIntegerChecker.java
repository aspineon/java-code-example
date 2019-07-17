package calc_implementation;

import calc.AIntegerChecker;

/**  �������� �� �� ��������� ������� */
public class NEIntegerChecker extends AIntegerChecker{
	private final static long serialVersionUID=1L;

	/**  �������� �� �� ��������� �������  
	 * @param delayMs - �������� ����� ��������� �������������� ������� 
	 * @param message - ���������, ������� ������� �������� 
	 * @param controlValue - ����������� �������� (�������� ��� ��������� )
	 */
	public NEIntegerChecker(int delayMs, String message, int controlValue) {
		super(delayMs, message,controlValue);
	}

	@Override
	protected boolean isAlarm(int value) {
		if(controlValue!=value){
			return true;
		}else return false;
	}

}
