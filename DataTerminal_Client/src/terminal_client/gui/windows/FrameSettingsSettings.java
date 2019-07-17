package terminal_client.gui.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import terminal.transfer.GetString;
import terminal.transfer.PercentReport;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;
import terminal.function.*;

public class FrameSettingsSettings extends JInternalFrameParent 
                                   implements GetString{
	/** ������� ��� �������� ������ �� ������� */
	private JTextField field_path;
	/** unique name for Task get from server "catalog for settings" */
	private String field_function_catalog_get;
	/** unique name for Task set to server "catalog for settings" */
	
	private String field_function_catalog_set=this.addUniqueString("ResponseSettingsCatalog");
	
	public FrameSettingsSettings(JInternalFrameParent parent, 
								 JDesktopPane desktop, 
								 ModalClose parent_element,
								 PercentReport percent_report) {
		super(parent.getAccess(), 
			  parent,
			  desktop, 
			  parent_element, 
			  percent_report,
			  "��������� ��������", 
			  400, 
			  140);
	}

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void initComponents() {
		// create component's
		field_path=new JTextField();
		field_path.setEditable(false);
		debug("������ ��������� ����� �� �������");
		if(this.field_function_catalog_get==null){
			this.field_function_catalog_get=this.addUniqueString("RequestSettingsCatalog");
		}
		this.field_function_catalog_get=this.replaceUniqueString(this.field_function_catalog_get);
		(new RequestSettingsCatalog(this,
									this.getPercentReport(),
									this.field_function_catalog_get,
									"��������� �������� \"���������\" ")
		).start(false);
		JPanel panel_text_path=this.getTextPanelWithComponent(field_path, "���� � �������� � ������� ��� �������� �� �������");
		JButton field_button_set_directory=new JButton("���������� �������");
		JButton field_button_show_dialog=new JButton("...");
		JPanel panel_show_dialog=this.getTextPanelWithComponent(field_button_show_dialog, "");
		// add listener's
		field_button_set_directory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_set_directory_click();
			}
		});
		field_button_show_dialog.addActionListener(new ActionListener(){
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

	private void on_button_set_directory_click(){
		if(!this.getPercentReport().isInAction(this.field_function_catalog_set, this.getRandomTailLength())){
			this.field_function_catalog_set=this.replaceUniqueString(this.field_function_catalog_set);
			(new ResponseSettingsCatalog(this.field_path.getText(),
										 this.getPercentReport(),
										 this.field_function_catalog_set,
										 "�������� �� ������ �������� � \"�����������\" ")
			).start(false);    
		}else{
			this.showWarningCollision(this);
		}
	}
	
	private void on_button_show_dialog(){
		JFileChooser dialog=new JFileChooser();
		dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(dialog.showDialog(this, "������� ��� ��������")==JFileChooser.APPROVE_OPTION){
			this.field_path.setText(dialog.getSelectedFile().getPath());
		}

	}
	
	/** Thread get positive result */
	@Override
	public void getString(String taskName, String value) {
		if(taskName!=null){
			if(this.equalsStringHeader(taskName, field_function_catalog_get)){
				this.field_path.setText(value);
			};
			if(this.equalsStringHeader(taskName, field_function_catalog_set)){
				debug("set_directory value:"+value);
			}
		}else{
			error("Thread return positive result, but \"taskName\" is null");
		}
	}

	/** Thread get negative result*/
	@Override
	public void getString(String taskName) {
		error("Thread return negative result: "+taskName);
	}
}
