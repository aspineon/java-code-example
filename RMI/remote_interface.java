import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * ���������, ������� ���� � �� ������� � �� �������, �������� ��������� �������
 */
public interface remote_interface extends java.rmi.Remote{
	public String getString(String prefix) throws java.rmi.RemoteException;
}