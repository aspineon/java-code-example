package variable_as_pointer;

public class VarAsPointer {
	/** ������������� ������������ ������ ��������� ������� (������������ ����� ��������� � ������ ���������� ) */
	public static void main(String[] args){
		System.out.println("begin");
		int[] tempValue=new int[]{5,12,14 };
		
		int[] value1=tempValue;
		int[] value2=value1;
		
		value1=new int[]{1};
		
		System.out.println("Value1: "+value1.length); // ��������� �� ����� ������ �� ������ �������� 
		System.out.println("Value2: "+value2.length); // ��������� �� ������ ������ �� ���� ��������� 
		System.out.println("-end-");
	}

}
