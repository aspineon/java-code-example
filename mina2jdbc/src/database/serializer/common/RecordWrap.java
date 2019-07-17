package database.serializer.common;

import java.io.Serializable;
import java.util.ArrayList;

/** ������� ����� ������ �� ���� ������ 
 */
public class RecordWrap implements Serializable{
	private final static long serialVersionUID=1L;
	/** ��� ������� ���� ������, ������� ����������� � ������� */
	private String tableName;
	/** ����� ����� ���� */
	private String[] fieldNames;
	/** �������, ������� �������� � ���� � ���� ����� �� �������� �������� */
	private ArrayList<Object[]> objects=new ArrayList<Object[]>();
	/** ���-�� ��������� � ���� ������ */
	private int columnSize;
	
	
	/** ������� ����� ������ �� ���� ������ 
	 * @param tableName - ��� ������� �� ������� ������ ������
	 * @param fieldNames - ����� ����� �� ������� � ���� ������ 
	 * */
	public RecordWrap(String tableName, String[] fieldNames){
		this.tableName=tableName;
		this.fieldNames=fieldNames;
		if(fieldNames!=null){
			this.columnSize=this.fieldNames.length;
		}
	}
	
	/** ������� ����� ������ �� ���� ������ 
	 * @param tableName - ��� ������� �� ������� ������ ������
	 * @param fieldNames - ����� ����� �� ������� � ���� ������ 
	 * */
	public RecordWrap(String tableName, String[] fieldNames, Object ... values){
		this(tableName, fieldNames);
		this.appendRow(values);
	}
	

	/** �������� ��� �������, ������ ������� ��������*/
	public String getTableName() {
		return tableName;
	}

	/** ���������� ��� �������, ������ ������� ��������*/
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/** �������� ����� �����, ������� ��������� � ������ �������*/
	public String[] getFieldNames() {
		return fieldNames;
	}

	/** ���������� ����� ����� ��� */
	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}


	/** �������� ���-�� ��������� */
	public int getColumnSize() {
		return columnSize;
	}

	/** ���������� ���-�� ��������� */
	public void setColumnSize(int size) {
		this.columnSize = size;
	}

	/** 
	 * �������� � �������� ��� ����� ������ �������� ������� ����������� ������ Object[]<br>
	 * <b> �����: �������� �� ����������� �� ������������, �� ���� ������ ������ ����������� � ������� </b>
	 * @param appendObjects - ������ �� ����������� �������� 
	 */
	public void appendRow(Object[] appendObjects){
		this.objects.add(appendObjects);
	}
	
	
	/** �������� ���-�� ����� � ������� */
	public int getRowCount(){
		return this.objects.size();
	}
	
	/** �������� ������ ������ �� ��������� ������� ������ 
	 * @param rowIndex - ������ ������ � ������� 
	 * @return ������ �� �������� 
	 */
	public Object[] getObjects(int rowIndex){
		if(rowIndex<this.objects.size()){
			return this.objects.get(rowIndex);
		}else{
			return null;
		}
	}
	
	/** �������� ��� ������ � ������� */
	public void clearDataOnly(){
		this.objects.clear();
	}
	
	/** �������� �������� ������ */
	public void reset(){
		this.fieldNames=null;
		this.columnSize=0;
		this.objects.clear();
		this.tableName=null;
	}
}
