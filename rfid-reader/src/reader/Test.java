package reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test extends Thread implements IOutputBlockListener{
	public static void main(String[] args){
		System.out.println("begin");
		(new Test()).start();
		System.out.println("-end");
	}
	
	public Test(){
		reader=new ReaderAsync("+".getBytes(), "=".getBytes(),7);
		reader.addOutputBlockListener(this);
	}

	private ReaderAsync reader; 
	/** ������ ������ �� ������ �� ������� */
	public void run(){
		this.reader.startReader();
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		while(true){
			try{
				String line=reader.readLine();
				if(line.equalsIgnoreCase("q")){
					break;
				}
				this.reader.inputData(line.getBytes());
			}catch(Exception ex){
				ex.printStackTrace();
				System.err.println("Exception: "+ex.getMessage());
			}
		}
		this.reader.stopReader();
	}

	@Override
	public void notifyBlock(byte[] array) {
		System.out.println("New Block: "+new String(array));
	}
}
