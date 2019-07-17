package database.serializer.unpack.interceptors;

import java.sql.Connection;

import database.serializer.common.RecordWrap;

/** ����������� ��� ������������ ���� ������ */
public abstract class Interceptor {
	private String interceptorName;
	
	/** ���������������� ����������� ��� ������� � ��������� ������ */
	public Interceptor(String interceptorName){
		this.interceptorName=interceptorName;
	}
	
	/** �������� ��� ������������ (������� � ���� ������ )
	 * @return ������� ��� ���������������� ������� ( ��� ������� ) 
	 * */
	public String getInterceptorName(){
		return this.interceptorName;
	}
	
	/** ��������� ������ �� ����������� ������������� - �������� �� ������ ����������� ������������������ ��� ������ ������ */
	public boolean isValidRecord(RecordWrap recordWrap){
		return recordWrap.getTableName().equalsIgnoreCase(this.interceptorName);
	}
	
	/**
	 *  ���������� ���������� ������ ������ �������������, ���������� �������������� ������� {@link Interceptor#isValidRecord(RecordWrap)}
	 * <b>!!! ������ �� �������������� Connection.commit() �� �������� ������ ������ </b>
	 * @param recordWrap - ������, ������� ����� ��������� 
	 * @return 
	 * <li> true - ������ ������� ��������� </li>
	 * <li> false - ������ ���������� ������ </li>
	 * @throws Exception - ������ �� ����� ���������� ������
	 */
	public abstract boolean processRecord(RecordWrap recordWrap, Connection connection) throws Exception;
}
