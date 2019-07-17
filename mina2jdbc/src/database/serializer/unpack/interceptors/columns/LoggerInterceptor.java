package database.serializer.unpack.interceptors.columns;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;

import database.serializer.common.RecordWrap;

/** �������� ������: �������� � ��������� ���� ��� ���� ��������� ������*/
public class LoggerInterceptor extends ColumnInterceptor{
	private String pathToSave;
	/** �������� ������: �������� � ��������� ���� ��� ���� ��������� ������
	 * @param columnName - ��� �������, ������� ����� ������������� �� ���������� ������� 
	 * @param pathToSave - ������ ���� � ����� ( � ��������� ������������ ) ��� ���������� ������  
	 */
	public LoggerInterceptor(String columnName, String pathToSave){
		super(columnName);
		this.pathToSave=pathToSave;
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
	
	@Override
	public Object processValue(Connection connection, 
							   RecordWrap recordWrap,
							   Object[] currentRow) {
		// �������� ������ �� �������, � ������� �������� ������
		int index=this.getIndexInArray(recordWrap.getFieldNames(), this.getColumnName());
		if(index>=0){
			try{
				// �� ���������� ������� �������� ������ ��� �����
				String filePath=(String)currentRow[index];
				filePath=this.getFileNameFromFullPath(filePath);
				FileOutputStream fos=new FileOutputStream(this.pathToSave+filePath);
				PrintWriter writer=new PrintWriter(new OutputStreamWriter(fos));
				for(int counter=0;counter<currentRow.length;counter++){
					writer.write(counter+" : "+currentRow[counter]);
					writer.write("\n");
				}
				writer.flush();
				fos.close();
				// ��������� � ���������� ����� ��� �������� 
			}catch(Exception ex){
				System.err.println("LoggerInterceptor#processValue Exception: "+ex.getMessage());
			}
		}else{
			System.err.println("LoggerInterceptor Object was not found: "+this.getColumnName());
		}
		return null;
	}

}
