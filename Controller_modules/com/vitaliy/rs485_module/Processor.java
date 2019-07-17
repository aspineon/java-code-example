package com.vitaliy.rs485_module;
import java.io.*;
/**
 * �������� �� ������� � ��������� ������, ���������� ������ � �������
 * ������������ ����������� ������, ��� �� �� �����������
 * ����� ����� -  
 */
public class Processor{
	private Queue queue;// ������� ������
	private OutputStream outputstream;
	private Timer timer;// ���� ������ � ��������� ����� (Timer.isRun()), ������ �� ���� ������ ������ �� �����
	private int delay_wait_answer=200;// ����� �������� ������ �� ����� � ������������
	private int unique_number=0;// ������� ���������� ������
	private int task_index=(-1);
	private Command current_command=null;
	private Ievent_work current_event_work=null;

	/**
	 * �����, � ������� ����� ���������� �������
	 * @param _outputstream
	 */
	public Processor(OutputStream _outputstream){
		this.outputstream=_outputstream;
		this.queue=new Queue();
	}
	/**
	 * �������� ������ � ������� �����
	 */
	public void add_to_queue(Command object_of_command,int time_delay,Ievent_work _event_work){
		//(String _name_unique, Command _object_of_command, int _time_delay,Ievent_work _event_work){
		String unique_name="";
		try{
			String temp_name=object_of_command.getClass().getName().trim();
			int dot_position=temp_name.lastIndexOf(".");
			if(dot_position>0){
				unique_name=this.get_unique_name(temp_name.substring(dot_position+1));
			}
			else{
				unique_name=this.get_unique_name(temp_name);
			}
		}
		catch(Exception e){
			unique_name=this.get_unique_name("");
		};
		
		if(time_delay<0){
			time_delay=0;
		}
		this.queue.add(unique_name, object_of_command, time_delay, _event_work);
	}
	/**
	 * �������� ������ �� ������� � �� �������� �������
	 * @param delete_index
	 */
	public void delete_from_queue(int delete_index){
		this.queue.delete_by_index(this.task_index);// ������� ������ �� �������
		this.current_command=null;
		this.current_event_work=null;
		if(this.timer.isRun()){
			this.timer.breakTimer();
		}
		this.timer=null;
		System.out.println("Processor.delete has_next_task?"+this.queue.length());
	}
	/**
	 * �������� ���������� ��� ������� ��� �������������
	 * ��� ����������� �����:  [��� �������][.][�������� ��������]
	 */
	private String get_unique_name(String command_name){
		if(unique_number>32000){
			unique_number=0;
		}
		return command_name+"."+(++this.unique_number);
	}
	/**
	 * ������ ������� � OutputStream
	 * @param s
	 * @return
	 */
	private boolean send_command(String s){
		boolean result=false;
		try{
			this.outputstream.write(s.getBytes());
			this.outputstream.write(13);
			this.outputstream.write(10);
			this.outputstream.flush();
		}
		catch(Exception e){
			result=false;
		}
		return result;
	}
	/**
	 * �������� ������ ������� �� ��� ����������� �������
	 */
	private Command get_command_from_queue(int index_of_command){
		return this.queue.get_object_of_command_by_index(index_of_command);
	}
	/**
	 * ����� ����� � ������ - ��������� �������� �������� - ������ �� ����� 
	 */
	public void response_process(String response){
		// �� ������� ������ �� �����?
		if((this.timer!=null)&&(this.timer.isRun())){
			if(response.length()>0){
				// ������ �� ������ ����� - ����� ���������� �������� ������
				if(this.current_event_work!=null){
					System.out.println("Processor.response_process: ������ �� ������ �����"+response);
					this.current_event_work.event_work(this.get_command_from_queue(this.task_index),response);
				}
				// ������� ������
				this.delete_from_queue(this.task_index);
			}
			else {
				// ������ ��� - ������� 
			}
		}
		else {
			// ���, �� ������� - ����� �������� ����� ������� - ������ �� ����
			// ��� ����� ����� - ������ �� �������, 
			if(this.current_command==null){
				// ������ �� ���� - ����� ��������� ����� ������ � �������
				if(queue.has_next_task()){
					// ������ ���� - ���������
					this.task_index=this.queue.get_index_of_next_task();
					this.current_command=this.queue.get_object_of_command_by_index(this.task_index);
					this.current_event_work=this.queue.get_object_of_work_by_index(this.task_index);
					// ������� ����� �� �������?
					if(this.current_command!=null){
						// ��, ������� ����� �� ������� 
						   //- ������������� ������
						this.timer=new Timer(this.delay_wait_answer);
						   //- �������� ������ � ����
						System.out.println("������� ������ � ����:");
						this.send_command(this.current_command.get_command());
					}
					else{
						// ��� ������� ��� ��������- ����� ������� ������ ������� � ������� ������
						this.delete_from_queue(this.task_index);
					}
				}
				else{
					// ������ ���, ������� ���� ������ ����� ��������� � �������
				}
			}
			else {
				// current_command!=null - ������ �� �������
				   // ������ ������ ����� - 
				if(this.current_event_work!=null){
					this.current_event_work.event_work(this.get_command_from_queue(this.task_index),"");
					System.out.println("������ �� �������");
				}
				// ������� ������ �� �������
				this.delete_from_queue(this.task_index);
			}
		}
	}
	
}
