package bonpay.jobber.launcher.executor;

/** ���������, ������� �������� �� ������ ����� - ������ ������ ��� ����������� ����� ����� ���� �� ��������� �� ���������� */
public interface IExecutor {
	/** ��������� ������ */
	public void execute();
	/** ���������� ������ */
	public void stop();
}
