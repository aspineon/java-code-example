package terminal.function;

import terminal.transfer.*;
import terminal.transport.Data;
import terminal.transport.Task;
import terminal.transport.Transport;

/** ������ ����� �������� �� ������� �� ������ ������� � ������� �������� � ��������� ������ �� �������*/
public class ResponseBoncardCatalog extends Function{
	/** ������ ���� � ��������, ������� ����� ��������� */
	private String field_path_to_directory;
	
	/**
	 * ������� ���������� �� ������ - ��� �������� ��� �������� �� ������� ���������� �������� "���������" ������ - 
	 * ���������� �� ���������� ������� � ������� �������
	 * @param path_to_directory - ���� � ��������, ������� ����� ���������
	 * @param object_for_process - ������, �������� ����� �������� ������� ���������� ������  (null - �� ��������� )
	 * @param unique_id - ���������� ��� ��� ������� ��������  ( ��� ������� object_for_process)
	 * @param caption - caption ��� ����������� ������������ ����� 
	 */
	public ResponseBoncardCatalog(String path_to_directory,
								  PercentReport object_for_process,
			                      String unique_id,
			                      String caption){
		super(null,
			  object_for_process,
			  unique_id,
			  caption);
		this.field_path_to_directory=path_to_directory;
	}
	
	/**
	 * �������� �� ������� ��� �������� ��� �������� �� ������� ���������� �������� "���������" ������ - 
	 * ���������� �� ���������� ������� � ������� ������� 
	 * @param path_to_server ������ ���� � �������
	 * @param path_to_directory - ���� � ��������, ������� ����� �������� �� ������
	 * @param object_for_process - ������, �������� ����� �������� ������� ���������� ������  (null - �� ��������� )
	 * @param unique_id - ���������� ��� ��� ������� �������� ( ��� ������� object_for_process)  
	 *
	public ResponseBoncardCatalog(String path_to_server,
			                     String path_to_directory, 
			                     PercentReport object_for_process,
			                     String unique_id,
			                     String caption ){
		super(path_to_server,object_for_process,unique_id,caption);
		this.field_path_to_directory=path_to_directory;
	}
	*/
	

	@Override
	public void run() {
		debug("run:begin");
		notifyReportPercent(5);
		Transport response=this.sendFirstTask();
		debug("show result");
		try{
			if((response.isDirectionFromServer())&&(response.getTask().isStateIsDone())){
				debug("response is DONE ");
				notifyReportPercent(100);
			}else{
				debug("response is ERROR ");
				notifyReportPercent(-1);
			}
		}catch(NullPointerException ex){
			// response.getTask()==null ��� response.getTask().getData()==null
			error(ex.getMessage());
			notifyReportPercent(-1);
		}
		debug("run:end");
		
	}

	
	
	/** ������� ������, ������� ����� ������ �� ������ � �������� ������������� ������� �� ��������� �������� */
	@Override
	protected Transport getFirstTransport() {
		Transport request=new Transport(new Task("CLIENT_RESPONSE_BONCARD_CATALOG",
				                        new Data(this.field_path_to_directory))
		                                );
		request.setDirectionFromClient();
		return request;
	}
	
}













