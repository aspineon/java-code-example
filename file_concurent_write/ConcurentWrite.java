package file_concurent_write;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/** ������� ������������� ������ �� ������ ������� � ���� ����
 * ��� ������ ����� ������������  
 * */
public class ConcurentWrite {
	public static void main(String[] args){
		new TempWriter("+: ","c:\\temp.txt",30);
		new TempWriter("-: ","c:\\temp.txt",30);
		new TempWriter("*: ","c:\\temp.txt",30);
		try{
			Thread.sleep(3000);
		}catch(Exception ex){};
		System.out.println("-end-");
	}
}

/** ������������ � ���� ����� */
class TempWriter implements Runnable{
	private FileOutputStream fos;
	private String prefix=null;
	private int sleepTime;
	/** ������������ � ���� ����� 
	 * @param prefix - ������� ��� ������� Writer-� 
	 * @param pathToFile - ������ ���� � �����
	 * @param sleepTime - ����� ��������� ����� ��������� ��������� 
	 * */
	public TempWriter(String prefix, String pathToFile, int sleepTime){
		this.prefix=prefix;
		this.sleepTime=sleepTime;
		try{
			File file=new File(pathToFile);
			fos=new FileOutputStream(file);
		}catch(Exception ex){
			System.err.println("TempWriter#constructor Exception:"+ex.getMessage());
		}
		Thread thread=new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	/** ���������� �����  */
	public void stopThread(){
		this.flagRun=false;
	}

	private volatile boolean flagRun=true;
	@Override
	public void run() {
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		while(flagRun){
			// write logger
			try{
				this.fos.write((prefix+sdf.format(new Date())+"\n").getBytes());
				this.fos.flush();
			}catch(Exception ex){
				System.err.println("Write Error: "+ex.getMessage());
			}
			// sleep 
			try{
				Thread.sleep(this.sleepTime);
			}catch(Exception ex){
			}
		}
		try{
			this.fos.close();
		}catch(Exception ex){};
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println(this.prefix+" Finalize ");
		super.finalize();
	}
}
