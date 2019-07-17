package jabber.gui.panel_send;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** ������, ���������� ����� ����������  � ����� ��� �������� ������� ����������*/
public class PanelSend extends JPanel{
	private final static long serialVersionUID=1L;
	
	/** ������, ���������� ����� ����������  
	 * @param title - (nullable) ��������� ��� ������
	 * @param actionButtonSend - ������� �� ������� ������ "������� ���������"  
	 * */
	public PanelSend(String title, ActionListener actionButtonSend){
		if(title!=null){
			this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		}
		initComponents(actionButtonSend);
	}
	
	private JTextField recipientField;
	private JTextField textField;
	
	private void initComponents(ActionListener actionButtonSend){
		
		recipientField=new JTextField();
		recipientField.setBorder(javax.swing.BorderFactory.createTitledBorder("recipient"));
		this.add(recipientField);
		
		textField=new JTextField();
		textField.setBorder(javax.swing.BorderFactory.createTitledBorder("text for send"));
		JButton buttonSend=new JButton("Send");
		buttonSend.addActionListener(actionButtonSend);
		JPanel panelSend=new JPanel(new BorderLayout());
		panelSend.add(textField,BorderLayout.CENTER);
		panelSend.add(buttonSend, BorderLayout.EAST);
		
		this.setLayout(new GridLayout(2,1));
		this.add(recipientField);
		this.add(panelSend);
	}
	
	/** �������� ���������� ����������  */
	public String getRecipient(){
		return this.recipientField.getText();
	}
	
	/** �������� ��������� ����� ��� �������� */
	public String getText(){
		return this.textField.getText();
	}

	/** ���������� ���������� � ���������� ��������� 
	 * @param value - ����������
	 * */
	public void setRecipient(String value) {
		this.recipientField.setText(value);
	}
	
}
