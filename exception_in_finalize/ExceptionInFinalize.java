package exception_in_finalize;

/**
 * ������������ ������ ������ Finalize
 * � ������: ��� �������� ������� Parent, �������� ��������� ������ Child, 
 * ��� ������ ������ ������� ������������ ������ Child, ����� ������ Parent.
 * ��� ����������� ������� Child � ������ Finalize, ������� �������� ������� ������,
 * ����������� ����������
 * ��������� - ���������� � Child ����� �� ������ �� ����� ������� finalize � ������ �������� � �������
 *  
 */
public class ExceptionInFinalize {
	public static void main(String[] args){
		System.out.println("begin");
		System.out.println("-end-");
		new Parent();
		System.gc();
	}
}

class Child{
	public Child(){
		System.out.println("Child create:"+this.toString());
	}
	
	protected void finalize() throws Throwable {
		System.out.println("Child finalize:"+this.toString());
		throw new RuntimeException("");
	};
}


class Parent{
	public Parent(){
		System.out.println("Parent create:"+this.toString());
		new Child();
	}
	
	protected void finalize() throws Throwable {
		System.out.println("Parent finalize:"+this.toString());
		super.finalize();
	};
}
