package gui.editor.database.gui;
import gui.table_column_render.ICellValue;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** �����, ������� ������������ ������ �� ��������� ������� � ���� ������ */
public class JTableWrap extends JTable{
	private final static long serialVersionUID=1L;
	private JTableWrapModel model;
	/**
	 *  �����, ������� ������������ ������ �� ��������� ������� � ���� ������
	 * @param connection - ���������� � ����� ������ 
	 * @param queryDataUnique - ������, ������� "��������" ����� �� ���� (SELECT id FROM table_1)
	 * @param queryDataId - ������, �� ��������� �������� ����� ��������� PreparedStatement c ����� ���������� (SELECT * FROM table_1 WHERE KOD=?)
	 * @param queryCount - ������, ������� ���������� ���-�� ������� �� ������� queryData SELECT count(*) FROM table_1
	 * @param nameColumns - ����� ���������� ��������
	 * @param width - �������� ������ ��� ��������
	 * @param date - ���� ( ����� � ���)	  
	 * @param renderColumns - ������� ��� ������ ������ � �������
	 * @throws ����� �� ������� �������� ������ �� ���������� ��������
	 */
	public JTableWrap(Connection connection,
					  String queryDataUnique,
					  String queryDataId,
					  String queryCount,
					  String[] nameColumns,
					  int[] width,
					  ICellValue[] renderColumns) throws Exception{
		super();
		this.model=new JTableWrapModel(connection,queryDataUnique, queryDataId, queryCount,renderColumns);
		this.setModel(this.model);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setColumnWidth(width);
		this.setColumnCaption(nameColumns);
	}
	
	private void setColumnWidth(int[] width){
		for(int counter=0;counter<width.length;counter++){
			this.getColumn(this.getColumnName(counter)).setPreferredWidth(width[counter]);
		}
	}
	private void setColumnCaption(String[] caption){
		for(int counter=0;counter<caption.length;counter++){
			this.getColumn(this.getColumnName(counter)).setHeaderValue(caption[counter]);
		}
	}
	
	/** �������� ������������ ������ */
	public void refreshData(Object ...values){
		this.model.refreshData(values);
		this.model.fireTableDataChanged();
	}

	/** �������� ���������� ������ �� ����������� �������� */
	public Object getSelectedObject(){
		return this.model.getSelectionId(this.convertRowIndexToModel(this.getSelectedRow()));
	}
	
	public void clearData(){
		this.model.clearData();
		this.model.fireTableDataChanged();
	}
}

/** ������ � ������� ��� ������� */
class JTableWrapModel extends AbstractTableModel{
	private final static long serialVersionUID=1L;
	private PreparedStatement statementDataUnique;
	private PreparedStatement statementDataId;
	private PreparedStatement statementDataCount;
	private ICellValue[] renderColumns;
	private Object[] values;
	/** ������ � ������� ��� ������� 
	 * @param connection - ���������� � ����� ������ 
	 * @param queryDataUnique - ������ � ������������ ������ ���������� ������   
	 * @param queryDataId - ������ ��� ��������� PreparedStatement 
	 * @param queryDataCount - ������ ���-�� �������
	 * @param date - ����, ( ����� � ��� )
	 * @param renderColumns - ������� ����������� ����������  
	 */
	public JTableWrapModel(Connection connection,
						   String queryDataUnique,
						   String queryDataId,
						   String queryDataCount,
						   ICellValue[] renderColumns) throws SQLException{
		this.statementDataUnique=connection.prepareStatement(queryDataUnique);
		this.statementDataId=connection.prepareStatement(queryDataId);
		this.statementDataCount=connection.prepareStatement(queryDataCount);
		this.renderColumns=renderColumns;
	}
	
	private ArrayList<Object> uniqueId=new ArrayList<Object>();
	
	/** �������� ��� ������ */
	public void clearData(){
		this.uniqueId.clear();
	}
	
	/** �������� ������ */
	public void refreshData(Object ...values){
		try{
			this.values=values;
			this.currentRow=(-1);
			if(this.values!=null){
				for(int counter=0;counter<this.values.length;counter++){
					this.statementDataUnique.setObject(counter+1, this.values[counter]);
				}
			}
			ResultSet rs=this.statementDataUnique.executeQuery();
			uniqueId.clear();
			while(rs.next()){
				uniqueId.add(rs.getObject(1));
			}
		}catch(Exception ex){
			System.err.println("JTableWrap#refreshData Exception:"+ex.getMessage());
		}
	}
	
	@Override
	public int getColumnCount() {
		return renderColumns.length;
	}

	@Override
	public int getRowCount() {
		int returnValue=0;
		try{
			if(this.values!=null){
				for(int counter=0;counter<this.values.length;counter++){
					this.statementDataCount.setObject(counter+1, this.values[counter]);
				}
			}
			ResultSet rs=this.statementDataCount.executeQuery();
			if(rs.next()){
				returnValue=rs.getInt(1);
			}
		}catch(Exception ex){
			System.err.println("JTableWrapModel#getRowCount Exception:"+ex.getMessage());
		}
		return returnValue;
	}

	private int currentRow=(-1);
	private ResultSet currentResultSet=null;
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex!=currentRow){
			// ���������� �������� ResultSet �� ����
			try{
				if(this.values!=null){
					for(int counter=0;counter<this.values.length;counter++){
						this.statementDataId.setObject(counter+1, this.values[counter]);
					}
					this.statementDataId.setObject(this.values.length+1, this.uniqueId.get(rowIndex));
				}else{
					this.statementDataId.setObject(1, this.uniqueId.get(rowIndex));
				}
				this.currentResultSet=this.statementDataId.executeQuery();
				this.currentRow=rowIndex;
				if(this.currentResultSet.next()==false){
					throw new Exception("data is not exists ");
				}
			}catch(Exception ex){
				System.err.println("JTableWrapModel#getValueAt Exception:"+ex.getMessage());
				this.currentResultSet=null;
				this.currentRow=(-1);
			}
			return this.renderColumns[columnIndex].getCellValue(this.currentResultSet);
		}else{
			// ResultSet ��� ������� - ������� ��������
			return this.renderColumns[columnIndex].getCellValue(this.currentResultSet);
		}
	}
	
	/** �������� ���������� ������������� ����������� ������� 
	 * @param row - ������, �� ������� ����� �������� ���������� ������
	 * @return Object ��� null, ���� �� ������� �������� ������
	 * */
	Object getSelectionId(int row){
		try{
			return this.uniqueId.get(row);
		}catch(Exception ex){
			System.err.println("JTableWrap#getSelectionId Exception: "+ex.getMessage());
			return null;
		}
		
	}
}