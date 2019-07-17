package gui;
import gui.dovidka.Dovidka;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.apache.log4j.BasicConfigurator;

import xml_ini.Loader;


public class Main extends JFrame {
	private final static long serialVersionUID=1L;
	/** ������, ������� �������� �� ����������� ������*/
	private Loader loader;
	
	public static void main(String[] args){
		BasicConfigurator.configure();
		new Main();
	}
	
	public Main(){
		super("");
		this.initComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Position.setFrameBySize(this, 400, 100);
		//this.setSize(400,100);
		this.setVisible(true);
		try{
			this.loader=new Loader("settings.xml ");
		}catch(Exception ex){
			System.out.println("Loader Exception: "+ex.getMessage());
		}
	}

	private void initComponents() {
		// create element's
		JButton buttonDovidka=new JButton("���������� ���i��� �� ���.���i");
		JButton buttonEditor=new JButton("������� ���������� ����������� ������");
		// add Listener
		buttonDovidka.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonDovidka();
			}
		});
		buttonEditor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonEditor();
			}
		});
		// placing components
		JPanel panelMain=new JPanel(new GridLayout(2,1));
		panelMain.add(buttonDovidka);
		panelMain.add(buttonEditor);
		this.getContentPane().add(panelMain);
	}
	
	/** reaction on striking button Dovidka */
	private void onButtonDovidka(){
		JDialog modal=new JDialog(this,"���������� ���i���",Dialog.ModalityType.APPLICATION_MODAL);
		modal.add(new Dovidka(this,this.loader));
		Position.setDialogToCenterBySize(modal, 750, 550);
		//modal.setSize(750,550);
		modal.setVisible(true);
	}
	/** reaction on striking button Editor */
	private void onButtonEditor(){
		JOptionPane.showMessageDialog(this, "��������, ���� ����� �������� ������������");
		/*JDialog modal=new JDialog(this,"��������",Dialog.ModalityType.APPLICATION_MODAL);
		modal.add(new Editor(modal, this.loader));
		Position.setDialogToCenterBySize(modal, 750, 550);
		//modal.setSize(750,550);
		modal.setVisible(true);
		*/
	}
}
