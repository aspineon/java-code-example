package database.serializer.pack.column;

import java.sql.ResultSet;

public interface IRecordColumn {
	/** ��� ������� ��� ���������� � ������� */
	public String getColumnName();
	
	public Object getObject(ResultSet rs);
}
