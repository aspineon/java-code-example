package bonpay.osgi.launcher.executor;

import bonpay.osgi.service.interf.IExecutor;

/** ������ �� ��������� ��������� ����� ��� ���������� */
public interface IExecutorAware {
	/** �������� ������������ ��� ������� */
	public IExecutor[] getExecutors();
}
