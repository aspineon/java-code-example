package enumeration;

/** ������ ������ ������������ ����������� ��������/�������� ��������� �������� � Enum ��������, ������� ����������������  */
public class EnumerationParser {
	public static void main(String[] args){
		System.out.println("begin");
		// State state=State.valueOf("begin"); - throws Exception 
		// State state=State.valueOf("first"); - throws Exception 
		State state=State.valueOf("First"); // ������������ � ��������, ������������ � �������� ����� � ������ 
		System.out.println("State:"+state);
		System.out.println("-end-");
	}
}

enum State{
    First, 
    Second, 
    Third;
    
}
