import java.util.concurrent.TimeUnit;


/** ������-����, ������� �������� ���������� ��� ������� ������� � ����� ��� ���������� ������� */
public class Core {
	int counter=0;
	
	public synchronized int decValue(){
		try{
			TimeUnit.MILLISECONDS.sleep(500);
		}catch(Exception ex){};
		--counter;
		System.out.println("decValue: "+counter);
		return counter;
	}
	
	public synchronized int incValue(){
		try{
			TimeUnit.MILLISECONDS.sleep(50);
		}catch(Exception ex){};
		++counter;
		System.out.println("incValue: "+counter);
		return counter;
	}

	public int getValue(){
		return counter;
	}
}
