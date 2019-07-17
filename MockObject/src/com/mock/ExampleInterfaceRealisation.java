package com.mock;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.easymock.EasyMock;

import com.example.IStringIntegerSetter;
import com.utility.BeanManager;

/** 
 * ������ ���������� ������ ��� ���������� <br />
 * �� ���� ��� ������ ���������� ���������� ���������� easymock <br />
 * createMock - ��� ������ ���������� ����� ����������� <br />
 * createStrictMock - ����� ����������� ������ ��������� ������ ���������� ( ��� ���� ���� ������� ������� EasyMock.expect ) <br />
 */
public class ExampleInterfaceRealisation {

	public static void main(String[] args){
		System.out.println("begin");System.out.println();
		
		System.out.println("------------");

		exampleReturnValue();
		System.out.println("------------");
		
		// ------------------------------------------------------
		exampleStub();
		System.out.println("------------");

		// ------------------------------------------------------
		try{
			exampleException();
		}catch(Exception ex){
			System.out.println("Throw Exception: "+ex.getMessage());
		}
		System.out.println("------------");
		
		
		System.out.println();
		System.out.println("-end-");
	}
	
	/**
	 * ������ Exception
	 */
	private static void exampleException(){
		IStringIntegerSetter mock=EasyMock.createMock(IStringIntegerSetter.class);
		EasyMock.expect(mock.getString()).andThrow(new RuntimeException("this is added RuntimeException"));
		EasyMock.replay(mock);
		System.out.println("This method throws the RuntimeException: "+ mock.getString());
	}
	
	
	/** 
	 * ������ ��������������� �������
	 */
	private static void exampleStub(){
		// ������� ������
		IStringIntegerSetter stubMock=EasyMock.createMock(IStringIntegerSetter.class);
		// ��� ���������� ������ ������
//		org.easymock.EasyMock.expect(stubMock.getParameter("param1"))
//			// ���������� ������� ��� ���������������
//			.andStubDelegateTo(new SimpleStub());
		
		// ��� ���������� ������ ��������� ���������� ������ ��� ���������� � ���� Regular expression
		EasyMock.expect(stubMock.getParameter(EasyMock.matches("[A-Z]*[a-z]*[0-9]*")))
		.andStubDelegateTo(new SimpleStub());
		// �������������� ������
		EasyMock.replay(stubMock);
		EasyMock.verify(stubMock);
		System.out.println(stubMock.getParameter("param1"));
		System.out.println(stubMock.getParameter("P1"));
		System.out.println(stubMock.getParameter("Parameter2"));
	}
	
	
	
	/**
	 * ������ ���������� ������������� �������� ��� ������ 
	 */
	private static void exampleReturnValue(){
		// �������� ������-������
		IStringIntegerSetter mock=org.easymock.EasyMock.createMock(IStringIntegerSetter.class);
		
		System.out.println("Object:"+mock);
		BeanManager.printParentObject(mock);
		BeanManager.printObjectInterfaces(mock);
		
		// ��� ���������� ������� ������ ���������� ��� ������������� ������
		org.easymock.EasyMock.expect(mock.getString())
			// ������������ �������� 
			.andReturn("this is temp value for ")
			.times(1);
			
		org.easymock.EasyMock.expect(mock.getString())
		// ������������ �������� 
		.andReturn("this is another value")
		.times(2);
		
		org.easymock.EasyMock.expect(mock.getString())
		// ������������ �������� 
		.andReturn("this is LAST value")
		.anyTimes();
		
		// or
//		EasyMock.expect(mock.getString())
//			.andReturn("this is temp value").times(1)
//			.andReturn("this is another value").times(2)
//			.andReturn("this is LAST value").anyTimes();
		
		// activate the mock
		org.easymock.EasyMock.replay(mock);
		
		// get value from mock object
		System.out.println("Return: "+mock.getString());
		System.out.println("Return: "+mock.getString());
		System.out.println("Return: "+mock.getString());
		System.out.println("Return: "+mock.getString());
	}
	
}

class SimpleStub implements IStringIntegerSetter{

	private int intValue;
	private String stringValue;
	
	@Override
	public int getInteger() {
		return intValue;
	}

	@Override
	public String getParameter(String parameter) {
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date())+" "+parameter;
	}

	@Override
	public String getString() {
		return stringValue;
	}

	@Override
	public void setInteger(int value) {
		this.intValue=value;
	}

	@Override
	public void setString(String value) {
		this.stringValue=value;
	}
	
}

