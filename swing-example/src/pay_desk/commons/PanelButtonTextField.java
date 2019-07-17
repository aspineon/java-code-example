package pay_desk.commons;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** ������, ������� �������� ����������� ����� ����������� ��� ����������� ������ � ������ � ��������������� */
public class PanelButtonTextField extends JPanel{
	private final static long serialVersionUID=1L;
	private JTextField textField;
	private JButton button;
	
	/** ������, ������� �������� ����������� ����� ����������� ��� ����������� ������ � ������ � ��������������� */
	public PanelButtonTextField(String title){
		initComponents(title);
	}
	
	/** ������������� ����������� */
	private void initComponents(String title){
		button=new JButton("...");
		this.setLayout(new BorderLayout());
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		textField=new JTextField();
		this.add(textField);
		this.add(button, BorderLayout.EAST);
	}
	
	public void setText(String value){
		this.textField.setText(value);
	}

	public void addActionLitener(ActionListener actionListener){
		this.button.addActionListener(actionListener);
	}
}
