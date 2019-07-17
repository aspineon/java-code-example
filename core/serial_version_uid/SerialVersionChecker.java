package serial_version_uid;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;


/**
 * ������������ ����������� ���������� (������������) ����� � ����� ��������� serialVersionUID � ��������������� � ������ ��������� ( � �������� ������� )
 * ��������� InvalidClassException ���������� ���������:
 * popObject Exception:(java.io.InvalidClassException)serial_version_uid.Temp; local class incompatible: stream classdesc serialVersionUID = 23, local class serialVersionUID = 24
 */
public class SerialVersionChecker {
	public static void main(String[] args){
		System.out.println("begin");
		String filePath="c:\\temp.obj";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		// pushObject(new Temp(sdf.format(new java.util.Date())), filePath);
		System.out.println(popObject(filePath));
		
		System.out.println("=end=");
	}
	
	/** ��������� ������� � ���� 
	 * @param object - ������
	 * @param path - ������ ���� � �����
	 */
	private static void pushObject(Object object, String path){
		try{
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(object);
			oos.flush();
			oos.close();
		}catch(Exception ex){
			System.err.println("pushObject Exception:"+ex.getMessage());
		}
	}

	/** ��������� ������� � ����
	 * @param path - ������ ���� � ������� 
	 * @return - ���������� ������ 
	 */
	private static Object popObject(String path){
		try{
			Object returnValue=null;
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(path));
			returnValue=ois.readObject();
			ois.close();
			return(returnValue);
		}catch(Exception ex){
			System.err.println("popObject Exception:("+ex.getClass().getName()+")"+ex.getMessage());
			return null;
		}
	}
	
}

class Temp implements Serializable{
	private final static long serialVersionUID=24L;
	
	private String value;
	
	public Temp(String value){
		this.value=value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}