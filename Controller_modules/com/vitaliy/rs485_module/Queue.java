package com.vitaliy.rs485_module;

import java.util.Enumeration;
import java.util.Vector;

/**
 * �������� �� ������ � �������� ������ 
 */
public class Queue implements Ievent_timer{
	/**
	 * ���������� ����� ������ ��� �� �������������
	 */
	private Vector name_unique;
	/**
	 * ��� ������, ������� ����� ����������� � ������ (name_unique) � �������������� 
	 */
	private Vector object_of_command;
	/**
	 * ����� �������� - (Integer) ���� ����� ����, ����� ����� ������������ ������� 
	 */
	private Vector time_delay;
	/**
	 * ������, ��������� �������� ����� ������, ��� ��������� ���������� ������  
	 */
	private Vector object_of_work;
	/**
	 * ������, ������� �������� �������, ������� ����������� ����� ��� ��������� ��������
	 */
	private Vector timer;
	/**
	 * �����������
	 */
	public Queue(){
		this.name_unique=new Vector();
		this.object_of_command=new Vector();
		this.time_delay=new Vector();
		this.object_of_work=new Vector();
		this.timer=new Vector();
	}
	/**
	 * ���������� ������ � ������� 
	 */
	public void add(String _name_unique, Command _object_of_command, int _time_delay,Ievent_work _event_work){
                this.name_unique.addElement(_name_unique);
		this.object_of_command.addElement(_object_of_command);
		this.time_delay.addElement(new Integer(_time_delay));
		this.object_of_work.addElement(_event_work);
		if(_time_delay>0){
			this.timer.addElement(new Timer(_time_delay,this,_name_unique));
		}
	}
	/**
	 * �������� ������ ������ �� �� ����������� �����
	 */
	public int get_index_by_name_unique(String name){
		return this.name_unique.indexOf(name);
	}
	/**
	 * ������� ������ �� ������� �� ������� 
	 */
	public void delete_by_index(int index_for_delete){
		if(index_for_delete>=0){
			this.name_unique.removeElementAt(index_for_delete);
			this.object_of_command.removeElementAt(index_for_delete);
			this.time_delay.removeElementAt(index_for_delete);
			this.object_of_work.removeElementAt(index_for_delete);
			if(this.timer.elementAt(index_for_delete)!=null){
				((Timer)this.timer.elementAt(index_for_delete)).breakTimer();
			}
			this.timer.removeElementAt(index_for_delete);
		}
	}
	/**
	 * ���������� ����� ����� ���������� ������ �� ����������� �������
	 */
	public void set_time_delay_by_unique_name(String name,int delay){
		int index_edit=this.get_index_by_name_unique(name);
		// �������� �� ������� � ������� ������
		if(index_edit>=0){
			if(delay<=0){
				((Timer)this.timer.elementAt(index_edit)).breakTimer();
				this.timer.setElementAt(null,index_edit);
				this.time_delay.setElementAt( new Integer(0),index_edit);
			}
			else{
				((Timer)this.timer.elementAt(index_edit)).breakTimer();
				this.timer.setElementAt(new Timer(delay,this,name),index_edit);
				this.time_delay.setElementAt(new Integer(delay),index_edit);
			}
			
		}
	}
	/**
	 * �������� ��� ������ �� �������
	 */
	public String get_name_of_class_by_index(int index){
		String result="";
		try{
			result=((Command)this.object_of_command.elementAt(index)).get_name().trim();
			if((result!=null)&&(result!="")){
				int dot_position=result.lastIndexOf('.');
				if(dot_position>=0){
					result=result.substring(dot_position);
				}
			}
		}
		catch(Exception e){
			result="";
		}
		return result;
	}
	/**
	 * �������� ������ ������� �� �������
	 */
	public Command get_object_of_command_by_index(int index){
		try{
			return (Command)this.object_of_command.elementAt(index);
		}
		catch(Exception e){
			return null;
		}
	}
	/**
	 * �������� ������, ��������� �������� ����� ������� ��� ��������� ��������, �� �������
	 */
	public Ievent_work get_object_of_work_by_index(int index){
		return (Ievent_work) this.object_of_work.elementAt(index);
	}
	/**
	 * �������� ����� �������� ���������� �������� �� �������
	 */
	public int get_time_delay_by_index(int index){
		return ((Integer)this.time_delay.elementAt(index)).intValue();
	}
	/**
	 * @return ���������� �������� ���� �� ������, ������� ����� ��������� 
	 */
	public boolean has_next_task(){
		boolean result=false;
		Enumeration enumeration=this.time_delay.elements();
		while(enumeration.hasMoreElements()){
			if(((Integer)enumeration.nextElement()).intValue()==0){
				result=true;
				break;
			}
		}
		return result;
	}
	/**
	 * ���������� ������ ������, ������� ���������� ���������
	 */
	public int get_index_of_next_task(){
		int result=-1;
		if(this.has_next_task()){
			// �������� �������� ���������� ����
			Enumeration enumeration=this.name_unique.elements();
			// ��������� ��� ���������� �����
			while(enumeration.hasMoreElements()){
				// �������� ������ �� ���������� �����
				int index=this.get_index_by_name_unique((String)enumeration.nextElement());
				if(this.get_time_delay_by_index(index)==0){
					// ����� � ������� ������� ����� 0
					result=index;
					break;
				}
			}
		}
		return result;
	}
	/**
	 * ���������� ��� ��������
	 */
	public void print(){
                Enumeration enumeration=this.name_unique.elements();
		while(enumeration.hasMoreElements()){
			String unique_name=(String)enumeration.nextElement();
			int index=this.get_index_by_name_unique(unique_name);
			System.out.print(" Name unique:"+unique_name);
			System.out.print(" Name of class:"+this.get_name_of_class_by_index(index));
			System.out.print(" Object_of_command:"+(this.get_object_of_command_by_index(index)));
			System.out.print(" Object_of_work:"+(this.get_object_of_work_by_index(index)));
			utility.println(" Delay:"+this.get_time_delay_by_index(index));
		}
	}
	/**
	 * ���������� ������� �� �������
	 * @param print_index
	 */
	public void print_by_index(int print_index){
                Enumeration enumeration=this.name_unique.elements();
		while(enumeration.hasMoreElements()){
			String unique_name=(String)enumeration.nextElement();
			int index=this.get_index_by_name_unique(unique_name);
			if(index==print_index){
				System.out.print(" Name unique:"+unique_name);
				System.out.print(" Name of class:"+this.get_name_of_class_by_index(index));
				System.out.print(" Object_of_command:"+(this.get_object_of_command_by_index(index)));
				System.out.print(" Object_of_work:"+(this.get_object_of_work_by_index(index)));
				utility.println(" Delay:"+this.get_time_delay_by_index(index));
			}
		}
	}
	/**
	 * ��������� ������������ ������� ��� ������� ������� - ��������� ������� ���������� � 0 
	 * @param event_unique_name
	 */
	public synchronized void event_timer(String event_unique_name) {
		this.set_time_delay_by_unique_name(event_unique_name, 0);
	}
	/**
	 * ���������� True ���� � ������� ��� ���� ��������
	 */
	public boolean isNotEmpty(){
		//return this.name_unique.iterator().hasNext()
		boolean result=false;
		if(this.name_unique.size()!=0){
			result=true;
		}
		return result;
	}
	/**
	 * ���������� ���-�� ����� � �������
	 */
	public int length(){
		return this.name_unique.size();
	}
}
