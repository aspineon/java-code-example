package gui.table_column_render;
import java.sql.ResultSet;

/** ���������, ������� ������������� ����������� ��������� ������ �� ��������� ResultSet */
public interface ICellValue {
	/** �������� ��������� �������� �� ResultSet */
	public String getCellValue(ResultSet rs);
}
