package reader;

/** ���������� � ��������� ������ ������ */
public interface IOutputBlockListener {
	/** ���������� � ��������� ������ ������ 
	 * @param array - ��������� ���� 
	 *  */
	public void notifyBlock(byte[] array);
}
