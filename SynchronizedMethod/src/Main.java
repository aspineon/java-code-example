import java.util.concurrent.TimeUnit;

/**
 * ������ ������ ������������� Synchronized ������ � ������� �������,
 * ������������� ����, ��� "�������" ������ � ��������, � �� � �������;
 * ������� ������� ���� ����� ���������������, ��� ���������� ��������� �� ������ ����� (syncronized(object))
 * � ��������� � ������ ����� ������� � "��������"  
 *
 */

public class Main {
	public static void main(String[] args){
		Core core=new Core();
		new Watcher(core, true);
		new Watcher(core, false);
		while (core.getValue()<1000){
			try{
				TimeUnit.SECONDS.sleep(5);
			}catch(Exception ex){};
		}
	}
}
