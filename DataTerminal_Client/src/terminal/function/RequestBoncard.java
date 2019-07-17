package terminal.function;

import terminal.transfer.GetFile;
import terminal.transfer.PercentReport;
import terminal.transport.Data;
import terminal.transport.Task;
import terminal.transport.Transport;

/** ��������� � ������� ��������� ������ ������, ������� ��� �� ���� ���������*/
public class RequestBoncard extends Function{
	/** ��������� �� ���������� ������ */
	GetFile field_get_file;
	/** ���������� �������������� ��� ������, ����� ����� ���� �������� ������������� � ������ �������� */
	String[] field_id_server_files=null;

	/** �������� ��������� �� ����������� ����� 
	 * @param get_file - ��������� �� ���������� ������ �� �����
	 * @param object_for_process - ������, �������� ����� �������� � �������� ����������
	 * @param unique_id - ���������� ������������� ��� ������� ��������
	 * @param caption - ��������� ��� ����������� �������� 
	 * */
	public RequestBoncard(GetFile get_file,
						  PercentReport object_for_process, 
						  String unique_id,
						  String caption) {
		super(object_for_process, unique_id, caption);
		this.field_get_file=get_file;
	}
	
	/** �������� �����, ������� ��� ���� ���������
	 * @param id_files - �������������� ������, ������� ������������ ����� ��������
	 * @param get_file - ��������� �� ���������� ������ �� �����
	 * @param object_for_process - ������, �������� ����� �������� � �������� ����������
	 * @param unique_id - ���������� ������������� ��� ������� ��������
	 * @param caption - ��������� ��� ����������� �������� 
	 * */
	public RequestBoncard(String[] id_files, 
			  			  GetFile get_file,
					      PercentReport object_for_process, 
			  			  String unique_id,
			  			  String caption) {
		super(object_for_process, unique_id, caption);
		this.field_get_file=get_file;
		this.field_id_server_files=id_files;
	}

	@Override
	protected Transport getFirstTransport() {
		Transport return_value=new Transport();
		if((this.field_id_server_files!=null)&&(this.field_id_server_files.length>0)){
			// ����� �������� ����� ��� ��������� ��������
			return_value.addTask(new Task("CLIENT_REQUEST_BONCARD",new Data(this.field_id_server_files)));
		}else{
			// ����� �������� ����� �����
			return_value.addTask(new Task("CLIENT_REQUEST_BONCARD"));
		}
		return return_value;
	}

	@Override
	public void run() {
		this.notifyReportPercent(5);
		Transport response=this.sendFirstTask();
		if(response!=null){
			if(response.getTask()!=null){
				if(response.getTask().getData()!=null){
					try{
						for(int counter=0;counter<response.getTask().getDataCount();counter++){
							debug("counter:"+counter);
							this.field_get_file.getFile("CLIENT_REQUEST_BONCARD", 
									    			    response.getTask().getData(counter).getDataName(), 
									    			    (byte[])response.getTask().getData(counter).getObject()
									    			    );
							notifyReportPercent((100-5)/response.getTask().getDataCount()*(1+counter));
						}
						notifyReportPercent(100);
					}catch(Exception ex){
						error(" exception GetFile:"+ex.getMessage());
						notifyReportPercent(-1);
					}
				}else{
					error("server Transport.Task.Data is null");
					notifyReportPercent(-1);
				}
			}else{
				error("server Transport.Task is null");
				notifyReportPercent(-1);
			}
		}else{
			error("server Transport null");
			notifyReportPercent(-1);
		}
	}
}
