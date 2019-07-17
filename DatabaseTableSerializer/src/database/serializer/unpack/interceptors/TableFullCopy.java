package database.serializer.unpack.interceptors;

import java.sql.Connection;
import java.sql.PreparedStatement;

import database.serializer.common.RecordWrap;

/** ������� ��� �������, ������� ��������� �������� � ��������������� Connection �� ����� ������� ��� ������������ ������ */
public class TableFullCopy extends Interceptor {
	private String tableName;
	
	/** ������� ��� �������, ������� ��������� �������� � ��������������� Connection �� ����� ������� ��� ������������ ������ 
	 * @param interceptorName - ��� ��������������� ������� �� ���������� ������� 
	 * @param tableName - ��� �������, � ������� ����� ����������� ������ ������ 
	 */
	public TableFullCopy(String interceptorName, String tableName ) {
		super(interceptorName);
		this.tableName=tableName;
	}

	/** �������� ��� ������� � ������� ������ ���� ��������� ������ 
	 * @return ��� �������, � ������� ����� ��������� ������ 
	 * */
	public String getTableName(){
		return this.tableName;
	}
	
	@Override
	public boolean processRecord(RecordWrap recordWrap, Connection connection) throws Exception {
		// creating SQL INSERT query
		// "insert into EVENT(date_sensor, date_camera) values(?,?)"
		String delimeter=", ";
		StringBuffer sqlNames=new StringBuffer();
		StringBuffer sqlValues=new StringBuffer();
		for(int counter=0;counter<recordWrap.getFieldNames().length;counter++){
			if(sqlNames.length()>0){
				sqlNames.append(delimeter);
				sqlValues.append(delimeter);
			};
			sqlNames.append(recordWrap.getFieldNames()[counter]);
			sqlValues.append("?");
		}
		StringBuffer query=new StringBuffer();
		query.append("insert into "+this.getTableName()+"(");
		query.append(sqlNames);
		query.append(") values(");
		query.append(sqlValues);
		query.append(")");
		PreparedStatement preparedStatement=connection.prepareStatement(query.toString());
		boolean returnValue=false;
		// ���������� ��� ������, ������� ���� � �������
		for(int rowCounter=0;rowCounter<recordWrap.getRowCount();rowCounter++){
			// �������� ��������������� ������
			preparedStatement.clearParameters();
			// ��������� PreparedStatement ������� 
			Object[] currentObjects=recordWrap.getObjects(rowCounter);
			for(int counter=0;counter<recordWrap.getFieldNames().length;counter++){
				preparedStatement.setObject(counter+1,currentObjects[counter]);
			}
			// ���������� ������ 
			try{
				preparedStatement.executeUpdate();
			}catch(Exception ex){
				System.err.println("TableEvent#processRecord Exception:"+ex.getMessage());
				throw ex;
			}
		}
		return returnValue;
	}

}
