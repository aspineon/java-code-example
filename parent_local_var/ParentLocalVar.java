package parent_local_var;


/** ������������ ����������/������������� �� ������ ������� ����������������  
 * �����, �� ����� ���������� �������, ���� � ������������ ������� ������ ��� ����� � ������ ������ �� �����������
 * ( �� ���� ��������� ���� � ������������ ������ 
 *  
 * 
 * */
public class ParentLocalVar {
	
	public static void main(String[] args){
		System.out.println("begin");
		Child child=new Child();
		System.out.println("ClassName: "+child.getInitClassName());
		System.out.println("-end-");
	}
}

class Parent{
	// 1
	private String initClassName=this.getClass().getName(); // Child !!!
	
	{
		// 2 
		initClassName=initClassName;
	}
	public String getInitClassName(){
		return this.initClassName;
	}
}

class Child extends Parent{
	private int value;
	public Child(){
		// 3
		value=5;
	}
}
