
public class Child extends Base{
	// ���� ������� ������� - ���������� �������� �������� �� �� �������������, ����� ��������� � ��� �� ������������ �������� ������
	public static void main(String[] args){
		new Child();
	}
	
	public Child(){
		System.out.println("Child constructor");
		System.out.println("Object:"+valueObject);
		System.out.println("IntValue:"+valueInt);
	}

	private Object valueObject=new Object();
	private int valueInt=5;
	
	{
		System.out.println("Child anonim area");
	}
	
	@Override
	protected void initParameters() {
		System.out.println("Object:"+valueObject);
		System.out.println("IntValue:"+valueInt);
	}
}
