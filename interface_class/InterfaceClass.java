package interface_class;

/**
 * �������� �� ����������� ����������� ������� �/��� ������������ ������ ���������� 
 */
public class InterfaceClass {
	public static void main(String[] args){
		// ������ ���������� ����������� ������, ������� �� �������� �����������
		// �� ���� ��������� �� ��������� ���������� ������� ������
		Temp.TempClassInner tempInner=new Temp("").new TempClassInner();
		
		// ������ ����������� ������������ ������, 
		// ������� ��������� �� ��������� ������ ������, � �� ������� 
		ITemp.TempInner value=new ITemp.TempInner("temp");
		System.out.println(value);
		
		ITemp temp=new Temp("����� ��� ��������");
		System.out.println(temp.getDescription());
	}
}

interface ITemp{
	public enum EValues{
		one, two, three;
	}
	
	public static class TempInner{
		private String text;
		public TempInner(String text){
			this.text=text;
		}
		@Override
		public String toString() {
			return text;
		}
	}
	
	public String getDescription();
}

class Temp implements ITemp{
	private String description;
	
	public Temp(String description){
		this.description=description;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}

	public class TempClassInner{
		public TempClassInner(){
			
		}
	}
}
