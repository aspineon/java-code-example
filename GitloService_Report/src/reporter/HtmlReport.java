package reporter;

import java.io.File;
import java.sql.ResultSet;

/** ����� ������� ���������� ������ ResultSet �������� ����� ������� � ����������� � ���� ����������� */
public class HtmlReport {
	private File patternFile;
	/** ����������� ResultSet �� ��������� ������� � ������� 
	 * @param pathToPattern - ���� � �����-�������, ��� ���������� �������
	 * @throws Exception ���� �� ������� ���������� ����-������
	 * */
	public HtmlReport(String pathToPattern) throws Exception {
		patternFile=new File(pathToPattern);
		if(patternFile.exists()==false){
			throw new Exception("File not exists:"+pathToPattern);
		}
	}

	/** ������� ����� �� ResultSet � ��������� ���� 
	 * @param resultSet - ����� ������, �� �������� ����� ������� ����� 
	 * @param pathToFile - ����, � ������� ������ ��� ������ ���� ��������� 
	 * @throw Exception - ���� �������� �����-���� ������   
	 * */
	public void outReportToFile(ResultSet resultSet,String pathToFile ) throws Exception {
	}
	
	
	
}
