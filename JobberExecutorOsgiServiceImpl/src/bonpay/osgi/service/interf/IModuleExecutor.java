package bonpay.osgi.service.interf;

/** ���������, ������� ������������ IExecutor-� �� ������� Launcher */
public interface IModuleExecutor {
	/** �������� ������, ������� ����� ��������� �� ����� 
	 * @param executorName - ���-�������������
	 * @param executor - ����������� ������
	 * @return 
	 * <li> <b>true</b> - ������ ��������</li>
	 * <li> <b>false</b> - ������ ���������� </li>
	 */
	public boolean addModuleExecutor(String executorName, IExecutor executor);
	/** ������� Executor �� ��������� ����������� ����� */
	public boolean removeModuleExecutor(String executorName);
	/** ������� Executor �� ��������� ����������� ������� */
	public boolean removeModuleExecutor(IExecutor executor);
	/** �������� Executor �� ����������� ����� 
	 * @param executorName - ���������� ��� ����������� 
	 * @return null - ���� ������ ��� �� ������� 
	 */
	public IExecutor getExecutorByName(String executorName);
	/** �������� ����� ���� Executor-��
	 * @return - ��� ������������������ ����� � ������� 
	 */
	public String[] getNameOfExecutors();
	
}
