package inner_class_create_object;

public class InnerClass {
	{
		// �� ����� ���������������� ����������� ���� ��� ����������� ����� 
		class AnotherClass{
			public void temp(){
			// 	System.out.println("AnotherClass static constructor");
			}
		}
	}
	
	public static void main(String[] args){
		InnerClass object=new InnerClass();
		
	}
}
