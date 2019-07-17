package ayur_veda;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ayur_veda.questionary.AddQuestionary;

import swing_framework.FrameMain;
import swing_framework.Position;

/** �������� ���� ��������� */
public class EnterPoint extends FrameMain{
	private final static long serialVersionUID=1L;
	
	/** ����� ����� � ��������� */
	public static void main(String[] args){
		System.out.println("Begin");
		new EnterPoint();
		System.out.println("End");
	}
	
	/** ������� ���� ���������, ������� ���������� ������� ����� ������� */
	public EnterPoint(){
		super("������� ����� �������");
		Position.set_frame_by_dimension(this, 600, 400);
		this.setVisible(true);
	}
	
	/** �������� ���� ������������ ��� �������� ������� */
	protected  JMenuBar getUserMenuBar(){
		JMenuBar menuBar=new JMenuBar();
		JMenu menuQuestionary=new JMenu("������");
		menuBar.add(menuQuestionary);
		
		JMenuItem showQuestionary=new JMenuItem("����������� ������");
		showQuestionary.addActionListener(this.getActionListener(this, "onButtonShowQuestionary"));
		menuQuestionary.add(showQuestionary);
		
		JMenuItem addQuestionary=new JMenuItem("�������� ������");
		addQuestionary.addActionListener(this.getActionListener(this, "onButtonAddQuestionary"));
		menuQuestionary.add(addQuestionary);
		
		return menuBar;
	}
	
	/** ������� �� ����� ����������� ���� ����� */
	public void onButtonShowQuestionary(){
		System.out.println("onButtonShowQuestionary ");

	}
	/** ������� �� ����� ����������� ������ ���������� ����� */
	public void onButtonAddQuestionary(){
		new AddQuestionary(this, 300,200);
	}
}
