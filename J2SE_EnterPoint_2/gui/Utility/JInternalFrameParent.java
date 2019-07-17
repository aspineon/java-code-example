package gui.Utility;

import gui.CommonObject;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public abstract class JInternalFrameParent extends JInternalFrame 
										   implements InternalFrameListener, 
										   			  ModalParent{
	private static final long serialVersionUID=1L;
	/** ������� ����, �� ������� ������������� ��� ������*/
	private JDesktopPane field_desktop;
	/** ������, ������� �������� ���������� ��� �������� ���� */
	private ModalParent field_modal_parent;
	/** ����� ������, ������� �������� ����������� ���������� ��� ����������� ������ */
	private CommonObject field_common_object;
	/**
	 * ������������ ���� ��� ���� ��������� ����
	 * @param parent - ������������ �����, �������� ����� �������� ����������
	 * @param common_object - ������, ������� �������� ����������� ��� ����������� ������ ����������  
	 * @param title - ��������� ��� ������� ������
	 * @param width - ������ ������
	 * @param height - ������ ������
	 * ���� width=0 && height==0 ����� - ������������ ������ 
	 */
	public JInternalFrameParent(JInternalFrameParent parent,
								CommonObject common_object,
								String title,
								int width,
								int height){
		super(title,
			  true, 
			  true, 
			  true, 
			  false);
		this.setSize(width, height);
		this.field_modal_parent=(ModalParent)parent;
		this.field_desktop=parent.getDesktop();
		this.field_common_object=common_object;

		parent.setVisible(false);
		this.addInternalFrameListener(this);
		this.field_desktop.add(this);
		if((width==0)&&(height==0)){
			try{
				this.setMaximum(true);
			}catch(Exception ex){};
		}else{
			set_frame_to_center(this, this.getDesktop(), 0, 0);
		}
		this.setVisible(true);
	}

	/**
	 * ������������ ���� ��� ���� ��������� ����
	 * @param desktop - ������� ���� 
	 * @param title - ��������� ��� ������� ������
	 * @param width - ������ ������ 
	 * @param height - ������ ������
	 * ���� width=0 && height==0 ����� - ������������ ������ 
	 */
	public JInternalFrameParent(JDesktopPane desktop,
							    CommonObject common_object,
							    String title,
							    int width,
							    int height){
		super(title,
			  true, 
			  true, 
			  true, 
			  false);
		this.setSize(width, height);
		this.field_modal_parent=null;
		this.field_desktop=desktop;
		this.field_common_object=common_object;
		
		this.addInternalFrameListener(this);
		this.field_desktop.add(this);
		if((width==0)&&(height==0)){
			try{
				this.setMaximum(true);
			}catch(Exception ex){};
		}else{
			set_frame_to_center(this, this.getDesktop(), 0, 0);
		}
		this.setVisible(true);
	}
	
    /**
     * ��������� JInternalFrame � ������ �������� ����� �� ��������� ����� � ������
     */
    public static void set_frame_to_center(JInternalFrame internalframe,
                                           JDesktopPane desktop,
                                           int offset_left,
                                           int offset_top){
        Dimension screenSize = desktop.getSize();
        internalframe.setBounds((int)(screenSize.getWidth()/2-offset_left/2-internalframe.getWidth()/2),
                                (int)(screenSize.getHeight()/2-offset_top/2-internalframe.getHeight()/2),
                                (int)internalframe.getWidth(),
                                (int)internalframe.getHeight());
    }
	
	public CommonObject getCommonObject(){
		return this.field_common_object;
	}
	
	public void setCommonObject(CommonObject common_object){
		this.field_common_object=common_object;
	}
	
	/** �������� ������ �� ������� ������� ���� */
	protected JDesktopPane getDesktop(){
		return this.field_desktop;
	}
	
	/** �������� ������ �� ������, �������� ����� �������� ���������� � ������ �������� ������� JInternalFrame*/
	protected ModalParent getModalParent(){
		return this.field_modal_parent;
	}
	/** ���������� ����������� ��� ���������� ����������, � �������� � ����� �� �� ContentPane */
	protected abstract void initComponents();
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		
	}
	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		
	}
	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		if(this.getModalParent()==null){
			System.exit(0);
		}else{
			this.getModalParent().modalClosing(this, 0);
		}
	}
	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		
	}
	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
	}

	
	@Override
	public void modalClosing(JInternalFrame frame, int result) {
		frame.setVisible(false);
		this.setVisible(true);
	}

	@Override
	public void hideModal() {
		this.setVisible(false);
	}

	@Override
	public void showModal() {
		this.setVisible(true);
	}
	

}
