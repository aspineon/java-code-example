package gui.View.TimePanel;
import java.util.Date;

/** ���������, ������� �������� ������ �� ��������� � ��������� ������� */
public interface ITimeManager {
	/** set Time (as Date)*/
	public void setTime(Date date);
	/** get Time (as Date)*/
	public Date getTime();
}
