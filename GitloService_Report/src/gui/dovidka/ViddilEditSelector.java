package gui.dovidka;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/** ������, ������� ���������� ����������� ������ ����� ��������� ������� */
public class ViddilEditSelector extends JPanel{
	private final static long serialVersionUID=1L;
	/** JComboBox ������� �������� ��������� ��� ����������� ������ */
	private JComboBox viddil=new JComboBox(new Object[]{"��� �����.","1","2","3","4","5","6","7","8","9"});
	/** ������, ������� ������� ��������� � ������ �������� ���������� */
	private JDialog parent;
	/** ��������� ��� ����������� ������ */
	private ViddilReturnValue modalResult;
	/** ����� �� ��������� ������� 
	 * @param text - �����, ������� ������� ���������� � �������� ������
	 * @param value - �������� ��������� (null - ��� ���������)
	
	 * */
	public ViddilEditSelector(JDialog parent, ViddilReturnValue modalResult,String text, Integer value){
		this.parent=parent;
		this.modalResult=modalResult;
		initComponents(text,value);
	}
	
	private void initComponents(String text, Integer value){
		this.setLayout(new GridLayout(3,1));
		JLabel labelAddress=new JLabel(text);
		JPanel panelAddress=new JPanel(new GridLayout(1,1));
		panelAddress.setBorder(javax.swing.BorderFactory.createTitledBorder("�����"));
		panelAddress.add(labelAddress);
		
		JPanel panelViddil=new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelViddil.setBorder(javax.swing.BorderFactory.createTitledBorder("���������"));
		panelViddil.add(viddil);
		if(value==null){
			viddil.setSelectedIndex(0);
		}else{
			viddil.setSelectedIndex(value);
		}
		JButton buttonOk=new JButton("���������");
		buttonOk.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonOk();
			}
		});
		JButton buttonCancel=new JButton("��������");
		buttonCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonCancel();
			}
		});
		JPanel panelManager=new JPanel();
		panelManager.setLayout(new GridLayout(1,2));
		panelManager.add(buttonOk);
		panelManager.add(buttonCancel);
		
		this.add(panelAddress);
		this.add(panelViddil);
		this.add(panelManager);
	}
	
	private void onButtonOk(){
		this.modalResult.setSelected(true);
		int selectedIndex=this.viddil.getSelectedIndex();
		if(selectedIndex==0){
			this.modalResult.setValue(null);
		}else{
			this.modalResult.setValue(selectedIndex);
		}
		this.parent.dispose();
	}
	private void onButtonCancel(){
		this.modalResult.setSelected(false);
		this.parent.dispose();
	}
}
