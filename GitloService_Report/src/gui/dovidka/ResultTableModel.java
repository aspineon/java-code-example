package gui.dovidka;

import gui.table_column_render.ICellValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ResultTableModel extends AbstractTableModel{
	/** */
	private static final long serialVersionUID = 1L;
	private Logger logger=Logger.getLogger(this.getClass());
	{
		logger.setLevel(Level.DEBUG);
	}
	/** ������� ���������� � ����� ������ */
	private Connection connection;
	
	/** Primary Key �����-������, ������� ���������� � ������� */
	private ArrayList<Integer> data;
	/** ������� � ������� */
	private ICellValue[] columns;
	/** preparedStatement ��� ������� ��������� ����� ������ �� ���� ������ */
	private PreparedStatement statementRow;
	/** preparedStatement ��� ������*/
	private PreparedStatement statementPrint;
	/** preparedStatement ��� ������� */
	private PreparedStatement statementProplata;
	
	/** 
	 * ������ ���� ������ 
	 * @param connection ���������� � ����� ������ 
	 * @param sqlQuery ������ � ���� ������ 
	 * @param columnReference - ������ �� ������� �������� �� �������, ������� ������� ���������� 
	 * */
	public ResultTableModel(Connection connection, 
							ICellValue[] columns){
		logger.debug("constructor:begin");
		this.connection=connection;
		this.columns=columns;
		this.data=new ArrayList<Integer>(300);
		
		logger.debug("constructor:end");
	}
	
	/** �������� ������ */
	public void clearData(){
		this.data.clear();
	}
	/** ���������� ������ � �������� ������ 
	 * @param sqlQuery - ���� ��������� �������
	 * @param sqlWhere - ���� ������� ��� ��������� �������
	 * @param sqlOrder - ���� ���������� ��� ��������� �������
	 */
	public void refresh(String sqlQuery, 
						String sqlWhere, 
						String sqlOrder,
						String sqlProplata){
		logger.debug("refresh:begin");
		try{
			Statement statement=this.connection.createStatement();
			try{
				statementRow=connection.prepareStatement(sqlQuery+sqlWhere+"?");
				statementPrint=connection.prepareStatement(sqlQuery+sqlWhere+"?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				statementProplata=connection.prepareStatement(sqlProplata,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			}catch(Exception ex){
				logger.error("constructor error:"+ex.getMessage());
			}
			logger.debug(sqlQuery+sqlOrder);
			ResultSet resultSet=statement.executeQuery(sqlQuery+sqlOrder);
			data.clear();
			while(resultSet.next()){
				data.add(new Integer((new Double(resultSet.getString(1))).intValue()));
			}
			logger.debug("ResultSet size:"+data.size());
		}catch(Exception ex){
			logger.error("refresh:"+ex.getMessage());
		}
		this.fireTableDataChanged();
		logger.debug("refresh:end");
	}
	
	/** �������� ����� ������ �� ���������� ������ */
	public ResultSet getResultSetByIndex(int index){
		try{
			this.statementPrint.setInt(1, this.data.get(index));
			return this.statementPrint.executeQuery();
		}catch(Exception ex){
			logger.error("getResultSetByIndex Error:"+ex.getMessage());
			return null;
		}
	}
	/** �������� ����� ������ ��� ������� 
	 * @param tna 
	 * @param tn1 
	 * */
	public ResultSet getResultSetForProplata(Float tna, Float tn1){
		try{
			statementProplata.clearParameters();
			statementProplata.setFloat(1, tna);
			statementProplata.setFloat(2, tn1);
			return this.statementProplata.executeQuery();
		}catch(Exception ex){
			logger.error("getResultSetForProplata Error:"+ex.getMessage());
			return null;
		}
	}
	
	@Override
	public int getColumnCount() {
		return this.columns.length;
	}

	@Override
	public int getRowCount() {
		return this.data.size();
	}
	
	/** ������, ������� �������� ������� ������ �� ������� ���� ������ */
	private ResultSet currentRow;
	/** ���������� ������������� ������ � ������� ���� ������ */
	private int currentRowIndex=-1;
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String returnValue=null;
		if(currentRowIndex==rowIndex){
			try{
				return  this.columns[columnIndex].getCellValue(currentRow);
			}catch(Exception ex){
				logger.error("get prepared ResultSet error:"+ex.getMessage());
			}
		}else{
			this.currentRowIndex=rowIndex;
			// load from database by record from "data"
			try{
				this.statementRow.setInt(1, this.data.get(rowIndex));
				currentRow=this.statementRow.executeQuery();
				if(currentRow.next()){
					returnValue=this.columns[columnIndex].getCellValue(currentRow);
				}
			}catch(Exception ex){
				logger.error("Load data from database Error: Row:"+rowIndex+"   Column:"+columnIndex);
			}
		}
		return (returnValue==null)?"":returnValue;
	}
	
}
