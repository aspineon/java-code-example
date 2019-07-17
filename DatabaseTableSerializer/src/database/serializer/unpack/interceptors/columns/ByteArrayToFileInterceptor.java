package database.serializer.unpack.interceptors.columns;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;

import database.serializer.common.RecordWrap;

/*����������� ��� �������, ������� �������� byte[] ������ ��� ������ � ���� � ��� ����� � ������ ������*/
public class ByteArrayToFileInterceptor extends ColumnInterceptor{
	private String columnFileName;
	private String path;
	
	/** ����������� ��� �������, ������� �������� byte[] ������ ��� ������ � ���� � ��� ����� � ������ ������  
	 * @param columnName - ��� �������, � ������� ��������� ������ �� byte[]
	 * @param columnFileName - ��� �������, � ������� ����� ��� �����
	 * @param path - ������ ���� � ����� ��� ���������� 
	 */
	public ByteArrayToFileInterceptor(String columnName,String columnFileName, String path){
		super(columnName);
		this.columnFileName=columnFileName;
		this.path=path;
	}

	@Override
	public boolean isForDatabaseSave() {
		return false;	
	}

	/** �������� �� ������� ����� ����� ������ ��� - ����� ��������� ������������ 
	 * @param fullPath - ������ ���� � �����
	 * @return - ������ ��� ����� ��� ������������ 
	 */
	private String getFileNameFromFullPath(String fullPath){
		int separatorFirst=fullPath.lastIndexOf("/");
		int separatorSecond=fullPath.lastIndexOf("\\");
		int lastIndexOfSeparator=(separatorFirst>separatorSecond)?separatorFirst:separatorSecond;
		if(lastIndexOfSeparator>=0){
			return fullPath.substring(lastIndexOfSeparator+1);
		}else{
			return fullPath;
		}
	}
	
	/** �������� ������ ���� � ���� */
	private void writeByteArrayToFile(String pathToFile, byte[] arrayOfByte) throws IOException{
		byte[] buffer=new byte[1024];
		int readedByte=0;
		FileOutputStream fos=new FileOutputStream(pathToFile);
		ByteArrayInputStream bais=new ByteArrayInputStream(arrayOfByte);
		while((readedByte=bais.read(buffer))!=(-1)){
			fos.write(buffer,0,readedByte);
		}
		bais.close();
		fos.flush();
		fos.close();
	}
	
	
	@Override
	public Object processValue(Connection connection, RecordWrap recordWrap,Object[] currentRow) {
		try{
			
			int indexOfArray=this.getIndexInArray(recordWrap.getFieldNames(), this.getColumnName());
			int indexOfFile=this.getIndexInArray(recordWrap.getFieldNames(), this.columnFileName);
			// �������� ������ �� ����
			byte[] arrayOfByte=(byte[])currentRow[indexOfArray];
			// �������� ��� �����
			String pathToFile=this.getFileNameFromFullPath((String)currentRow[indexOfFile]);
			// ��������� � ����� ������ �� ����
			if(this.path!=null){
				pathToFile=this.path+pathToFile;
			}
			this.writeByteArrayToFile(pathToFile, arrayOfByte);
		}catch(Exception ex){
			System.err.println("ByteArrayToFileInterceptor#processValue Exception: "+ex.getMessage());
		}
		// TODO Auto-generated method stub
		return null;
	}

}
