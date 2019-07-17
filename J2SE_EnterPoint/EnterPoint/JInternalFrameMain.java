package gui.EnterPoint;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * �����, ������� �������� ������� ���������� ������� ��� ����������
 * @author cherkashinv
 */
public class JInternalFrameMain extends JInternalFrame implements InternalFrameListener{
	/** ������� ����, �� ������� ���������� ����������� ���� �������*/
	private JDesktopPane field_desktop;
	
	/**
	 * ������� ���������� �����
	 * @param desktop - ������� ���� ����������� ������
	 * @param caption - ��������� ��� ����������� ������
	 * @param width - ������ ����������� ������
	 * @param height - ������ ����������� ������
	 */
	public JInternalFrameMain(JDesktopPane desktop,
							  String caption,
							  int width,
							  int height){
		super(caption,true,true,true,true);
		this.field_desktop=desktop;
		this.addInternalFrameListener(this);
		this.setSize(width,height);
		this.setVisible(true);
		this.field_desktop.add(this);
		Position.set_frame_to_center(this, field_desktop, 20, 20);
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}
}
