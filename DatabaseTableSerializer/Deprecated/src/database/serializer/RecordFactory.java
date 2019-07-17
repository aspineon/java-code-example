package database.serializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;

/** ������, ������� ������ ��������� ������, �� ����������� �������������� */
public class RecordFactory {
	/** ��� ������� */
	private String tableName;
	/** ����� �����, ������� ������� �������� � ���� ������ */
	private IRecordColumn[] columns;
	/** ����� ����� */
	private String[] fieldNames;
	/** ������ � ���� ������ ��� ���������� ��������, ������� ����� ��������� � ������ �������  */
	private String query;
	
	
	/** ������ ������� ������ ��������� ������, �� �������������� ���������� ���� 
	 * @param connection - ���������� � ����� ������, Connection �� ����������� � �������
	 * @param tableName - ��� ������� �� ������� ������� �������� ������
	 * */
	public RecordFactory(String tableName, String primaryField, IRecordColumn ... columns){
		this.tableName=tableName;
		this.query="select * from "+tableName+" where "+primaryField+" in ";
		this.columns=columns;
		this.fieldNames=new String[this.columns.length];
		for(int counter=0;counter<this.columns.length;counter++){
			this.fieldNames[counter]=this.columns[counter].getColumnName();
		}
	}
	
	
	/* �������� �� ���� ������ ����� ���� �����, ������� ���� � ��������� ������� 
	protected String[] getFieldNames(Connection connection, String tableName){
		String[] returnValue=null;
		try{
			ResultSet rs=connection.createStatement().executeQuery("Select * from "+tableName);
			ResultSetMetaData rsmd=rs.getMetaData();
			returnValue=new String[rsmd.getColumnCount()];
			for(int counter=0;counter<returnValue.length;counter++){
				returnValue[counter]=rsmd.getColumnName(counter+1);
			};
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("RecordFactory#getFieldNames Exception:"+ex.getMessage());
		}
		return returnValue;
	}*/
	
	/** �������� �� ������� int ������, � ������� ������ �������� ��������� �������� 
	 * @param values - ������ int ��������
	 * @return ������ ��� ������� � SQL ������ 
	 * */
	private String getValuesDelimeterComma(int[] values){
		if(values!=null){
			StringBuffer returnValue=new StringBuffer();
			for(int counter=0;counter<values.length;counter++){
				returnValue.append(values[counter]);
				if(counter!=(values.length-1)){
					returnValue.append(", ");
				}
			}
			return returnValue.toString();
		}else{
			return "";
		}
	}
	
	
	/** �������� ������-������� ��� ��������� ������� �� ���� ������  
	 * @param uniqueId - ���������� ������������� ������, ������� ����� �������� �� �������
	 * @param connection - ���������� � ����� ������
	 * @return null ���� �� ������� �������� ������ ������ 
	 * */
	public RecordWrap getRecords(Connection connection, int ... idValues){
		RecordWrap returnValue=null;
		try{
			ResultSet rs=connection.createStatement().executeQuery(query+" ( "+this.getValuesDelimeterComma(idValues) +" )");
			returnValue=new RecordWrap(this.tableName,this.fieldNames);
			while(rs.next()){
				Object[] currentValue=new Object[this.fieldNames.length];
				for(int counter=0;counter<currentValue.length;counter++){
					currentValue[counter]=this.columns[counter].getObject(rs);
				}
				returnValue.appendRow(currentValue);
			}
			rs.getStatement().close();
		}catch(Exception ex){
			returnValue=null;
			System.err.println("RecordFactory#getRecord: "+ex.getMessage());
		}
		return returnValue;
	}
	
	/** �������� ������ � ��������������� �����
	 * @param output - ����� ��� ������ ������ 
	 * @param recordWrap - ������, ������� ���������� ���������
	 * */
	public void writeToStream(OutputStream output, RecordWrap recordWrap) throws IOException{
		ObjectOutputStream out=new ObjectOutputStream(output);
		out.writeObject(recordWrap);
		out.flush();
	}
}
