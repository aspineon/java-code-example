package dbfConverter;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.linuxense.javadbf.DBFField;

public class Field {
	private int position;
	private String dbfName;
	private String sqlName;
	private String sqlTypeString;
	private SqlType sqlType;
	private int sqlTypeLength;
	//private DBFField dbfField;
	
	/** ����, ������� �������� ��������� ��������� ����� Dbf � SQL*/
	public Field(int number, DBFField dbfField){
		this.position=number;
		//this.dbfField=dbfField;
		this.dbfName=dbfField.getName();
		this.sqlName=dbfField.getName();
		this.sqlTypeString=this.getSqlTypeFromDbfType(dbfField);
	}

	/** �������������� ��� DBF ����� � ��� SQL*/
	private String getSqlTypeFromDbfType(DBFField field){
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
	
	
	/**
	 * @return the sqlTypeString
	 */
	public String getSqlTypeString() {
		return sqlTypeString;
	}

	/**
	 * @param sqlTypeString the sqlTypeString to set
	 */
	public void setSqlTypeString(String sqlTypeString) {
		this.sqlTypeString = sqlTypeString;
	}

	/**
	 * @return the sqlTypeLength
	 */
	public int getSqlTypeLength() {
		return sqlTypeLength;
	}

	/**
	 * @param sqlTypeLength the sqlTypeLength to set
	 */
	public void setSqlTypeLength(int sqlTypeLength) {
		this.sqlTypeLength = sqlTypeLength;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the dbfName
	 */
	public String getDbfName() {
		return dbfName;
	}

	/**
	 * @param dbfName the dbfName to set
	 */
	public void setDbfName(String dbfName) {
		this.dbfName = dbfName;
	}

	/**
	 * @return the sqlName
	 */
	public String getSqlName() {
		return sqlName;
	}

	/**
	 * @param sqlName the sqlName to set
	 */
	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	/**
	 * @return the sqlType
	 */
	public SqlType getSqlType() {
		return sqlType;
	}

	/**
	 * @param sqlType the sqlType to set
	 */
	public void setSqlType(SqlType sqlType) {
		this.sqlType = sqlType;
	}
	

	public static String getSqlCreateTable(String tableName,Field[] fields){
		StringBuffer sql=new StringBuffer();
		sql.append("CREATE TABLE ");
		sql.append(tableName);
		sql.append(" ( ");
		for(int counter=0;counter<fields.length;counter++){
			sql.append(fields[counter].getSqlName());
			sql.append(" ");
			sql.append(fields[counter].getSqlTypeString());
			if(counter!=(fields.length-1)){
				sql.append(", ");
			}
		}
		sql.append(" ) ");
		return sql.toString();
	}

	
	/** 
	 * @param frame - ������������ Frame
	 * @param tableName - ��� �������  
	 * @param fields - ���� ��� �������������� 
	 * */
	public static String showModalPanel(JFrame frame, String tableName, Field[] fields, String errorMessage){
		CorrectPanel panelName=new CorrectPanel("��� ������� ���� ������:",tableName,tableName);
		CorrectPanel[] panels=new CorrectPanel[fields.length];
		for(int counter=0;counter<fields.length;counter++){
			panels[counter]=new CorrectPanel("",fields[counter].getSqlName(),fields[counter].getSqlName());
		}
		// placing 
		JPanel panelMain=new JPanel(new BorderLayout());
		JPanel panelCorrect=new JPanel(new GridLayout(fields.length+1,1));
		panelCorrect.add(panelName);
		for(int counter=0;counter<fields.length;counter++){
			panelCorrect.add(panels[counter]);
		}
		panelMain.add(new JScrollPane(panelCorrect),BorderLayout.NORTH);
		panelMain.add(new JScrollPane(new JTextArea(errorMessage,5,40)),BorderLayout.CENTER);
		JButton buttonDone=new JButton("Done");
		final JDialog dialog=new JDialog(frame,"Correct fields",Dialog.ModalityType.APPLICATION_MODAL);
		buttonDone.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.setVisible(false);
			}
		});
		// ���������� ������ � ���� ���������� ����
		JScrollPane pane=new JScrollPane(panelMain);
		pane.getVerticalScrollBar().setVisible(false);

		JPanel panel=new JPanel(new BorderLayout());
		panel.add(pane,BorderLayout.CENTER);
		panel.add(buttonDone,BorderLayout.SOUTH);
		dialog.getContentPane().add(panel);
		dialog.pack();
		dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		dialog.setVisible(true);
		// ������� ������ �� ���������� �����������  
		tableName=panelName.getDestination();
		for(int counter=0;counter<fields.length;counter++){
			fields[counter].setSqlName(panels[counter].getDestination());
		}
		return tableName;
	}

	/** �������� ������ ������ Double, Integer, Varchar, Timestamp*/
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
	
}

class CorrectPanel extends JPanel{
	/** */
	private static final long serialVersionUID = 1L;
	private JTextField fieldSource;
	private JTextField fieldDestination;
	
	public CorrectPanel(String caption,String source, String destination){
		super(new GridLayout(1,2));
		this.fieldSource=new JTextField(source);
		this.fieldSource.setEditable(false);
		this.fieldDestination=new JTextField(destination);
		this.add(fieldSource);
		this.add(fieldDestination);
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(caption));
	}
	
	public String getSource(){
		return this.fieldSource.getText();
	}
	public String getDestination(){
		return this.fieldDestination.getText();
	}
}
