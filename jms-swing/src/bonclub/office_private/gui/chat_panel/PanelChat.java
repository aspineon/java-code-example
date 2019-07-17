package bonclub.office_private.gui.chat_panel;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import bonclub.office_private.gui.text_exchange.ITextInput;
import bonclub.office_private.gui.text_exchange.ITextOutput;

import static javax.swing.BorderFactory.createTitledBorder;

/** ������, ������� �������� ����������� ������� � ��������� ������������� */
public class PanelChat extends JPanel implements ITextInput{
	private final static long serialVersionUID=1L;
	private ITextOutput textOutput;
	
	/** ������, ������� �������� ����������� ������� � ��������� �������������
	 * @param userTitle - ��� ������������  
	 * @param output - ������, ������� ������ ��� �������� ��������� 
	 * */
	public PanelChat(String userTitle, ITextOutput output){
		initComponents(userTitle);
		this.textOutput=output;
	}
	
	/** ������� ������� ����������� */
	private JTextArea history;
	private JTextField textForSend;
	private JButton buttonForSend;
	
	/** �������������� ������������� ����������� */
	private void initComponents(String userTitle){
		this.setLayout(new BorderLayout());
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(userTitle));
		
		history=new JTextArea();
		history.setBorder(javax.swing.BorderFactory.createTitledBorder("������� �������"));
		this.add(history, BorderLayout.CENTER);
		
		JPanel panelManager=new JPanel(new BorderLayout());
		this.add(panelManager, BorderLayout.SOUTH);
		
		buttonForSend=new JButton("��������� �����");
		panelManager.add(buttonForSend, BorderLayout.EAST);
		buttonForSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonSend();
			}
		});
		buttonForSend.setEnabled(false);

		textForSend=new JTextField();
		textForSend.setBorder(createTitledBorder("����� ��� ������������"));
		textForSend.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent arg0) {
				buttonForSend.setEnabled((textForSend.getText()!=null)&&(!textForSend.getText().trim().equals("")));
			}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		panelManager.add(textForSend,BorderLayout.CENTER);
		
	}

	/** ������ �� ������ "������� ���������" */
	private void onButtonSend(){
		String text=this.textForSend.getText();
		if((text!=null)&&(!text.trim().equals(""))){
			if(this.textOutput.sendText(text)==true){
				this.history.append(text);this.history.append("\n");
				this.textForSend.setText("");
				this.buttonForSend.setEnabled(false);
			}else{
				// ��������� �� ���������� 
			}
		}else{
			// text is empty - will not be sended
		}
	}
	
	@Override
	public void textInput(final String recipient, final String message) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				onTextReceive(recipient, message);
			}
		});
	}
	
	private void onTextReceive(String sender, String message){
		// INFO ����� ��������� ��������� �� ���������� �������
		this.history.append("\""+sender+"\": ");
		this.history.append(message+"\n");
		System.out.println("PanelChat#onTextReceive: "+message);
	}
}
