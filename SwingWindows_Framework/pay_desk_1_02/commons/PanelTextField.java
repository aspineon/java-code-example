package pay_desk.commons;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** ������, ������� �������� ����������� ����� ����������� ��� ����������� ������ � ������ � ��������������� */
public class PanelTextField extends JPanel{
	private final static long serialVersionUID=1L;
	private JTextField textField;
	
	/** ������, ������� �������� ����������� ����� ����������� ��� ����������� ������ � ������ � ��������������� */
	public PanelTextField(String title){
		initComponents(title);
	}
	
	/** ������������� ����������� */
	private void initComponents(String title){
		this.setLayout(new BorderLayout());
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		textField=new JTextField();
		this.add(textField);
	}
	
	public void setText(String value){
		this.textField.setText(value);
	}

	public String getText(){
		return this.textField.getText();
	}

}
