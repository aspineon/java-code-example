package gui.Utility;
import javax.swing.JInternalFrame;

/** ���������, ������� �������� �� ���������� ���� ��������� � ��������*/
public interface ModalParent {
	/** �������� ���������� ������������� ���� */
	public void modalClosing(JInternalFrame frame, int result);
	/** �������� ������ ���� */
	public void hideModal();
	/** �������� ������ ���� */
	public void showModal();
}
