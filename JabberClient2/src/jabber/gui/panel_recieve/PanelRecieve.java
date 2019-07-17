package jabber.gui.panel_recieve;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/** ������ �� ������������ �������� ��������� */
public class PanelRecieve extends JPanel{
	private final static long serialVersionUID=1L;
	
	/** ������ �� ������������ �������� ��������� */
	public PanelRecieve(String title){
		if(title!=null){
			this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		}
		initComponents();
	}
	
	/** ��������� ����, ������� �������� ����� �������� ��������� */
	private JTextArea textArea;
	
	private void initComponents(){
		this.setLayout(new GridLayout(1,1));
		textArea=new JTextArea();
		this.add(textArea);
	}
	
	/** �������� ������ */
	public void clearHistory(){
		this.textArea.setText("");
	}
	
	/** �������� � ������ ������ ������ ������� */
	public void addHistory(String recipient, String text){
		if(text!=null){
			if(recipient!=null){
				this.textArea.insert(recipient+" : "+text+"\n", 0);
			}else{
				this.textArea.insert(text+"\n", 0);
			}
		}
	}
	
}
