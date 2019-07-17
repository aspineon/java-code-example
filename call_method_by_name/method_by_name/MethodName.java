package method_by_name;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * �����, ������� ������ ��� ������ ������� �� ���, ������,
 * ����� 
 */
public class MethodName {
	private Class field_class;
	private Object field_source_object;
	private Object[] field_arguments;
	private Method field_method;
	public MethodName(){
		this.field_class=null;
		this.field_source_object=null;
		this.field_arguments=null;
		this.field_method=null;
	}
	/**
	 * ����������� � ������������ ����������� 
	 * @param object_with_method - ������ � �������� ����� ������ �����
	 * @param method_name -��� ������, ������� ����� ������ � ������� {@link}object_with_method
	 * @param arguments ��������� ������, ������� ����� ������
	 */
	public MethodName(Object object_with_method,String method_name,Object[] arguments) throws NoSuchMethodException{
			this.setClass(object_with_method);
			this.setMethod(method_name,arguments);
			this.setArguments(arguments);
	}
	/**
	 * ���������� �����
	 */
	public void setClass(Class source_class){
		this.field_class=source_class;
	}
	/**
	 * ���������� �����, ������ �� �������
	 * @param source_class
	 */
	public void setClass(Object source_class){
		this.field_class=source_class.getClass();
		this.field_source_object=source_class;
	}
	/**
	 * ���������� ����� �������, � ������� �������� ����� ��������� 
	 * @param class_name ��� ������
	 * @throws ClassNotFoundException ���� ����� �� ������ �� �����
	 */
	public void setClass(String class_name) throws ClassNotFoundException{
		this.field_class=Class.forName(class_name);
	}
	/**
	 * ���������� ����� �� ��� �����
	 * @param method_name - ��� ������
	 * @throws NoSuchMethodException - ���� ����� �� ������ � ������� ������������� �������, � � ������� �������������� �����������
	 */
	public void setMethod(String method_name) throws NoSuchMethodException{
		this.field_method=null;
		Method method = this.field_class.getMethod(method_name, new Class[]{});
		this.field_method=method;
	}
	/**
	 * ��������� ������, ������� ����� ������ ��
	 * @param method_name ����� ������
	 * @param argument_class ���������� � ���� �������
	 * @throws NoSuchMethodException ���� ������ ����� �� ������ � ������� ������������ ������� Class
	 */
	public void setMethod(String method_name,
						  Class[] argument_class) 
						  throws NoSuchMethodException{
		this.field_method=null;
		this.field_method=this.field_class.getMethod(method_name, argument_class);
	}
	/**
	 * ��������� ������, ������� ����� ������ 
	 * @param method_name ��� ������
	 * @param argument_object ���������, ������� ��������� � ������
	 * @throws NoSuchMethodException ���� ����� �� ������
	 */
	public void setMethod(String method_name, Object[] argument_object) throws NoSuchMethodException{
		Class[] argument_classes=new Class[argument_object.length];
		for(int counter=0;counter<argument_object.length;counter++){
			argument_classes[counter]=argument_object[counter].getClass();
		}
		this.field_method=null;
		this.field_method=this.field_class.getMethod(method_name,argument_classes);
	}
	/**
	 * ���������� ���������, ������� ����� ������������ � ������ �������
	 * @param arguments
	 */
	public void setArguments(Object[] arguments){
		this.field_arguments=arguments;
	}
	/**
	 * ���������� ������, � �������� ����� ���������� ������ �����
	 */
	public void setObjectWithMethod(Object source){
		this.field_source_object=source;
	}
	/**
	 * @return ���������� �������� �������� ������� �� ��������� ��������� ����������
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public Object getValue() throws InvocationTargetException,IllegalAccessException{
		return this.field_method.invoke(
						this.field_source_object,
						this.field_arguments
					);
	}
}
