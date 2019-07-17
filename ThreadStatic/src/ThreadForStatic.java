
/** ������������ ��������� ������������ ������ � ������������� ����� */
public class ThreadForStatic {
	public static void main(String[] args){
		System.out.println("begin");
		(new CommonThread()).start();
		try{Thread.sleep(5000);}catch(Exception ex){};
		(new CommonThread()).start();
		System.out.println("end");
	}
}

class CommonThread extends Thread{
	public void run(){
		for(int counter=0;counter<5;counter++){
			//Common.staticBeat();
			//Common.beat();
			// � ������ ������ ��������� ���������� ������������ ���������� � ���� ���������� �������� 
			Common.beat(Integer.toString(counter));
			try{
				Thread.sleep(1000);
			}catch(Exception ex){};
		}
	}
}

/** ����� ����� ��� ��������� ������� � ������������ ������ */
class Common{
	private static int counter=0;
	public static void staticBeat(){
		// ����������� ���������� ����� ���� ��� ���� ������� 
		for(counter=0;counter<10;counter++){
			try{
				System.out.println(counter+" : "+Thread.currentThread().getName());Thread.sleep(500);
			}catch(Exception ex){};
		}
	}

	public static void beat(){
		// ����������� ����������� ���������� ����� ��������� ��� ������� ������ 
		for(int counter=0;counter<10;counter++){
			try{
				System.out.println(counter+" : "+Thread.currentThread().getName());Thread.sleep(500);
			}catch(Exception ex){};
		}
	}

	public static void beat(String information){
		// ����������� ����������� ���������� ����� ��������� ��� ������� ������ 
		for(int counter=0;counter<10;counter++){
			try{
				System.out.println(counter+" : "+information+"  "+Thread.currentThread().getName());Thread.sleep(500);
			}catch(Exception ex){};
		}
	}
	
}