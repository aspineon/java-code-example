package terminal.transport;

import java.io.Serializable;
import java.util.ArrayList;

public class Transport implements Serializable{
	/** */
	private static final long serialVersionUID = 1L;
	/** ��������� ��� ����� */
	private ArrayList<Task> field_task=new ArrayList<Task>();
	
	/** ���� ����������� - �� ������� � �������, ���� �� ������� � ������� */
	private int field_flag_direction=0;
	private static int DIRECTION_FROM_CLIENT=0;
	private static int DIRECTION_FROM_SERVER=1;
	
	
	/** ��������� ��� ������� ����� �������� � �������� */
	public Transport(){
	}
	
	/** ��������� ��� ������� ����� �������� � �������� */
	public Transport(Task task){
		this.addTask(task);
	}
	
	/** �������� ������ � ����� */
	public void addTask(Task task){
		this.field_task.add(task);
	}
	
	/** �������� ������ ����� �� ������, ���� ��� ���� 
	 * @return List<Task>[0] ��� null, ���� ������ ����� ���� 
	 *  
	 */
	public Task getTask(){
		if(this.getTaskCount()>0){
			return this.getTask(0);
		}else{
			return null;
		}
	}
	
	/** �������� ������ �� ������ */
	public Task getTask(int number){
		if((number<this.getTaskCount())&&(number>=0)){
			return this.field_task.get(number);
		}else{
			return null;
		}
	}
	
	/** �������� ���-�� ����� */
	public int getTaskCount(){
		return this.field_task.size();
	}
	
	/** ���������� ����������� �� ������� � ������� */
	public void setDirectionFromClient(){
		this.field_flag_direction=Transport.DIRECTION_FROM_CLIENT;
	}
	
	/** ���������� ����������� �� ������� � ������� */
	public void setDirectionFromServer(){
		this.field_flag_direction=Transport.DIRECTION_FROM_SERVER;
	}
	
	/** ����������� �� ������� � ������� ?*/
	public boolean isDirectionFromClient(){
		return (this.field_flag_direction==Transport.DIRECTION_FROM_CLIENT);
	}
	
	/** ����������� �� ������� � ������� ?*/
	public boolean isDirectionFromServer(){
		return (this.field_flag_direction==Transport.DIRECTION_FROM_SERVER);
	}
}
