import java.rmi.*;
import java.rmi.registry.*;

public class server{
	// ����
	public static int PORT=2001;
	// ��� �������
	public static String SERVER_NAME="Example";
	public static void main(String args[]) throws Exception{
	// ������, ������� ��������� ��������� ��������� � ����������� �� UnicastRemoteObject
		remote_interfaceImpl remote=new remote_interfaceImpl();
	// �������� ������ � �����
		Registry registry=LocateRegistry.createRegistry(PORT);
		System.out.println("registry created");
		// �������� ������ �������� - �� ��������� � ������ - ���� �� �����
		//registry.rebind("//localhost:"+Integer.toString(PORT)+"/"+SERVER_NAME,remote);
	//����������� ������� ������� �� ����� )
		registry.rebind(SERVER_NAME,remote);
		System.out.println("Server started on "+"//localhost:"+Integer.toString(PORT)+"/"+SERVER_NAME);
	}
}
