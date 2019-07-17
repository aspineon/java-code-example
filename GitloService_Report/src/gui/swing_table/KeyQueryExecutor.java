package gui.swing_table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/** �����, ������� ������ ��� ��������� �������������� �������� ��� ���� � ��� ������ */
public class KeyQueryExecutor {
	/** ������� ���������� ���������� */
	@SuppressWarnings("unused")
	private void debug(Object information){
		System.out.print("KeyQueryExecutor: ");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}
	/** ������� ���������� �� ������� */
	private void error(Object information){
		System.out.print("KeyQueryExecutor: ");
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	private PreparedStatement statementForData;
	private ArrayList<ArrayList<Object>> keys;
	public int length=0;
	/** ������, ������� ������ ��� ��������� ResultSet �� ��������� ������ 
	 * @param statementForData - ������ ��� ������
	 * @param keys - ������ �� ArrayList, ������� ������������ � �������� ������ ��� ������� � PreparedStatement
	 * (��� ��� ������� � ArrayList ������ ������������� ������� ������� ������ � PreparedStatement)
	 * */
	public KeyQueryExecutor(PreparedStatement statementForData,ArrayList<ArrayList<Object>> keys){
		this.statementForData=statementForData;
		this.keys=keys;
		this.length=keys.size();
	}

	/** �������� ����� ����� �� ��������� ������� � ���������� � ����� ������ */
	private ArrayList<ArrayList<Object>> getKeys(String query, Connection connection){
		ArrayList<ArrayList<Object>> returnValue=null;
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery(query);
			int columnCount=rs.getMetaData().getColumnCount();
			returnValue=new ArrayList<ArrayList<Object>>();
			while(rs.next()){
				ArrayList<Object> currentRow=new ArrayList<Object>();
				for(int counter=0;counter<columnCount;counter++){
					currentRow.add(rs.getObject(counter+1));
				}
				this.keys.add(currentRow);
			}
		}catch(Exception ex){
			error("Constructor Exception: "+ex.getMessage());
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	/** �������(�� �������� � ������� �������): �������� ����� ����� �� ��������� ������� � ���������� � ����� ������ */
	private ArrayList<ArrayList<Object>> getKeys(ResultSet rs){
		ArrayList<ArrayList<Object>> returnValue=null;
		try{
			int columnCount=rs.getMetaData().getColumnCount();
			returnValue=new ArrayList<ArrayList<Object>>();
			while(rs.next()){
				ArrayList<Object> currentRow=new ArrayList<Object>();
				for(int counter=0;counter<columnCount;counter++){
					currentRow.add(rs.getObject(counter+1));
				}
				returnValue.add(currentRow);
			}
		}catch(Exception ex){
			error("Constructor Exception: "+ex.getMessage());
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** ������, ������� ������ ��� ��������� ResultSet �� ��������� ������ 
	 * @param statementForData - ������ ��� ������
	 * @param query - ������, ������� ����� ��������� �� ��������� connection, � ��� ���������� ������ ������� ��� ����� � ArrayList&ltObject&gt[]
	 * @param connection - ���������� � ����� ������, �� ��������� �������� ����� �������� ������ 
	 * */
	public KeyQueryExecutor(PreparedStatement statementForData,
						    String query, 
						    Connection connection){
		this.statementForData=statementForData;
		this.keys=this.getKeys(query, connection);
		this.length=keys.size();
	}

	/** ������, ������� ������ ��� ��������� ResultSet �� ��������� ������ 
	 * @param statementForKey - ������ ��� ������
	 * @param rs - ����� ������ ��� ������
	 * */
	public KeyQueryExecutor(PreparedStatement statementForData,
						    ResultSet rs){
		this.statementForData=statementForData;
		this.keys=this.getKeys(rs);
		this.length=keys.size();
	}
	
	/** ���������� ����� ����� ��� ������� */
	public void setNewKeys(ArrayList<ArrayList<Object>> newKeys){
		this.keys=newKeys;
		this.length=this.keys.size();
	}
	
	/** ���������� ����� ����� �� ��������� ������� � ���������� � ����� ������
	 * @param query - ������ � ���� ������, ������� �������� ������ ����������� ���� ��� ��������� ������
	 * @param connection - ���������� � ����� ������ 
	 * */
	public void setNewKeys(String query, Connection connection){
		this.keys=this.getKeys(query,connection);
		this.length=this.keys.size();
	}
	
	/** ���������� ����� ����� �� ��������� ������ ������ ( ResultSet )
	 * @param rs - ����� ������, ������� �������� ������ ����������� ����� 
	 * */
	public void setNewKeys(ResultSet rs){
		this.keys=this.getKeys(rs);
		this.length=this.keys.size();
	}
	
	/** �������� �� ��������� ������� ResultSet 
	 * @param rowIndex - ������ �����, ������� ����� ����������� � ResultSet
	 * */
	public ResultSet getResultSetBody(int rowIndex){
		ResultSet returnValue=null;
		try{
			this.statementForData.clearParameters();
			for(int counter=0;counter<this.keys.get(rowIndex).size();counter++){
				this.statementForData.setObject(counter+1,this.keys.get(rowIndex).get(counter));
			}
			returnValue=this.statementForData.executeQuery();
		}catch(Exception ex){
			error("getResultSetBody: Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** �������� �� ��������� ������ ResultSet*/
	public ResultSet getResultSetBody(ArrayList<Object> keyValues){
		ResultSet returnValue=null;
		try{
			this.statementForData.clearParameters();
			for(int counter=0;counter<keyValues.size();counter++){
				this.statementForData.setObject(counter+1,keyValues.get(counter));
			}
			returnValue=this.statementForData.executeQuery();
		}catch(Exception ex){
			error("getResultSetBody: Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** �������� ����� ������ ��� ���������� rowIndex 
	 * @param rowIndex - ������, �� ������� ����� �������� ������
	 * */
	public ArrayList<Object> getKeys(int rowIndex){
		return this.keys.get(rowIndex);
	}
	
}
