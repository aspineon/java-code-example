package logger.utility;

/** �������, ������� ����� ��������� ������ */
public interface ILoggerCondition {
	/** �������, ������� ��������� ������
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - true - ���������� �������� ������ </li>
	 * 	<li><b>false</b> - false - ��� ����� ��������� ������ </li>
	 * </ul>
	 *  */
	public boolean condition();
}
