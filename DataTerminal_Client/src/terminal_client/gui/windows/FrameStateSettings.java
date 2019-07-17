package terminal_client.gui.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import terminal.function.RequestBoncardCatalog;
import terminal.function.ResponseBoncardCatalog;
import terminal.transfer.GetString;
import terminal.transfer.PercentReport;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;

public class FrameStateSettings extends JInternalFrameParent implements GetString{
	/** ������� ��� ��������� �������� ��� ���� �������*/
	private JTextField field_path;
	
	/** ���������� ��� �����������/����������� Function - �������� ������� */
	private String field_function_directory_get;
	/** ���������� ��� �����������/����������� Function - ���������� ������� */
	private String field_function_directory_set=this.addUniqueString("ResponseBoncardCatalog");
	

	public FrameStateSettings(JInternalFrameParent parent, JDesktopPane desktop, 
							  ModalClose parent_element,PercentReport percent_report) {
		super(parent.getAccess(),parent, desktop, parent_element, percent_report,"��������� ��������", 400, 140);
	}

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void initComponents() {
		// create component's
		field_path=new JTextField();
		// �������� �� ������ ������ �� ��������� ��������
		if(this.field_function_directory_get==null){
			this.field_function_directory_get=this.addUniqueString(this.addUniqueString("RequestBoncardCatalog"));
		}
		this.field_function_directory_get=this.replaceUniqueString(this.field_function_directory_get);
		(new RequestBoncardCatalog(this,
								   this.getPercentReport(),
								   this.field_function_directory_get,
								   "�������� ������� �������� �������")
								   ).start(false);		
		field_path.setEditable(false);
		/*
		try{
			Thread.sleep(2000);
		}catch(Exception ex){};
		*/
		
		JPanel panel_text_path=this.getTextPanelWithComponent(field_path, "���� � �������� � ������� ��� �������� �� �������");
		JButton field_button_set_directory=new JButton("���������� �������");
		JButton field_button_show_dialog=new JButton("...");
		JPanel panel_show_dialog=this.getTextPanelWithComponent(field_button_show_dialog, "");
		// add listener's
		field_button_set_directory.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				on_button_set_directory_click();
				debug("set_directory");
			}
		});
		field_button_show_dialog.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				on_button_show_dialog();
			}
		});
		// placing
		JPanel panel_main=new JPanel(new GridLayout(2,1));
		JPanel panel_path=new JPanel(new BorderLayout());
		panel_path.add(panel_text_path, BorderLayout.CENTER);
		panel_path.add(panel_show_dialog,BorderLayout.EAST);
		
		panel_main.add(panel_path);
		panel_main.add(field_button_set_directory);
		this.getContentPane().add(panel_main);
	}

	/** reaction on striking button set directory */
	private void on_button_set_directory_click(){
		if(!this.getPercentReport().isInAction(this.field_function_directory_set,this.getRandomTailLength())){
			this.field_function_directory_set=this.replaceUniqueString(this.field_function_directory_set);
			(new ResponseBoncardCatalog(this.field_path.getText(),
				                       this.getPercentReport(),
					                   this.field_function_directory_set,
					                   "��������� �������� �������� �������")).start(false);
		}else{
			this.showWarningCollision(this);
		}
	}
	/** reaction on striking button show dialog for set catalog */
	private void on_button_show_dialog(){
		JFileChooser dialog=new JFileChooser();
		dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(dialog.showDialog(this, "������� ��� ��������")==JFileChooser.APPROVE_OPTION){
			this.field_path.setText(dialog.getSelectedFile().getPath());
		}
	}
	
	/** �������� ����� �� ������ ���������� */
	@Override
	public void getString(String taskName, String value) {
		debug("getString:begin");
		debug("taskName:"+taskName);
		if((taskName!=null)&&(taskName.startsWith("RequestBoncardCatalog"))){
			debug("getString:value="+value);
			this.field_path.setText(value);
		}
		debug("getString:end");
	}

	@Override
	public void getString(String taskName) {
		JOptionPane.showMessageDialog(this, "������ ��� ��������� ������");
	}
}
