package bonpay.jobber.launcher.executor;

/** ������ �� ��������� ��������� ����� ��� ���������� */
public interface IExecutorAware {
	/** �������� ������������ ��� ������� */
	public IExecutor[] getExecutors();
}
