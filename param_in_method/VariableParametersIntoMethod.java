package param_in_method;

public class VariableParametersIntoMethod {
	
	/**   
	 * ������������ ��������� ��������� �������� � �������� ���������� ����������� ����� ��������
	 * 
	 * */
	public static void main(String[] args){
		System.out.println("���������� ���-�� ���������� �� �������: "+checkParameters());
		System.out.println("���������� ���-�� ���������� ����� null: "+checkParameters(null));
		System.out.println("���������� ���-�� ���������� ����� 2 ���������: "+checkParameters("1","2"));
		System.out.println("���������� ���-�� ���������� ����� ������� �� ���� ���������: "+checkParameters(new Object[]{"3","4","5"}));
		
	}
	
	private static String checkParameters(Object ... objects){
		if(objects==null){
			return "NULL";
		}else{
			return Integer.toString(objects.length);
		}
	}
}
