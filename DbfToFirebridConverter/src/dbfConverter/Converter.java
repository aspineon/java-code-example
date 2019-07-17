package dbfConverter;

import java.io.*;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
/** ��������������� �� �������� c DBF ������� � ���� ������ JDBC, ��������� JDBC Connection */
public class Converter {
	/** Logger */
	private Logger logger=Logger.getLogger(this.getClass());
	{
		logger.setLevel(Level.WARN);
	}
	/** ���� � �������� � ������� DBF */
	private File directory;
	/** ���������� � ����� ������ */
	private Connection connection;
	/** ���� � �����, � ������� ����� ������ Log */
	private File logFile;
	/** ����, ������� ������� � ������������� ������ ������ � ��� */
	private boolean flagOutToLog=false;
	/** Log Writer */
	private BufferedWriter logWriter;
	
	/** ������������ ����� ��� ����������� ������ ��������� ����� ���� ������ */
	private JFrame frameParent;
	
	/** ��������������� �� �������� c DBF ������� � ���� ������ JDBC, ��������� JDBC Connection 
	 * @param directory - ������� �� �������� ����� ����� ����� DBF 
	 * @param connection - ���������� � ����� ������, � ������� ����� �������� SQL ������� 
	 * @param logFile - Log file ���� ����� ������ ������ � ��������� 
	 * */
	public Converter(JFrame parentFrame, 
					 File directory, 
					 Connection connection, 
					 File logFile){
		this.directory=directory;
		this.connection=connection;
		this.logFile=logFile;
		this.flagOutToLog=true;
		try{
			logWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.logFile)));
			this.flagOutToLog=true;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(parentFrame, "LogFile Error:"+ex.getMessage());
		};
	}
	
	public void finalize(){
		if(logWriter!=null){
			try{
				logWriter.close();
			}catch(Exception ex){};
		}
	}

	/** ������ ��������� ������ �� DBF � Connection 
	 * @param true - ���� �� ��������� ��������� ������ 
	 * */
	public boolean convert(){
		boolean returnValue=true;
		// �������� ��� DBF ����� �� ��������
		File[] listOfFiles=this.directory.listFiles(new ExtensionFileFilter("dbf"));
		logger.debug("List of file:"+listOfFiles.length);
		for(int counter=0;counter<listOfFiles.length;counter++){
			logger.debug(counter+":"+listOfFiles[counter]);
			// ������� � ���� ����������� ������� � ������ �� DBF �����
			StringWrap tableName=new StringWrap(listOfFiles[counter].getName().substring(0,listOfFiles[counter].getName().length()-4));
			
			Field[] fields;
			if((fields=createTable(tableName,listOfFiles[counter],this.connection,true))!=null){
				// �������� ��� ������ �� DBF � Connection
				if(this.writeData(listOfFiles[counter],this.connection,tableName.getValue(),fields)==false){
					writeToLog("Error in create Table: "+listOfFiles[counter].getAbsolutePath());
					returnValue=false;
				}
			}else{
				writeToLog("Error in create Table: "+listOfFiles[counter].getAbsolutePath());
				returnValue=false;
			}
		}
		return returnValue;
	}
	
	
	/** ������� �� ��������� ���� � DBF ����� ������� � ���� ������ � ������� �� ��� 
	 * @param dbfFile - ���� � DBF ����� 
	 * @param connection - ���������� � ����� ������
	 * @param removeBeforeCreate - ����������� ������� ������� ����� �� ��������� 
	 * @return null ���� ��������� ������ �� ����� ��������   
	 * */
	private Field[] createTable(StringWrap tableName, File dbfFile, Connection connection, boolean removeBeforeCreate){
		Field[] returnValue=null;
		FileInputStream fis=null;
		try{
			// read all headers
			fis=new FileInputStream(dbfFile);
			DBFReader reader = new DBFReader(fis);
			int fieldCount=reader.getFieldCount();
			DBFField[] dbfFields=new DBFField[fieldCount];
			Field[] fields=new Field[fieldCount];
			for(int counter=0;counter<fieldCount;counter++){
				dbfFields[counter]=reader.getField(counter);
				fields[counter]=new Field(counter,dbfFields[counter]);
			}
			// all field was received
			
			if(removeBeforeCreate){
				// TODO remove table before create
				try{
					this.connection.createStatement().executeUpdate("DROP TABLE "+tableName);
					this.connection.commit();
					writeToLog("table was dropped");
				}catch(Exception ex){
					logger.debug("DropTable "+tableName+"Exception:"+ex.getMessage());
				}
			}
			
			while(true){
				String sql=null;
				try{
					sql=Field.getSqlCreateTable(tableName.getValue(), fields);
					logger.debug("Create Table: "+sql);
					this.connection.createStatement().executeUpdate(sql.toString());
					this.connection.commit();
					break;
				}catch(Exception ex){
					// SQL ������ �� �������� - ������� - ex.getMessage()  
					if(JOptionPane.showConfirmDialog(this.frameParent, "Correct table:"+tableName,"",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
						tableName.setValue(Field.showModalPanel(frameParent, tableName.getValue(), fields, sql+"\n\n"+ex.getMessage()));
					}else{
						break;
					}
				}
			}
			returnValue=fields;
		}catch(Exception ex){
			writeToLog(ex.getMessage());
		}finally{
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception ex){};
			}
		}
		
		return returnValue;
	}
	
	
	/** 
	 * �������� ������ �� �������-����� DBF � Connection c ������������ ������ � ���������� ������
	 * @param dbfFile - ���� DBF
	 * @param connection - ���������� � ����� ������
	 * @param tableName - ��� ������� � ���� ������, � ������� ����� ������ Insert
	 * @param fields - ����, ������� ���������� ��� ��������� ������
	 */
	private boolean writeData(File dbfFile, 
							  Connection connection, 
							  String tableName, 
							  Field[] fields){
		boolean returnValue=true;
		FileInputStream fis=null;
		try{
			fis=new FileInputStream(dbfFile);
			DBFReader reader=new DBFReader(fis);
			PreparedStatement statement=this.getPreparedStatement(connection, tableName,fields);
			Object[] row;
			while( (row=reader.nextRecord())!=null){
				statement.clearParameters();
				for(int counter=0;counter<fields.length;counter++){
					if(row[counter] instanceof String){
						logger.debug(counter+" : "+Field.convertDosString((String)fields[counter].getObjectForSql(row[counter])));
						statement.setObject(counter+1, Field.convertDosString((String)fields[counter].getObjectForSql(row[counter])));
					}else{	
						if(row[counter]!=null){
							logger.debug(counter+":"+row[counter].getClass().getName());
							statement.setObject(counter+1, fields[counter].getObjectForSql(row[counter]));
						}else{
							statement.setObject(counter+1, fields[counter].getObjectForSql(row[counter]));
						}
						
					}
					
				}
				try{
					statement.executeUpdate();
					connection.commit();
				}catch(Exception ex){
					this.writeToLog("Execute Exception: "+ex.getMessage());
					returnValue=false;
				}
			}
		}catch(Exception ex){
			this.writeToLog("writeData:"+ex.getMessage());
			returnValue=false;
		}finally{
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception ex){};
			}
		}
		 
		return returnValue;
	}
	
	/** �������� PreparedStatement 
	 * @throws SQLException */
	private PreparedStatement getPreparedStatement(Connection connection, String tableName, Field[] fields) throws SQLException{
		StringBuffer query=new StringBuffer();
		query.append("INSERT INTO "+tableName+" (");
		for(int counter=0;counter<fields.length;counter++){
			query.append(fields[counter].getSqlName());
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
	
	
	private void writeToLog(Object information){
		if(this.flagOutToLog==true){
			try{
				logWriter.write(information.toString());
				logWriter.flush();
				logger.info(information.toString());
			}catch(Exception ex){
				logger.error("writeToLog Exception:"+ex.getMessage());
			};
		}
	}
}


class ExtensionFileFilter implements java.io.FileFilter{

	private String[] ext;
	
	public ExtensionFileFilter(String ... ext){
		if(ext==null){
			this.ext=new String[]{"txt"};
		}
		this.ext=ext;
	}

	@Override
	public boolean accept(File arg0) {
		return isFilter(getExtension(arg0));
	}
	
	/** ������� True ���� ������ ���������� ���� � �������  */
	private boolean isFilter(String value){
		boolean returnValue=false;
		for(int counter=0;counter<ext.length;counter++){
			if(ext[counter].equalsIgnoreCase(value)){
				returnValue=true;
				break;
			}
		}
		return returnValue;
	}
	
	/** �������� ���������� ����� */
	private String getExtension(File file){
		String fileName=file.getName();
		int dotPosition=fileName.lastIndexOf(".");
		if(dotPosition>0){
			return fileName.substring(dotPosition+1);
		}else{
			return "";
		}
	}

}

class StringWrap{
	private String value;
	
	public StringWrap(String value){
		this.value=value;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String toString(){
		return value;
	}
}