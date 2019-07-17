package database.serializer.unpack.interceptors.columns;

import java.sql.Connection;

import database.serializer.common.RecordWrap;

/** ������, ������� �������� ������ ��� ��������� �� ��� ������� � ���� ������ ������ ������� RecordWrap */
public abstract class ColumnInterceptor {
	private String columnName;
	
	/** ������, ������� �������� ������ ��� ��������� �� ��� ������� � ���� ������ ������ ������� RecordWrap 
	 * @param columnName - ��� �������, � ������ �� ����, ��� ������� ������ ������ ����������
	 * */
	public ColumnInterceptor(String columnName){
		this.columnName=columnName;
	}
	
	public String getColumnName(){
		return columnName;
	}
	
	/** �������� ������ �������� � �������
	 * @param fieldsName - ����, ������� ���� � �������
	 * @param value - ��������, ������� ����� ������ � �������
	 * @return - ������ ��������, ��� (-1) ���� ������� �� ������
	 */
	protected int getIndexInArray(String[] fieldsName, String value){
		int returnValue=(-1);
		for(int counter=0;counter<fieldsName.length;counter++){
			if(fieldsName[counter].equalsIgnoreCase(value)){
				returnValue=counter;
				break;
			}
		}
		return returnValue;
	}
	
	/** ������ ���� ������ ���� ��������� � ���� ������
	 * @return 
	 * <li> <true> - ��, ���� ������ ���� ��������� </li>
	 * <li> <false> - ���, ������ �� ��� ���� ������  </li>
	 * 
	 * */
	public abstract boolean isForDatabaseSave();
	
	/** �������� ������ �� ��������� ���������� ������ */
	public abstract Object processValue(Connection connection, RecordWrap recordWrap, Object[] currentRow);
	
}
