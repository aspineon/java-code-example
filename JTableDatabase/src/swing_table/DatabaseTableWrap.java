package swing_table;
import javax.swing.*;

import java.sql.*;
import java.util.ArrayList;
/** ����������� ������� �� ���� ������ �� 
 * ���������� ��������� Swing*/
public class DatabaseTableWrap {
	/** �������, ��� ������� ����� ������������� �������� �������� */
	private JTable table;
	/** ����� ����� ��� ����������� �� ��������� */
	private String[] dataCaption;
	/** ������ ������ ��� ������� */
	private DatabaseTableWrapModel model;
	/** ������ �� PrefferedWidth for table */
	private int[] columnWidth;
	/** ������, ������� �������� ������ � �������� ����� �� ������� */
	private KeyQueryExecutor keyExecutor;
	
	/** ������, ������� ���������� ������� �� ���� ������ �� Swing ��������� JTable
	 * @param statementData - ������ � ���� ��� ��������� ������, �� ��������� ����� <i> setInteger(0) must be allowed </i>
	 * @param columnCaption - ���������, ������� ����� ���������� � ��������
	 * @param columnWidth - �������� �������� ��� �������
	 * @param keys - ����� ������ ��� ��������������� ����������� ������
	 * */
	public DatabaseTableWrap(PreparedStatement statementData,
						     String[] columnCaption,
						     int[] columnWidth,
						     ResultSet keys){
		this.dataCaption=columnCaption;
		this.columnWidth=columnWidth;
		this.keyExecutor=new KeyQueryExecutor(statementData,keys);
		createTable();
	}

	
	private void createTable(){
		// ������� ���������� 
		table=new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try{
			this.model=new DatabaseTableWrapModel(this.keyExecutor,
												  dataCaption);
			this.table.setModel(this.model);
			for(int counter=0;counter<dataCaption.length;counter++){
				String columnName=this.table.getColumnName(counter);
				this.table.getColumn(columnName).setPreferredWidth(this.columnWidth[counter]);
				this.table.getColumn(columnName).setHeaderValue(this.dataCaption[counter]);
			}
		}catch(Exception ex){
			System.err.println("DataBaseTableWrap Exception: create Table Error:"+ex.getMessage());
		}
		
	}
	
	/** �������� ������ � ������� - �������� �����-���� �� ��������� ������� ������������ ������ */
	public void updateData(ResultSet rs) throws Exception{
		this.keyExecutor.setNewKeys(rs);
		this.model.fireTableDataChanged();
		this.table.revalidate();
	}
	
	/** �������� ������ �� �������, ������� �������� ��� ����������� ������ */
	public JTable getTable(){
		return this.table;
	}
	
	/** �������� ������� �� ����������� ��������� 
	 * */
	public ArrayList<ArrayList<Object>> getSelectedValues(){
		ArrayList<ArrayList<Object>> returnValue=new ArrayList<ArrayList<Object>>();
		int[] selectedRows=this.table.getSelectedRows();
		for(int counter=0;counter<selectedRows.length;counter++){
			returnValue.add(
							this.model.getKeysByIndex(
													  this.table.convertRowIndexToModel(selectedRows[counter])
													  )
							);
		}
		return returnValue;
	}
	
	/** �������� ������� �� ����������� ��������� �� ��������� ����� ������� �� ������� */
	public ArrayList<Object> getSelectedColumnValues(int columnIndex){
		ArrayList<Object> returnValue=new ArrayList<Object>();
		int[] selectedRows=this.table.getSelectedRows();
		for(int counter=0;counter<selectedRows.length;counter++){
			returnValue.add(
							this.model.getKeysByIndex(
													  this.table.convertRowIndexToModel(selectedRows[counter])
													  ).get(columnIndex)
							);
		}
		return returnValue;
		
	}
}
