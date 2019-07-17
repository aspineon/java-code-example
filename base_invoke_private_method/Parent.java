package base_invoke_private_method;

import java.lang.reflect.Method;

public abstract class Parent {
	
	/** ������� ����� � ������� ������� (��� ����������, ��� ������������� �������� ) �� ����� ������� ������  */
	public void callMethod(String name){
		try{
			// Method method=Parent.class.getMethod(name);
			Method method=this.getClass().getMethod(name);
			method.invoke(this);
		}catch(Exception ex){
			System.err.println("Parent Exception: "+ex.getMessage());
		}
	}
	
	 
}
