package database.serializer.unpack.interceptors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.serializer.common.RecordWrap;
import database.serializer.unpack.interceptors.columns.ColumnInterceptor;

/** �����, ������� �������� ������ �������, ������� ������������ � ��������� ������ �� ������� RecordWrap ��� �� ������ ����������� */
public class TablePartColumns {
	private String tableDestination;
	private String[] columns;
	/** ������, ������� ������� ��� ����� ���������� � ��������������� ������ ��������� � ������ ������� ����� */
	private int[] indexes;
	/** ������������ ��� ������, ������� ���������� � ������ ���� ������  */
	private ColumnInterceptor[] columnInterceptors; 
	
	/** �����, ������� �������� ������ �������, ������� ������������ � ��������� ������ �� ������� RecordWrap ��� �� ������ ����������� 
	 * @param tableDestination - �������, � ������� ����� �������� �������� 
	 * @param columns - �������, ������� ������ ���� ��������� �� ���������� �������
	 * @param columnInterceptors - ������� ������������ �� ���������� ������� 
	 */
	public TablePartColumns(String tableDestination, 
							String[] columns, 
							ColumnInterceptor[] columnInterceptors){
		this.tableDestination=tableDestination;
		this.columns=columns;
		this.columnInterceptors=columnInterceptors;
	}
	
	/** ������� ���������� � ����� ������ */
	private Connection connection;
	private RecordWrap recordWrap;
	private PreparedStatement preparedStatement;
	
	
	/** �������� ������ ������ � ������� �����
	 * @param array ������ �� �����
	 * @param value ������ ��� ������
	 * @return - ������� -1, ���� ������ �� ������ 
	 */
	private int getStringIndex(String[] array, String value){
		int returnValue=(-1);
		try{
			for(int counter=0;counter<array.length;counter++){
				if(array[counter].equalsIgnoreCase(value)){
					returnValue=counter;
					break;
				}
			}
		}catch(Exception ex){};
		return returnValue;
	}
	
	
	
	/** ����������� ������ �� ��������� ����� ������
	 * @param recordWrap
	 * @param connection
	 * @return false - ���� �� ������� �������� ������ ��� ���������� ������ - ������ ��������� 
	 */
	public boolean recalculateRecord(RecordWrap recordWrap, Connection connection) {
		try{
			// �������� ��� �������, ������� �� �������� ��� �������������� ���� ������ � recordWrap, � ��������� ������
			/** ������� ��������� � ������ ������� ����� � ������ �� � recordWrap*/
			this.indexes=new int[this.columns.length];
			for(int counter=0;counter<this.indexes.length;counter++){
				this.indexes[counter]=this.getStringIndex(recordWrap.getFieldNames(), this.columns[counter]);
			}
			// ��������� ������� ���� ����� ������������� � ��������, ���� �� ��� - ��������
			if(this.columnInterceptors!=null){
				for(int counter=0;counter<this.columnInterceptors.length;counter++){
					if(this.getStringIndex(this.columns, this.columnInterceptors[counter].getColumnName())<0){
						// ������ ������� ��� - ��������
						this.columns=this.addStringToArrayString(this.columns, this.columnInterceptors[counter].getColumnName());
						this.indexes=this.addIntToArrayInt(this.indexes,(-2));
					}
				}
				
			}
			// ������� PreparedStatement
			String delimeter=", ";
			StringBuffer sqlNames=new StringBuffer();
			StringBuffer sqlValues=new StringBuffer();
			// ����� ��� ������� ������� �����
			// ��������� ����� ��� ������������ ���� ����������� � (-2)
			if(this.columnInterceptors!=null){
				// ���� ������������, ���������� �������� � -2
				for(int counter=0;counter<this.columnInterceptors.length;counter++){
					int indexInterceptor=this.getStringIndex(this.columns, this.columnInterceptors[counter].getColumnName());
					if(indexInterceptor>=0){
						// ��� ������������ ������� � ����������������� �������� 
						this.indexes[indexInterceptor]=(-2);
					}
				}
			}
			
			for(int counter=0;counter<columns.length;counter++){
				if(indexes[counter]!=(-1)){
					if(indexes[counter]==(-2)){
						// ������ ���� ������ ���� ���������� �������������
						ColumnInterceptor interceptor=this.getInterceptorByName(this.columns[counter]);
						if(interceptor.isForDatabaseSave()){
							if(sqlNames.length()>0){
								sqlNames.append(delimeter);
								sqlValues.append(delimeter);
							};
							sqlNames.append(this.columns[counter]);
							sqlValues.append("?");
						}else{
							// ������ �� ����� ��������� � ���� ������ 
						}
					}else{
						// ���� ��� ������������ - ������ ��������� ������ � ����
						if(sqlNames.length()>0){
							sqlNames.append(delimeter);
							sqlValues.append(delimeter);
						};
						sqlNames.append(this.columns[counter]);
						sqlValues.append("?");
					}
				}else{
					// ��� ����������� � ���������� ������ ��� �������� �������� 
				}
			}
			if(sqlNames.length()==0){
				throw new Exception("");
			}
			// ��������� connection
			this.connection=connection;
			this.recordWrap=recordWrap;
			// ������� PreparedStatement
			StringBuffer query=new StringBuffer();
			query.append("insert into "+this.tableDestination+"(");
			query.append(sqlNames);
			query.append(") values(");
			query.append(sqlValues);
			query.append(")");
			this.preparedStatement=this.connection.prepareStatement(query.toString());
			return true;
		}catch(Exception ex){
			System.err.println("TablePartColumns#recalculateRecord Exception: "+ex.getMessage());
			return false;
		}
	}
	
	/** 
	 * �������� � ������� ��� ���� ������� � ���� 
	 * @param array
	 * @param value
	 * @return
	 */
	private int[] addIntToArrayInt(int[] array, int value) {
		if(array==null){
			return new int[]{value};
		}else{
			int[] returnValue=new int[array.length+1];
			for(int counter=0;counter<array.length;counter++){
				returnValue[counter]=array[counter];
			}
			returnValue[array.length]=value;
			return returnValue;
		}
	}

	/** 
	 * �������� � ������� ��� ���� ������� � ���� 
	 * @param arrayOfString
	 * @param value
	 * @return
	 */
	private String[] addStringToArrayString(String[] arrayOfString, String value) {
		if(arrayOfString==null){
			return new String[]{value};
		}else{
			String[] returnValue=new String[arrayOfString.length+1];
			for(int counter=0;counter<arrayOfString.length;counter++){
				returnValue[counter]=arrayOfString[counter];
			}
			returnValue[arrayOfString.length]=value;
			return returnValue;
		}
	}

	/** �������� ����������� �� ����� ������� */
	private ColumnInterceptor getInterceptorByName(String columnName){
		ColumnInterceptor returnValue=null;
		for(int counter=0;counter<this.columnInterceptors.length;counter++){
			if(this.columnInterceptors[counter].getColumnName().equalsIgnoreCase(columnName)){
				returnValue=this.columnInterceptors[counter];
				break;
			}
		}
		return returnValue;
	}
	
	/** ���������� � �������������� ������ ����� ������ 
	 * @param currentObjects - �������, ������� ���������� ���������� 
	 * @throws ���� �� ���������� ���������� ������ � ������ 
	 * */ 
	public void setDataToStatement(Object[] currentObjects) throws SQLException {
		this.preparedStatement.clearParameters();
		int queryPosition=0;
		for(int counter=0;counter<this.indexes.length;counter++){
			/** ���� �� ������ ���������� ���� � ������� */
			if(this.indexes[counter]>=0){
				// ����� �������� ������
				queryPosition++;
				this.preparedStatement.setObject(queryPosition, currentObjects[this.indexes[counter]]);
			}else{
				// ��������, ����� �������� ������ �� ������������ 
				if(this.indexes[counter]==(-2)){
					ColumnInterceptor interceptor=this.getInterceptorByName(this.columns[counter]);
					if(interceptor.isForDatabaseSave()){
						// ��� ���������� � ���� ������
						queryPosition++;
						Object value=interceptor.processValue(this.connection, this.recordWrap, currentObjects);
						this.preparedStatement.setObject(queryPosition, value);
					}else{
						// �� ��� ���� ������ - ������ ���������� ������
						interceptor.processValue(this.connection, this.recordWrap, currentObjects);
					}
				}
			}
		}
	}

	/** ��������� ������ � ��������� ����������, ���� ���-�� ����� �� ��� */
	public void executeUpdate() throws SQLException{
		preparedStatement.executeUpdate();
	}

	/** ���������� ��� �������, ��������� � ���������� �� ���� ������ */
	public void freeConnection() {
		try{
			this.preparedStatement.close();
		}catch(Exception ex){};
		this.connection=null;
		this.recordWrap=null;
	}

}
