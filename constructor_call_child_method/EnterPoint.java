package constructor_call_child_method;

public class EnterPoint {
	/*
	// ���������� ��������� ���� �������� 
	Parent#anonym block
	// ���������� ���� ������������ ��������
	Parent#constructor
	// � ���� ������������ ���������� ����� ( �����, ������������ � ������ ������� )
	call method main ( �� ���� �� ������������ ���������� ����� �� ���������� �������, � ����� ��������� ���������������� )
	// ������������� ������ ������ �������, ������� ��������� - !!! ���������!!!
	Child#methodMain: 
	// ��������� ���� ������������ ������� 
	Child#anonym block
	// ���� ������������ ������������ ������� 
	Child#constructor
	*/
	public static void main(String[] args){
		new Child();
	}
}
