package calc_implementation;

import calc.AStringChecker;

/** �������� �� �� ��������� ����� */
public class NEStringChecker extends AStringChecker{
	private final static long serialVersionUID=1L;
	
	/** �������� �� �� ��������� ����� 
	 * @param delayMiliseconds - �������� � ������������ ����� ��������� ������� ����� ���������� �������
	 * @param alarmDescription - ���������, ������� ����� ������ � ������ ��������� �������
	 * @param controlValue - ��������, � ������� ����� ���������� �������� 
	 * @param ignoreCase - ������������ �� c 
	 */
	public NEStringChecker(int delayMiliseconds, String alarmDescription, String controlValue, boolean ignoreCase) {
		super(delayMiliseconds, alarmDescription, controlValue, ignoreCase);
	}

	@Override
	protected boolean isAlarm(String value) {
		try{
			if(this.isIgnoreCase()){
				return !this.getControlValue().equalsIgnoreCase(value);
			}else{
				return !this.getControlValue().equals(value);
			}
		}catch(Exception ex){
			System.err.println("EQStringChecker Exception:"+ex.getMessage());
			return false;
		}
	}


}
