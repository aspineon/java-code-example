package database.serializer.pack.column;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.ResultSet;

/** �������� �������, ������� �������� ��� ����� ��� ��������� � ���� �������  */
public class FileFromColumn implements IRecordColumn {
	/** ��� ������� ��� ����������� � ������� �������� ������ */
	private String columnName=null;
	/** ��� ������� � ���� ������ ��� ��������� ������� ���� � ����� */
	private String columnWithPathToFile=null;
	/** ������� ��� ����������� � ����������� ��������� ���� */
	private String prefixPath=null;
	
	/** �������� �������, ������� �������� ��� ����� ��� ��������� � ���� �������  
	 * @param columnName ��� ������� ��� ����������� � ������� �������� ������
	 * @param columnWithPathToFile ��� ������� � ���� ������ ��� ��������� ������� ���� � �����
	 */
	public FileFromColumn(String columnName, String columnWithPathToFile){
		this.columnName=columnName;
		this.columnWithPathToFile=columnWithPathToFile;
	}

	/** �������� �������, ������� �������� ��� ����� ��� ��������� � ���� �������  
	 * @param columnName ��� ������� ��� ����������� � ������� �������� ������
	 * @param columnWithPathToFile ��� ������� � ���� ������ ��� ��������� ������� ���� � �����
	 * @param prefixPath - �������, ������� ����� ��������� � �������� �� ������� ���� ������ (��� ������� ���� � �����) 
	 * */
	public FileFromColumn(String columnName, String columnWithPathToFile, String prefixPath ){
		this(columnName, columnWithPathToFile);
		this.prefixPath=prefixPath;
	}
	
	@Override
	public String getColumnName() {
		return this.columnName;
	}

	@Override
	public Object getObject(ResultSet rs) {
		try{
			// �������� ��� �����
			String pathToFile=rs.getString(this.columnWithPathToFile);
			if(prefixPath!=null){
				pathToFile=prefixPath+pathToFile;
			}
			return this.getByteArrayFromFile(pathToFile);
		}catch(Exception ex){
			System.err.println("FileFromColumn#getObject Exception: "+ex.getMessage());
			return null;
		}
	}

	
	/** �������� �� ���������� ����� ������ �� ���� */
	private byte[] getByteArrayFromFile(String pathToFile){
		try{
			FileInputStream fis=new FileInputStream(pathToFile);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			byte[] buffer=new byte[1024];
			int size=0;
			while((size=fis.read(buffer))>=0){
				baos.write(buffer,0,size);
			}
			return baos.toByteArray();
		}catch(Exception ex){
			System.err.println("FileFromColumn Exception: "+ex.getMessage());
			return null;
		}
	}
}
