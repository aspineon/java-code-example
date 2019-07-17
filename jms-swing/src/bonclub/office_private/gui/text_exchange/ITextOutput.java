package bonclub.office_private.gui.text_exchange;

/** ��������� �� �������� ���������� ���������  */
public interface ITextOutput {
	/** ��������� ��������� ��������� 
	 * @param message - ��������� ���������
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - ������� ���������� </li>
	 * 	<li><b>false</b> - �� ���������� </li>
	 * </ul>
	 * */
	public boolean sendText(String message);
}
