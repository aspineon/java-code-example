package gui.swing_table;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
/** �����, ������� ���������� ������ �� ������ */
public class DatabaseTableWrapModel extends AbstractTableModel{
	private final static long serialVersionUID=1L;
	/** ������, ������� �������� �� ��������� ������ � ResultSet �� ���������� ������ */
	private KeyQueryExecutor keys;
	/** ���-�� ��������  */
	private String[] columnCaption;
	
	/** ������, ������� �������� ������ ��� �������
	 * @param statementData - ������ ��� ��������� ������ 
	 * @param keys - ������, ���������� ����� � ������� ��� ������ ������ 
	 * @param columnCount - ���-�� ��������
	 * */
	public DatabaseTableWrapModel(KeyQueryExecutor keys,
							      String[] columnCaption){
		this.keys=keys;
		this.columnCaption=columnCaption;
	}
	
	/** �������� ����� � ���� ������ */
	public void setKeys(KeyQueryExecutor newKeys){
		this.keys=newKeys;
		this.fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return this.columnCaption.length;
	}

	@Override
	public int getRowCount() {
		if(this.keys==null){
			return 0;
		}else{
			return keys.length;
		}
	}

	private ResultSet currentData;
	private int currentRow=(-1);
	@Override
	public Object getValueAt(int row, int column) {
		if(row!=currentRow){
			try{
				currentData=this.keys.getResultSetBody(row);
				if(currentData.next()){
					currentRow=row;
				}
			}catch(Exception ex){
				//System.err.println("DatabaseTableWrapModel Exception:"+ex.getMessage());
			}
		}
		try{
			return currentData.getObject(column+1);
		}catch(Exception ex){
			//System.err.println("DatabaseTableWrapModel: getValueAt: "+ex.getMessage());
			currentRow=(-1);
			return null;
		}
	}
	
	public ArrayList<Object> getKeysByIndex(int index){
		return this.keys.getKeys(index);
	}
}
