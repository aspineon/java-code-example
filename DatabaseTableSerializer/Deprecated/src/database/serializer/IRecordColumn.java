package database.serializer;

import java.sql.ResultSet;

public interface IRecordColumn {
	/** ��� ������� ��� ���������� � ������� */
	public String getColumnName();
	
	public Object getObject(ResultSet rs);
}
