import java.rmi.*;
/**
 * 
 */
public class client{
	// ���� �� ������� ���������� ������
	public static int PORT=2001;
	// ��� �������, ���� �������� ������
	public static String SERVER_NAME="Example";
	public static void main(String args[]) throws Exception{
		// ������ ������� � ������� "//HOST:PORT/SERVICE_NAME"
		String lookup_string="//localhost:"+Integer.toString(PORT)+"/"+SERVER_NAME;
		System.out.println("URL:"+lookup_string);
		// �������� ������ �� ���������, ��������� ��������� ����� ����� URL
		// �� ����� ���� ���� �� ��������� ���������� �������� � ����� ��� �������� ���������
		// �� ������� ������ ���� � �������� � �������� ����������
		remote_interface remote=(remote_interface) Naming.lookup(lookup_string);
		System.out.println("remote_interface getting");
		System.out.println("RESULT:"+remote.getString("temp_value"));		
	}
}