package common;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.linuxense.javadbf.DBFField;

import database.SqlReservWords;
import database.SqlType;


/** ������, ������� ������������� ��� ������ �� ����� � ���� */
public class Field {
	private int position;
	private DBFField field;
	private SqlType sqlType=SqlType.VARCHAR;
	private String sqlFieldName;
	private int sqlTypeLength=100;
	private JComponent visualComponent=null;
	
	public Field(DBFField field, int position){
		this.field=field;
		this.position=position;
		// ��������, ���������� ��������������� ����� �� �������
		this.sqlFieldName=this.replaceSqlFieldName(this.field.getName());
		this.visualComponent=createJComponent();
	}
	
	public SqlType getSqlType(){
		return this.sqlType;
	}
	public int getSqlLength(){
		return sqlTypeLength;
	}
	
	private String replaceSqlFieldName(String sqlName){
		if(SqlReservWords.isSqlOperator(sqlName)){
			sqlName=sqlName+"_";
		}
		return sqlName;
	}
	
	private JComponent createJComponent(){
		return new JCheckBox(this.sqlFieldName,true);
	}
	
	public void setComponentBackground(Color color){
		this.visualComponent.setBackground(color);
	}
	
	public JComponent getJComponent(){
		return this.visualComponent;
	}
	
	public DBFField getDbfField(){
		return this.field;
	}

	public int getFieldPosition(){
		return this.position;
	}

	public String getSqlFieldName(){
		return this.sqlFieldName;
	}
	
	/** �������������� ��� DBF ����� � ��� SQL*/
	public String getSqlTypeFromDbfType(){
		if(field.getDataType()==DBFField.FIELD_TYPE_D){
			// Date
			this.sqlType=SqlType.TIMESTAMP;
			this.sqlTypeLength=0;
			return "TIMESTAMP";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_F){
			// Double
			this.sqlType=SqlType.DOUBLE;
			this.sqlTypeLength=0;
			return "FLOAT";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_L){
			// Logical
			this.sqlType=SqlType.VARCHAR;
			this.sqlTypeLength=5;
			return "VARCHAR(5)";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_M){
			return "VARCHAR("+field.getFieldLength()+")";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_N){
			// Numeric // INTEGER
			this.sqlTypeLength=0;
			this.sqlType=SqlType.DOUBLE;
			return "FLOAT";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_C){
			// Character
			this.sqlTypeLength=field.getFieldLength();
			this.sqlType=SqlType.VARCHAR;
			return "VARCHAR("+field.getFieldLength()+")";
		}
		this.sqlType=SqlType.VARCHAR;
		this.sqlTypeLength=field.getFieldLength();
		return "VARCHAR("+field.getFieldLength()+")";
	}
	
	public Object getObjectForSql(Object object) {
		if(object==null){
			return null;
		}
		if(this.sqlType==SqlType.DOUBLE){
			try{
				return new Double(object.toString());
			}catch(Exception ex){
				return new Double(0);
			}
		}
		if(this.sqlType==SqlType.TIMESTAMP){
			try{
				java.sql.Date returnValue=new java.sql.Date(((java.util.Date)(object)).getTime());
				return returnValue;
			}catch(Exception ex){
				java.sql.Date returnValue=new java.sql.Date(  (new java.util.Date(0)).getTime());
				return returnValue;
			}
		}
		if(this.sqlType==SqlType.INTEGER){
			try{
				Double value=new Double(object.toString());
				int intValue=value.intValue();
				return new Integer(intValue);
			}catch(Exception ex){
				return new Integer(0);
			}
		}
		if(this.sqlType==SqlType.VARCHAR){
			try{
				return (String)(object.toString());
			}catch(Exception ex){
				return "";
			}
		}
		return object;
	}
	
	
	/** �������������� ����������� ������ �� DBF � "����������" ������������� ������� ���� */
	public static String convertDosString(String value){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<value.length();counter++){
			returnValue.append(getCharFromInteger(Integer.valueOf(value.charAt(counter)),value.charAt(counter)));
		}
		return returnValue.toString();
	}
	
	
	public static PreparedStatement getPreparedStatement(Connection connection, String tableName, Field[] fields) throws SQLException{
		StringBuffer query=new StringBuffer();
		query.append("INSERT INTO "+tableName+" (");
		for(int counter=0;counter<fields.length;counter++){
			query.append(fields[counter].getSqlFieldName());
			if(counter!=(fields.length-1)){
				query.append(", ");
			}
		}
		query.append(")");
		query.append(" VALUES(");
		for(int counter=0;counter<fields.length;counter++){
			query.append("?");
			if(counter!=(fields.length-1)){
				query.append(", ");
			}
		}
		query.append(")");
		return connection.prepareStatement(query.toString());		
	}
	
	/** �������������� ������� �������� � "���������� ����������� "*/
	private static char getCharFromInteger(int value,char defaultValue){
		if(value==(128))return '�';
		if(value==(129))return '�';
		if(value==(130))return '�';
		if(value==(131))return '�';
		if(value==(132))return '�';
		if(value==(133))return '�';
		if(value==(134))return '�';
		if(value==(135))return '�';
		if(value==(136))return '�';
		if(value==(137))return '�';
		if(value==(138))return '�';
		if(value==(139))return '�';
		if(value==(140))return '�';
		if(value==(141))return '�';
		if(value==(142))return '�';
		if(value==(143))return '�';
		if(value==(144))return '�';
		if(value==(145))return '�';
		if(value==(146))return '�';
		if(value==(147))return '�';
		if(value==(148))return '�';
		if(value==(149))return '�';
		if(value==(150))return '�';
		if(value==(151))return '�';
		if(value==(152))return '�';
		if(value==(153))return '�';
		if(value==(154))return '�';
		if(value==(155))return '�';
		if(value==(156))return '�';
		if(value==(157))return '�';
		if(value==(158))return '�';
		if(value==(159))return '�';
		if(value==(160))return '�';
		if(value==(161))return '�';
		if(value==(162))return '�';
		if(value==(163))return '�';
		if(value==(164))return '�';
		if(value==(165))return '�';
		if(value==(166))return '�';
		if(value==(167))return '�';
		if(value==(168))return '�';
		if(value==(169))return '�';
		if(value==(170))return '�';
		if(value==(171))return '�';
		if(value==(172))return '�';
		if(value==(173))return '�';
		if(value==(174))return '�';
		if(value==(175))return '�';
		if(value==(224))return '�';
		if(value==(225))return '�';
		if(value==(226))return '�';
		if(value==(227))return '�';
		if(value==(228))return '�';
		if(value==(229))return '�';
		if(value==(230))return '�';
		if(value==(231))return '�';
		if(value==(232))return '�';
		if(value==(233))return '�';
		if(value==(234))return '�';
		if(value==(235))return '�';
		if(value==(236))return '�';
		if(value==(237))return '�';
		if(value==(238))return '�';
		if(value==(239))return '�';
		//if(value==())return '_';
		if(value==(240))return '�';
		if(value==(242))return '�';
		if(value==(244))return '�';
		//if(value==())return '_';
		//if(value==())return '_';
		//if(value==())return '_';
		if(value==(241))return '�';
		if(value==(252))return '�';
		if(value==(243))return '�';
		//if(value==())return '_';
		//if(value==())return '_';
		//if(value==())return '_';
		if(value==(245))return '�';
		return defaultValue;
	}
	
	public static String getSqlCreateTable(String tableName,Field[] fields){
		StringBuffer sql=new StringBuffer();
		sql.append("CREATE TABLE ");
		sql.append(tableName);
		sql.append(" ( ");
		for(int counter=0;counter<fields.length;counter++){
			sql.append(fields[counter].getSqlFieldName());
			sql.append(" ");
			sql.append(fields[counter].getSqlTypeFromDbfType());
			if(counter!=(fields.length-1)){
				sql.append(", ");
			}
		}
		sql.append(" ) ");
		return sql.toString();
	}

	public boolean isSelected(){
		try{
			return ((JCheckBox)this.visualComponent).isSelected();
		}catch(Exception ex){
			return false;
		}
	}
}
