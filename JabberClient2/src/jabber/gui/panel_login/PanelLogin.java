package jabber.gui.panel_login;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/** ������, ������� ���������� ��������� ����� ������ � ������ */
public class PanelLogin extends JPanel{
	private final static long serialVersionUID=1L;
	
	/** ������, ������� ���������� ��������� ����� ������ � ������ 
	 * @param title (nullable) ��������� ��� ������ ������
	 * @param connectButtonListener - ��������� ������� �� ������ Connect
	 * @param disconnectButtonListener - ��������� ������� �� ������ Disconnect
	 */
	public PanelLogin(String title, ActionListener connectButtonListener, ActionListener disconnectButtonListener){
		if(title!=null){
			this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		}
		this.initComponents(connectButtonListener, disconnectButtonListener);
	}
	
	private JTextField fieldLogin;
	private JPasswordField fieldPassword;
	private JButton buttonConnect;
	private JButton buttonDisconnect;
	private JPanel panelButton=new JPanel(new GridLayout(1,1));

	private void initComponents(ActionListener connectButtonListener, ActionListener disconnectButtonListener){
		JPanel panelLogin=new JPanel(new GridLayout(1,2));
		JLabel labelLogin=new JLabel("Login");
		labelLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		panelLogin.add(labelLogin);
		fieldLogin=new JTextField();
		panelLogin.add(fieldLogin);
		
		JPanel panelPassword=new JPanel(new GridLayout(1,2));
		JLabel labelPassword=new JLabel("Password");
		labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		panelPassword.add(labelPassword);
		fieldPassword=new JPasswordField();
		panelPassword.add(fieldPassword);
		
		buttonConnect=new JButton("Connect");
		buttonConnect.addActionListener(connectButtonListener);
		buttonDisconnect=new JButton("Disconnect");
		buttonDisconnect.addActionListener(disconnectButtonListener);
		
		
		this.setLayout(new GridLayout(3,1));
		this.add(panelLogin);
		this.add(panelPassword);
		panelButton.add(buttonConnect);
		this.add(this.panelButton);
	}
	
	public void showButtonConnect(){
		this.fieldLogin.setEnabled(true);
		this.fieldPassword.setEnabled(true);
		this.panelButton.removeAll();
		this.panelButton.add(buttonConnect);
		this.panelButton.revalidate();
		this.panelButton.repaint();
	}
	
	public void showButtonDisconnect(){
		this.fieldLogin.setEnabled(false);
		this.fieldPassword.setEnabled(false);
		this.panelButton.removeAll();
		this.panelButton.add(buttonDisconnect);
		this.panelButton.revalidate();
		this.panelButton.repaint();
	}
	
	/** �������� ����� */	
	public String getLogin(){
		return this.fieldLogin.getText();
	}
	
	/** �������� ������  */
	public String getPassword(){
		return new String(this.fieldPassword.getPassword());
	}

	/** ���������� ����� 
	 * @param login - ����� ��� ��������� � ���������� ��������� 
	 * */
	public void setLogin(String login) {
		this.fieldLogin.setText(login);
	}
	/** ���������� ������ 
	 * @param password - ������ ��� ��������� � ���������� ���������
	 * */
	public void setPassword(String password){
		this.fieldPassword.setText(password);
	}
}
