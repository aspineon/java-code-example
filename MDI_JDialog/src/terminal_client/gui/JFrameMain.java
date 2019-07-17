package terminal_client.gui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import terminal_client.gui.windows.*;

public class JFrameMain extends JFrame{
	/** ������� ����, �� ������� ����� �������������� ����������� ���� ���������� ��������� */
	
	public JFrameMain(){
		super("���������-������ ��� ������� �������");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		initComponents();
		this.setVisible(true);
	}
	
	/** init component's */
	private void initComponents(){
		// create element
		JButton field_button_load_boncard=new JButton("�������� ��������� �������");
		
		// add listener's
		field_button_load_boncard.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_load_boncard_click();
			}
		});
		
		// placing element's
		JPanel panel_main=new JPanel();
		panel_main.setLayout(new BorderLayout());
		panel_main.add(field_button_load_boncard,BorderLayout.NORTH);
		this.getContentPane().add(panel_main);
	}
	
	
	/** reaction on Striking button */
	private void on_button_load_boncard_click(){
		new JDialogMain(this);
	}
}















