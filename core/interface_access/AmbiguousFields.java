package interface_access;

public class AmbiguousFields implements IPrintable, IPrint{

	/**
	 �����: ���� ������ ��������� ���������� � ����������� ������,
	 �� ������ �������� ������ ����������� � ���������� ����������
	 ( ����� - ambiguous field access )
	 */
	public static void main(String[] args){
		System.out.println("begin");
		AmbiguousFields value=new AmbiguousFields();
		// System.out.println(""+(value).DESTINATION);
		System.out.println(""+((IPrint)value).DESTINATION);
		System.out.println(""+((IPrintable)value).DESTINATION);
		System.out.println("-end-");
	}
	
	@Override
	public void print() {
		System.out.println(this);
	}

}
