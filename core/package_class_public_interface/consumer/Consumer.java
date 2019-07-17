package private_class_public_interface.consumer;

import java.lang.reflect.Modifier;

import private_class_public_interface.producer.Producer;
/**
 * �����:
 * ��� ������, ������� private, ���� ��������� ��������� ( �������
 * ��� �� ����������� public ) ����� ���� �������� ��� ���������
 * ������ ������, � ��� ������� - ���� ��������� public.
 * ( � ��� ������� private ����� �� ��������� getClass().newInstance() 
 * �� ��������� - Exception )
 * 
 * ���������� ����������� ��������� �������, ��� �����
 * default ( package ) ���������
 * @author Technik
 *
 */
public class Consumer {
	public static void main(String[] args) throws Exception, IllegalAccessException{
		IGetString holder=new Producer().getHolder();
		decodeModifiers(holder.getClass());
		System.out.println(holder.getString());
	}
	
	private static void decodeModifiers(Class clazz){
		if(Modifier.isPrivate(clazz.getModifiers())){
			System.out.println("private");
		}
		if(Modifier.isProtected(clazz.getModifiers())){
			System.out.println("protected");
		}
		if(Modifier.isPublic(clazz.getModifiers())){
			System.out.println("public");
		}
		if(Modifier.isAbstract(clazz.getModifiers())){
			System.out.println("abstract");
		}
		if(Modifier.isStatic(clazz.getModifiers())){
			System.out.println("static");
		}
		if(Modifier.isFinal(clazz.getModifiers())){
			System.out.println("final");
		}
		System.out.println("Modifiers:"+clazz.getModifiers());
	}
	
}
