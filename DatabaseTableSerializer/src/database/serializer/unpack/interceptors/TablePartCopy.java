package database.serializer.unpack.interceptors;

import java.sql.Connection;
import database.serializer.common.RecordWrap;

/** ������� ��� �������, �������� �������� ������ ��������� ���������� ���� � ���� ������  */
public class TablePartCopy extends Interceptor {
	private TablePartColumns columns;
	
	/** ������� ��� �������, �������� �������� ������ ��������� ���������� ���� � ���� ������
	 * @param interceptor - ��� ������������
	 * @param fieldsForSave
	 */
	public TablePartCopy(String interceptor, TablePartColumns columns) {
		super(interceptor);
		this.columns=columns;
	}

	@Override
	public boolean processRecord(RecordWrap recordWrap, Connection connection) throws Exception {
		// �������� PreparedStatement
		if(this.columns.recalculateRecord(recordWrap, connection)){
			// ����������� �� ���� ������� ���������� ������ 
			for(int rowCounter=0;rowCounter<recordWrap.getRowCount();rowCounter++){
				Object[] currentObjects=recordWrap.getObjects(rowCounter);
				// ��������� � PreparedStatement ��������� ������ ������
				this.columns.setDataToStatement(currentObjects);
				// ��������� �� ���� ���������� ������
				this.columns.executeUpdate();
			}
			this.columns.freeConnection();
			return true;
		}else{
			return false;
		}
	}

}
