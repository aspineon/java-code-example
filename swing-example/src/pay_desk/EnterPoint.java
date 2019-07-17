package pay_desk;

import javax.swing.JMenuBar;

import javax.swing.JMenuItem;

import pay_desk.questionary.Questionary;

import database.Connector;

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
		super("�������� �������� ����� �����");
		Position.set_frame_by_dimension(this, 800, 600);
		this.setVisible(true);
		try{
			FrameMain.setObject("CONNECTOR", new Connector());
		}catch(Exception ex){
			System.err.println("Exception Ex:"+ex.getMessage());
		}
	}
	
	/** �������� ���� ������������ ��� �������� ������� */
	protected  JMenuBar getUserMenuBar(){
		JMenuBar menuBar=new JMenuBar();
		JMenuItem itemQuestionary=new JMenuItem("������������ �������");
		itemQuestionary.addActionListener(this.getActionListener(this, "onQuestionary"));
		menuBar.add(itemQuestionary);
		return menuBar;
	}
	
	/** ������� �� ����� ����������� ���� ����� */
	public void onQuestionary(){
		new Questionary(this, 400,400);
	}
	
}
