package terminal_client.gui.utility;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import java.util.Random;
import java.util.zip.*;
import java.io.*;

import terminal.transfer.PercentReport;

/** ����� ������� �������� ��������� ��� ���� JInternalFrame ����*/
public abstract class JInternalFrameParent extends JInternalFrame 
										   implements ModalClose,
										   			  InternalFrameListener,
										   			  KeyListener{

	private static final long serialVersionUID = 1L;
	private JDesktopPane field_desktop=null;
	private ModalClose field_parent_element=null;
	private PercentReport field_percent_report=null;
	/** ������, ������� �������� ������ ���������� ����������� �������� ���� */
	private Access field_access=null;
	/** ������ ������������ ���� � ������� ������ �� ������� ����*/
	protected String field_gui_parent="";
	
	/** output debug information */
	protected void debug(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" DEBUG ");
		System.out.println(information);
	}
	/** output error information */
	protected void error(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	
	protected void showWarningCollision(JInternalFrame frame){
		JOptionPane.showMessageDialog(frame, "������ �������� � ��������� �������� ������ �� �������");
	}

	private final static String hexChars[] = { "0", "1", "2", "3", "4", "5","6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	
	/** �������� ��������� ������ �������� ������
	 * @param  count - ���-�� ��������� ��������
	 * */
	private String getUniqueChar(int count){
        StringBuffer return_value=new StringBuffer();
        Random random=new java.util.Random();
        int temp_value;
        for(int counter=0;counter<count;counter++){
            temp_value=random.nextInt(hexChars.length);
            return_value.append(hexChars[temp_value]);
        }
        return return_value.toString();
    }
	
	
	private int field_unique_length=4;
	/** �������� ������ ���������� ��������, ������� ����������� � ������������� �������� � ���������� ��� ������ */
	public int getRandomTailLength(){
		return this.field_unique_length;
	}
	
	/** �������� ������������ ������, �� ������ � ����������� � ��� ���������� ������������������� 
	 * @param value - ������ � ������� ��������� �������� ��������� ������������������� ���������
	 * */
	private String getOriginalString(String value){
		return value.substring(0, value.length()-this.field_unique_length);
	}
	
	/** �������� � ������ ��������� ������������������ */
	protected String addUniqueString(String value){
		return value+this.getUniqueChar(field_unique_length);
	}
	
	/** �������� � ������ ��������� ������������������ */
	protected String replaceUniqueString(String value){
		return addUniqueString(getOriginalString(value));
	}
	
	/** �������� ��������� ���� ����� (��� ���������� ������������������) �� ������������ 
	 * @param value_1 - ������ ������ ��� ���������
	 * @param value_2 - ������ ������ ��� ���������
	 * */
	protected boolean equalsStringHeader(String value_1, String value_2){
		return this.getOriginalString(value_1).equals(this.getOriginalString(value_2));
	}
	
    /** �������������� ��������� ���� � ����� ����
     * @param path_to_file ���� � �����
     * @return ���������� byte[] 
     */
    public byte[] getZipBytesFromFile(String path_to_file){
        byte[] return_value=null;
        try{
            File file_source=new File(path_to_file);
            if(file_source.exists()){
                BufferedInputStream origin = null;
                ByteArrayOutputStream dest=new ByteArrayOutputStream();
                ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
                //out.setMethod(ZipOutputStream.DEFLATED);
                byte data[] = new byte[2048];
                FileInputStream file = new FileInputStream(path_to_file);
                origin = new BufferedInputStream(file, 2048);
                // �������� � Zip ����� ��� �����, ��� ��� ������ ���� 
                ZipEntry entry = new ZipEntry(file_source.getName());
                out.putNextEntry(entry);
                int count;
                while((count = origin.read(data, 0, 2048)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
                out.close();
                dest.close();
                return_value=dest.toByteArray();
            }else{
                debug("fileToArchiv: file not found");
            }
        }catch(Exception ex){
            error("getZipBytesFromFile:"+ex.getMessage());
        }
        return return_value;
    }
    
    /** ��������������� ����� ���� � ���� Zip ������ � ���� �� ����� 
     * @param data ������ ���� ������� Zip
     * @param path_to_file ��� ����� ��� ���������� �� ������
     * @return true ���� ���� ������� ��������
     */
    public boolean getFileFromZipBytes(byte[] data,String path_to_file){
        boolean return_value=false;
        try{
            // �������� ����� 
            ByteArrayInputStream fis=new ByteArrayInputStream(data);
            // �������� zip �����
            ZipInputStream zis=new ZipInputStream(new BufferedInputStream(fis));
            // ��������� ��������� ��������
            ZipEntry entry;
            BufferedOutputStream dest;
            while((entry=zis.getNextEntry())!=null){
                if(entry.isDirectory()){
                    debug("this is directory:"+entry.getName());
                }else{
                    debug("this is file:"+entry.getName());
                    int count;
                    byte buffer[] = new byte[2048];
                    // write the files to the disk
                    FileOutputStream fos = new FileOutputStream(path_to_file);
                    dest = new BufferedOutputStream(fos, 2048);
                    while ((count = zis.read(buffer, 0, 2048)) != -1) {
                       dest.write(buffer, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }
            }
            zis.close();
            return_value=true;
        }catch(Exception ex){
            error("getFileFromZipBytes Exception:"+ex.getMessage());
        }
        return return_value;
    }
	
    /** ���������� ��� �������� ������ ��� ������� ���� ������*/
    protected String getOnlyClassName(){
    	String return_value=this.getClass().getName();
    	int dot_index=return_value.lastIndexOf(".");
    	if(dot_index>=0){
    		return_value=return_value.substring(dot_index+1);
    	}else{
    		return return_value;
    	}
    	return return_value;
    }
    
    /** ���������� ��� �������� ������ ��� ������� ���� ������*/
    protected String getOnlyClassName(Object object){
    	String return_value=object.getClass().getName();
    	int dot_index=return_value.lastIndexOf(".");
    	if(dot_index>=0){
    		return_value=return_value.substring(dot_index+1);
    	}else{
    		return return_value;
    	}
    	return return_value;
    }
    
    /** ���������� ������ ���� � ������� ������ */
    public String getGuiPath(){
    	if(this.field_gui_parent.length()>0){
    		return this.field_gui_parent+"."+this.getOnlyClassName();
    	}else{
    		return this.getOnlyClassName();
    	}
    }
    
    /** ���������� ������, ������� �������� �� ���������� ����������� �������� ���� */
    public Access getAccess(){
    	return this.field_access;
    }
    public void setAccess(Access access){
    	this.field_access=access;
    }
    
	/**
	 * ��������� � ������ Source ��������� � ������� GridLayout, 
	 * ���������� Access �� �������������� ����������� �����������  
	 * @param source ������, �� ������� ���������� ����������� ��������
	 * @param panel_elements ������, ������� ������ ���� ����������� �� ������ ��������
	 * @param name_of_elements ����� �������, ������� ������ ���� �����������
	 * @param url_gui ������ ��� GUI ������� ������
	 * @param access ������, ������� �������� �� ���������� ������������ �����������
	 */
	protected void placingComponents(JPanel source, 
			                         JPanel[] panel_elements, 
			                         String[] name_of_elements,
			                         String url_gui,
			                         Access access){
		int count_visible_element=0;
		boolean[] visible_element=new boolean[panel_elements.length];
		// ������� ������������ ���������
		String current_path;
		for(int counter=0;counter<name_of_elements.length;counter++){
			if((url_gui!=null)&&(!url_gui.trim().equals(""))){
				current_path=url_gui+"."+name_of_elements[counter];
			}else{
				current_path=name_of_elements[counter];
			}
			if(access.isEnabledPath(current_path)){
				count_visible_element++;
				visible_element[counter]=true;
			}
		}
		// ������� Layout
		if(count_visible_element!=0){
			source.setLayout(new GridLayout(count_visible_element,1));
		}
		for(int counter=0;counter<visible_element.length;counter++){
			if(visible_element[counter]==true){
				source.add(panel_elements[counter]);
			}
		}
		
		// ���������� ������ �� Layout
	}
    
    
	/** 
	 * �������� ��� ���� JInternalFrame ���� � ���������
	 * @param parent_element - ������������ �����
	 * @param desktop ������� ����, �� ������� ���������� �����������
	 * @param parent_element - �����, �������� ����� ���������� ����������, � ������ ��������
	 * @pram percent_report - ������, ������� ��������� ��������� �� ����������� ���������� � ������� �� ������� � �������� 
	 * @param title
	 * @param width
	 * @param height
	 */
	public JInternalFrameParent(Access access,
							    JInternalFrameParent parent_frame,
								JDesktopPane desktop,
								ModalClose parent_element,
								PercentReport percent_report,
								String title,
								int width,
								int height){
		this(access, parent_frame, desktop, parent_element, percent_report, title, width, height, false, true, false, false);
	}

	/** 
	 * �������� ��� ���� JInternalFrame ���� � ���������
	 * @param parent
	 * @param desktop
	 * @param parent_element
	 * @pram percent_report - ������, ������� ��������� ��������� �� ����������� ���������� � ������� �� ������� � �������� 
	 * @param title
	 * @param width
	 * @param height
	 * @param is_resizable
	 * @param is_closable
	 * @param is_maximizable
	 * @param is_iconifiable
	 */
	public JInternalFrameParent(Access access, 
								JInternalFrameParent parent_frame, 
								JDesktopPane desktop,
								ModalClose parent_element,
								PercentReport percent_report,								
								String title,
								int width,
								int height,
								boolean is_resizable,
								boolean is_closable,
								boolean is_maximizable,
								boolean is_iconifiable){
		super(title,
			  is_resizable,
			  is_closable,
			  is_maximizable,
			  is_iconifiable);
		this.field_desktop=desktop;
		this.field_parent_element=parent_element;
		this.field_percent_report=percent_report;
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.addInternalFrameListener(this);
		this.field_desktop.add(this);
		this.setSize(width,height);
		this.setAccess(access);
		initComponents();
		position.set_frame_to_center(this, field_desktop);
		this.addKeyListener(this);
		this.setVisible(true);
		if(parent_frame!=null){
			this.field_gui_parent=parent_frame.getGuiPath();
		};
	}
	
	/** method for create, addListener's and Placing component's */
	abstract protected void initComponents();
	
	@Override
	public void modalClose(int result){
		this.setVisible(true);
	}

	/** get Parent Window */
	protected ModalClose getParentWindow(){
		return this.field_parent_element;
	}
	
	/** get current desktop*/
	protected JDesktopPane getDesktop(){
		return this.field_desktop;
	}
	
	/** wrapping component to Text Border*/
	protected JPanel getTextPanelWithComponent(Component component, String text){
		JPanel return_value=new JPanel(new GridLayout(1,1));
		return_value.add(component);
		return_value.setBorder(javax.swing.BorderFactory.createTitledBorder(text));
		return return_value;
	}

	/** wrapping component to Empty Border
	 * @param component wich wrapping
	 * @param top
	 * @param left 
	 * @param bottom
	 * @param right
	 * */
	protected JPanel getEmptyPanelWithComponent(Component component, int top, int left, int bottom, int right){
		JPanel return_value=new JPanel(new GridLayout(1,1));
		return_value.add(component);
		return_value.setBorder(javax.swing.BorderFactory.createEmptyBorder(top, left, bottom, right));
		return return_value;
	}
	
	public PercentReport getPercentReport(){
		return this.field_percent_report;
	}
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		this.field_parent_element.modalClose(0);
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
	
	public void keyTyped(KeyEvent e){
		
	}
	public void keyPressed(KeyEvent e){
		
	}
	public void keyReleased(KeyEvent e){
		// TODO �� �������� � KeyListener - ���������� ��������� ������� ������ �������� ����������
		System.out.println("ESCAPE");
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			internalFrameClosing(null);
		}
	}

}
