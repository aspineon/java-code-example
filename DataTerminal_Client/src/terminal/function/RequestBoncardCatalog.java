package terminal.function;

import terminal.transfer.*;
import terminal.transport.Task;
import terminal.transport.Transport;

/** ������ ����� �������� �� ������� �� ������ ������� � ������� �������� � ��������� ������ �� �������*/
public class RequestBoncardCatalog extends Function{
	/** ������, �������� ����� �������� ���������� ������� */
	private GetString field_return_response=null;
	
	
	/**
	 * �������� �� ������� ��� �������� ��� �������� �� ������� ���������� �������� "���������" ������ - 
	 * ���������� �� ���������� ������� � ������� �������
	 * @param object_for_answer - ������, �������� ����� �������� ��������� ����������
	 * @param object_for_process - ������, �������� ����� �������� ������� ���������� ������  (null - �� ��������� )
	 * @param unique_id - ���������� ��� ��� ������� ��������  ( ��� ������� object_for_process)
	 * @param caption - caption ��� ����������� ������������ ����� 
	 */
	public RequestBoncardCatalog(GetString object_for_answer, 
			                     PercentReport object_for_process,
			                     String unique_id,
			                     String caption){
		super(null,object_for_process,unique_id,caption);
		this.field_return_response=object_for_answer;
	}
	
	/*
	 * �������� �� ������� ��� �������� ��� �������� �� ������� ���������� �������� "���������" ������ - 
	 * ���������� �� ���������� ������� � ������� ������� 
	 * @param path_to_server ������ ���� � �������
	 * @param object_for_answer - ������, �������� ����� �������� ��������� ����������
	 * @param object_for_process - ������, �������� ����� �������� ������� ���������� ������  (null - �� ��������� )
	 * @param unique_id - ���������� ��� ��� ������� �������� ( ��� ������� object_for_process)  
	 *
	public RequestBoncardCatalog(String path_to_server,
			                     GetString object_for_answer, 
			                     PercentReport object_for_process,
			                     String unique_id,
			                     String caption ){
		super(path_to_server,object_for_process,unique_id,caption);
		this.field_return_response=object_for_answer;
	}
	*/
	

	@Override
	public void run() {
		debug("run:begin");
		notifyReportPercent(5);
		Transport response=this.sendFirstTask();
		try{
			if((response.isDirectionFromServer())&&(response.getTask().isStateIsDone())){
				
				debug("response is DONE:"+(String)response.getTask().getData().getObject());
				this.field_return_response.getString(this.getUniqueId(), 
													(String)response.getTask().getData().getObject());
				
				notifyReportPercent(100);
			}else{
				debug("response is ERROR ");
				this.field_return_response.getString(this.getCaption());
				notifyReportPercent(-1);
			}
		}catch(NullPointerException ex){
			// response.getTask()==null ��� response.getTask().getData()==null
			this.field_return_response.getString(this.getCaption());
			error(ex.getMessage());
			notifyReportPercent(-1);
		}
		debug("run:end");
	}

	
	
	/** ������� ������, ������� ����� ������ �� ������ � �������� ������������� ������� �� ��������� �������� */
	@Override
	protected Transport getFirstTransport() {
		Transport request=new Transport(new Task("CLIENT_REQUEST_BONCARD_CATALOG"));
		request.setDirectionFromClient();
		return request;
	}
	
}













