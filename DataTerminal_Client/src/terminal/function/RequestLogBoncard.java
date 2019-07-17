package terminal.function;

import terminal.transfer.GetTask;
import terminal.transfer.PercentReport;
import terminal.transport.Task;
import terminal.transport.Transport;

/** ��������� Log ��� ���� � ������� */
public class RequestLogBoncard extends Function{
	
	/** ������, ������� �������� ��������� ��������� Task � ����������� ���������� � ������������� ������� */
	private GetTask field_task_table;
	
	/**
	 * ��������� ��� �������� �����/���������� ������� � �������
	 * @param task_table - ������, ������� ��������� ��������� �������������� Task � JTable
	 * @param object_for_process ������, ������� ������������ � �������� ����������
	 * @param unique_id - ���������� ������������� ��� ����������� � ����������� �������� 
	 * @param caption - ��������� ��� ������ ���������� � �������� 
	 */
	public RequestLogBoncard(GetTask task_table,
							 PercentReport object_for_process,
							 String unique_id, 
							 String caption) {
		super(object_for_process, 
			  unique_id, 
			  caption);
		this.field_task_table=task_table;
	}

	@Override
	protected Transport getFirstTransport() {
		Transport return_value=new Transport(new Task("CLIENT_REQUEST_LOG_BONCARD"));
		return return_value;
	}

	@Override
	public void run() {
		notifyReportPercent(5);
		Transport request=this.sendFirstTask();
		if((request!=null)&&(request.getTask()!=null)){
			this.field_task_table.getTask(request.getTask());
			notifyReportPercent(100);
		}else{
			notifyReportPercent(-1);
		}
	}
}
