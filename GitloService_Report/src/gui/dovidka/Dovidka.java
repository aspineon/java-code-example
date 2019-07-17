package gui.dovidka;
import gui.Position;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.Connector;
import database.FirebirdConnection;
import dbfConverter.Converter;

import javax.swing.*;

import org.apache.log4j.BasicConfigurator;

import xml_ini.Loader;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.toedter.calendar.*;

public class Dovidka extends JPanel{
	private final static long serialVersionUID=1L;
	private JMonthChooser monthChooser;
	private JYearChooser yearChooser;
	/** ��� �1 */
	private JRadioButton buttonOne;
	/** ��� �2 */
	private JRadioButton buttonTwo;
	
	/** ���� - ��� */
	private JRadioButton buttonBorgYes;
	/** ���� - �i */
	private JRadioButton buttonBorgNo;

	/** �������� - ��� */
	private JRadioButton buttonProplataYes;
	/** �������� - �i */
	private JRadioButton buttonProplataNo;
	
	/** ����� �� ��������� - ��*/
	private JRadioButton buttonPlataYes;
	/** ����� �� ��������� - ���*/
	private JRadioButton buttonPlataNo;
	/** ����� �� ��������� - ���*/
	private JRadioButton buttonPlataAll;

	/** ����� �������� �� ����� */
	private JRadioButton buttonPochta;
	/** ����� ��� ����� */
	private JLabel labelPochta;
	/** ����� ������ ��������� */
	private JRadioButton buttonViddil;
	/** ������������ ���� ��� ��������� ����*/
	private JFrame parentFrame;
	
	public static void main(String[] args){
		BasicConfigurator.configure();
		try{
			JFrame frame=new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Position.setFrameBySize(frame, 750, 550);
			Dovidka dovidka=new Dovidka(frame,new Loader("settings.xml"));
			frame.getContentPane().add(dovidka);
			//this.setSize(400,100);
			frame.setVisible(true);
			
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
	}

	/** ������� ���������� � ����� ������ */
	private Connection connection;
	private Connector connector;
	private ResultTable table;
	private Loader loader;
	private JComboBox comboViddil;
	private JDateChooser dateReport;
	
	/** ����� ����� � ���������� */
	public Dovidka(JFrame parent, Loader loader){
		super();
		this.parentFrame=parent;
		//super("����� �������");
		//this.connector=new FirebirdConnection(new File("D:\\�����������\\�����������\\temp.gdb"));
		this.loader=loader;
		this.connector=new FirebirdConnection(loader.getValue("//SETTINGS/URL_DATABASE_TEMP"));
		//new HsqldbConnection("data");
		this.connection=connector.getConnection();
		try{
			this.connection.setAutoCommit(false);
		}catch(Exception ex){};
		
		if(this.connection==null){
			initComponentsError();
			/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(100,100);
			setVisible(true);*/
		}else{
			initComponents();
			/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(600,500);
			setVisible(true);*/
		}
	}
	
	private String getPathToHtml(){
		try{
			return loader.getValue("//SETTINGS/PATTERN_HTML");
		}catch(Exception ex){
			return "pattern.html";
		}
		//return "D:\\eclipse_workspace\\GitloService_Report\\src\\reporter\\pattern.html ";
	}

	private String getPathToHtmlAkt(){
		try{
			return loader.getValue("//SETTINGS/PATTERN_AKT_HTML");
		}catch(Exception ex){
			return "pattern.html";
		}
		//return "D:\\eclipse_workspace\\GitloService_Report\\src\\reporter\\pattern.html ";
	}
	
	private String getPathToDbf(){
		try{
			return loader.getValue("//SETTINGS/DBF_DIRECTORY");
		}catch(Exception ex){
			return "\\";
		}
		//return "D:\\eclipse_workspace\\GitloService_Report\\Information\\";
	}
	
	private String getPathToSourceDBF(){
		try{
			return loader.getValue("//SETTINGS/DBF_DIRECTORY_SOURCE");
		}catch(Exception ex){
			return "\\";
		}
		//return "D:\\eclipse_workspace\\GitloService_Report\\Information\\";
	}
	
	/** �������� ����, � ������� ����� ������� ������ (html)*/
	private String getOutFile(){
		return "c:\\out.html";
	}
	
	/** ���������� ������� �� ������ ����������� � ���� ������ */
	private void initComponentsError(){
		//JPanel panelMain=new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(new JLabel("Error in load JDBC "));
		//this.getContentPane().add(panelMain);
	}
	
	/** ������������������ ��� ���������� ���������� */
	private void initComponents(){
		this.setLayout(new GridLayout(1,1));
		// create component's
			// ������ �������� 
		JPanel panelLoad=new JPanel();
		panelLoad.setBorder(javax.swing.BorderFactory.createTitledBorder("�������� ������"));
		panelLoad.setLayout(new BorderLayout());
		
		dateReport=new JDateChooser(new Date());
		monthChooser=new JMonthChooser();
		yearChooser=new JYearChooser();

		buttonOne=new JRadioButton("��� 1",true);
		buttonTwo=new JRadioButton("��� 2");
		ButtonGroup group=new ButtonGroup();
		group.add(buttonOne);
		group.add(buttonTwo);

		buttonBorgYes=new JRadioButton("��");
		buttonBorgNo=new JRadioButton("���",true);
		ButtonGroup groupBorg=new ButtonGroup();
		groupBorg.add(buttonBorgYes);
		groupBorg.add(buttonBorgNo);
		
		buttonProplataYes=new JRadioButton("��");
		buttonProplataNo=new JRadioButton("���",true);
		ButtonGroup groupProplata=new ButtonGroup();
		groupProplata.add(buttonProplataYes);
		groupProplata.add(buttonProplataNo);
		
		buttonPlataYes=new JRadioButton("����� �� ���������");
		buttonPlataNo=new JRadioButton("��� ����� �� ���������");
		buttonPlataAll=new JRadioButton("��� ������",true);
		ButtonGroup groupPlata=new ButtonGroup();
		groupPlata.add(buttonPlataYes);
		groupPlata.add(buttonPlataNo);
		groupPlata.add(buttonPlataAll);
		
		comboViddil=new JComboBox(new String[]{"1","2","3","4","5","��� ���������"});
		JButton buttonViddilEdit=new JButton("...");
		JPanel panelWrapViddil=new JPanel(new BorderLayout());
		panelWrapViddil.add(comboViddil);
		panelWrapViddil.setBorder(javax.swing.BorderFactory.createTitledBorder("�����"));
		panelWrapViddil.add(buttonViddilEdit,BorderLayout.EAST);
		buttonPochta=new JRadioButton("");
		buttonViddil=new JRadioButton("",true);
		labelPochta=new JLabel("�����");
		labelPochta.setEnabled(false);
		ButtonGroup groupViddil=new ButtonGroup();
		groupViddil.add(buttonPochta);
		groupViddil.add(buttonViddil);
		JPanel panelViddil=new JPanel();
		GroupLayout groupLayout=new GroupLayout(panelViddil);
		panelViddil.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addComponent(buttonViddil,20,20,20)
											   	 .addComponent(panelWrapViddil,120,120,120)
											   	 )
										.addGroup(groupLayout.createSequentialGroup()
											   	 .addComponent(buttonPochta,20,20,20)
											   	 .addComponent(labelPochta,120,120,120)
											   	 )
									   );
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
									 .addComponent(buttonViddil)
									 .addComponent(panelWrapViddil)
									 );
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
				 					 .addComponent(buttonPochta)
				 					 .addComponent(labelPochta)
				 					);
		
		JButton buttonLoad=new JButton("��������� ������");
		JButton buttonRefresh=new JButton("��������");
		// placing
		JPanel panelWrapDateReport=new JPanel(new GridLayout(1,1));
		panelWrapDateReport.setBorder(javax.swing.BorderFactory.createTitledBorder("���� ������"));
		panelWrapDateReport.add(this.dateReport);
		
		JPanel panelCriterion=new JPanel();
		JPanel panelWrapMonthChooser=new JPanel();
		groupLayout=new GroupLayout(panelWrapMonthChooser);
		panelWrapMonthChooser.setLayout(groupLayout);
		groupLayoutHorizontal=groupLayout.createSequentialGroup();
		groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		
		groupLayoutHorizontal.addComponent(monthChooser,100,100,100);
		groupLayoutHorizontal.addComponent(yearChooser,60,60,60);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
									 .addComponent(monthChooser)
									 .addComponent(yearChooser)
							         );
		panelWrapMonthChooser.setBorder(javax.swing.BorderFactory.createTitledBorder("����� � ���"));
		
		JPanel panelWrapRadioButton=new JPanel(new GridLayout(1,2));
		panelWrapRadioButton.add(buttonOne);
		panelWrapRadioButton.add(buttonTwo);
		panelWrapRadioButton.setBorder(javax.swing.BorderFactory.createTitledBorder("����� ���"));

		JPanel panelWrapRadioButtonBorg=new JPanel(new GridLayout(1,2));
		panelWrapRadioButtonBorg.add(buttonBorgYes);
		panelWrapRadioButtonBorg.add(buttonBorgNo);
		panelWrapRadioButtonBorg.setBorder(javax.swing.BorderFactory.createTitledBorder("�������������"));
		
		JPanel panelWrapRadioButtonProplata=new JPanel(new GridLayout(1,2));
		panelWrapRadioButtonProplata.add(buttonProplataYes);
		panelWrapRadioButtonProplata.add(buttonProplataNo);
		panelWrapRadioButtonProplata.setBorder(javax.swing.BorderFactory.createTitledBorder("��������"));

		JPanel panelWrapRadioButtonPlata=new JPanel(new GridLayout(3,1));
		panelWrapRadioButtonPlata.add(buttonPlataAll);
		panelWrapRadioButtonPlata.add(buttonPlataYes);
		panelWrapRadioButtonPlata.add(buttonPlataNo);
		
		
		groupLayout=new GroupLayout(panelCriterion);
		panelCriterion.setLayout(groupLayout);
		groupLayoutHorizontal=groupLayout.createSequentialGroup();
		groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		
		groupLayoutHorizontal.addComponent(panelWrapDateReport,100,100,100);
		groupLayoutHorizontal.addComponent(panelWrapMonthChooser,175,175,175);
		groupLayoutHorizontal.addComponent(panelWrapRadioButton,150,150,150);
		groupLayoutHorizontal.addComponent(panelWrapRadioButtonPlata,150,150,150);
		groupLayoutHorizontal.addComponent(panelViddil,150,150,150);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
									 .addComponent(panelWrapDateReport,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									 .addComponent(panelWrapMonthChooser,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									 .addComponent(panelWrapRadioButton,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									 .addComponent(panelWrapRadioButtonPlata,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									 .addComponent(panelViddil,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									);
		
		panelLoad.add(panelCriterion);
		JPanel panelButtonLoad=new JPanel(new BorderLayout());
		panelButtonLoad.add(buttonLoad,BorderLayout.CENTER);
		panelButtonLoad.add(buttonRefresh,BorderLayout.WEST);
		panelLoad.add(panelButtonLoad,BorderLayout.SOUTH);
			// ������ ����������� ����������� 
		JPanel panelResult=new JPanel(new GridLayout(1,1));
		table=new ResultTable(this.connection,
							  this.getPathToHtml(),
							  this.getPathToHtmlAkt()
							  );
		panelResult.add(new JScrollPane(table));
		panelResult.setBorder(javax.swing.BorderFactory.createTitledBorder("������ ��� ������"));
		
			// ������ ���������� �����������
		JButton buttonPrintCurrent=new JButton("������ ������� ����-������� ");
		JButton buttonPrintAll=new JButton("������ ���� ����-������");
		JButton buttonPrintCurrentAkt=new JButton("������ �������� ����");
		JButton buttonPrintAllAkt=new JButton("������ ���� �����");
		JButton buttonSquare=new JButton("����� �� ��������");
		JButton buttonOborotka=new JButton("��������� ���������");
		JPanel panelManager=new JPanel();
		panelManager.setBorder(javax.swing.BorderFactory.createTitledBorder("����� ������"));
		groupLayout=new GroupLayout(panelManager);
		panelManager.setLayout(groupLayout);
		groupLayoutHorizontal=groupLayout.createSequentialGroup();
		groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									   .addGroup(groupLayout.createSequentialGroup()
											    .addGap(165)
											    .addComponent(panelWrapRadioButtonBorg,100,100,100)
											    .addGap(10)
											    .addComponent(panelWrapRadioButtonProplata,100,100,100)
									   )
									   .addGroup(groupLayout.createSequentialGroup()
												.addGap(10)// 25
												.addComponent(buttonPrintCurrent,250,250,250)
												.addGap(20)
												.addComponent(buttonPrintAll,200,200,200)
											   	)
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(10)
												.addComponent(buttonPrintCurrentAkt,250,250,250)
												.addGap(20)
												.addComponent(buttonPrintAllAkt,200,200,200)
												.addGap(100)
											   	)
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(10)
												.addComponent(buttonSquare,250,250,250)
												.addGap(20)
												.addComponent(buttonOborotka,200,200,200)
												.addGap(100)
												
												 )
									   );
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				 .addComponent(panelWrapRadioButtonBorg)
				 .addComponent(panelWrapRadioButtonProplata)
									);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
									 .addComponent(buttonPrintCurrent)
									 .addComponent(buttonPrintAll)
									);
		groupLayoutVertical.addGap(5);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
				 					.addComponent(buttonPrintCurrentAkt)
				 					.addComponent(buttonPrintAllAkt)
									);
		groupLayoutVertical.addGap(5);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
									 .addComponent(buttonSquare)
									 .addComponent(buttonOborotka)
									 );
		
		// add listener's
		buttonViddilEdit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonViddilEdit();
			}
		});
		buttonOborotka.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonOborotka();
			}
		});
		buttonSquare.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonSquare();
			}
		});
		buttonPrintCurrentAkt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonPrintCurrentAkt();
			}
		});
		buttonPrintAllAkt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonPrintAllAkt();
			}
		});
		
		buttonLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonLoadClick();
			}
		});
		buttonRefresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonRefresh();
			}
		});
		
		
		buttonPrintCurrent.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonPrintCurrentClick();
			}
		});
		buttonPrintAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonPrintAllClick();
			}
		});

		
		ActionListener visualChange=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onChangeVisualCriterion();
			}
		};
		ActionListener buttonJKChange=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onChangeJK();
			}
		};
		ActionListener buttonPochtaChange=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onChangePochta();
			}
		};
		
		buttonOne.addActionListener(visualChange);
		buttonTwo.addActionListener(visualChange);
		buttonPlataAll.addActionListener(visualChange);
		buttonPlataYes.addActionListener(visualChange);
		buttonPlataNo.addActionListener(visualChange);
		comboViddil.addActionListener(visualChange);
		buttonViddil.addActionListener(visualChange);
		buttonPochta.addActionListener(visualChange);
		
		buttonOne.addActionListener(buttonJKChange);
		buttonTwo.addActionListener(buttonJKChange);

		buttonViddil.addActionListener(buttonPochtaChange);
		buttonPochta.addActionListener(buttonPochtaChange);
		
		PropertyChangeListener propertyChangeListener=new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if((evt.getPropertyName().equalsIgnoreCase("year"))||(evt.getPropertyName().equalsIgnoreCase("month"))){
					onChangeVisualCriterion();
				}
			}
        };
		monthChooser.addPropertyChangeListener(propertyChangeListener);
		yearChooser.addPropertyChangeListener(propertyChangeListener);
		
		// placing
		JPanel panelMain=new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.add(panelLoad,BorderLayout.NORTH);
		panelMain.add(panelResult,BorderLayout.CENTER);
		panelMain.add(panelManager,BorderLayout.SOUTH);
		//this.getContentPane().add(panelMain);
		this.add(panelMain);
	}
	
    private boolean copyFile(String source, String destination) throws Exception{
        boolean return_value=false;
        File file_source = new File(source);
        File file_destination = new File(destination);
        InputStream in = new FileInputStream(file_source);

      //For Append the file.
//      OutputStream out = new FileOutputStream(f2,true);
      //For Overwrite the file.
      OutputStream out = new FileOutputStream(file_destination);

        // buffer size
        byte[] buf = new byte[4096];
        int len;
        while ((len = in.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        return_value=true;
        return return_value;
    }
	
	/** reaction on Striking buttonLoad*/
	private void onButtonLoadClick(){
		Cursor cursor=this.getCursor();
		try{
			String pathToDbf=this.getPathToDbf();
			String pathToSourceDbf=this.getPathToSourceDBF();
			String currentFileName=null;
			// ����������� �� ����������-��������� � ���������� ��������
			try{
				currentFileName="arn.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
			}
			try{
				currentFileName="npom.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
			}
			try{
				currentFileName="ul.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
			}
			try{
				currentFileName="mfo.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
				throw new Exception("copy ERROR");
			}
			try{
				currentFileName="viddil_js.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
				throw new Exception("copy ERROR");
			}
			try{
				currentFileName="ura_pp.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
				throw new Exception("copy ERROR");
			}
			try{
				currentFileName="ura_wp.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
				throw new Exception("copy ERROR");
			}
			try{
				currentFileName="pochta.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
				throw new Exception("copy ERROR");
			}
			
			String fileTnName=null;
			fileTnName="tn"+this.yearChooser.getYear()+MessageFormat.format("{0,number,00}",new Object[]{new Integer(this.monthChooser.getMonth()+1)})+".dbf";
			try{
				currentFileName=fileTnName;
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
				throw new Exception("copy ERROR");
			}
			String fileOsName=null;
			fileOsName="os"+this.yearChooser.getYear()+MessageFormat.format("{0,number,00}",new Object[]{new Integer(this.monthChooser.getMonth()+1)})+".dbf";
			try{
				currentFileName=fileOsName;
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
				throw new Exception("copy ERROR");
			}
			String filePlName=null;
			filePlName="pl"+this.yearChooser.getYear()+MessageFormat.format("{0,number,00}",new Object[]{new Integer(this.monthChooser.getMonth()+1)})+".dbf";
			try{
				currentFileName=filePlName;
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "������ ����������� �����:\n"+pathToSourceDbf+currentFileName+"\n � �������:"+pathToDbf+"\n"+ex.getMessage());
				throw new Exception("copy ERROR");
			}
			
			
			// INFO ����� �������� ���� ������ �� DBF
			File fileArn=new File(getPathToDbf()+"arn.dbf ");
			File fileNpom=new File(getPathToDbf()+"npom.dbf ");
			File fileMfo=new File(getPathToDbf()+"mfo.dbf ");
			File fileTn=new File(getPathToDbf()+fileTnName);
			File fileUl=new File(getPathToDbf()+"ul.dbf");
			File fileViddil=new File(getPathToDbf()+"viddil_js.dbf");
			File fileUraPp=new File(getPathToDbf()+"ura_pp.dbf");
			File fileUraWp=new File(getPathToDbf()+"ura_wp.dbf");
			File filePochta=new File(getPathToDbf()+"pochta.dbf");
			File fileOs=new File(getPathToDbf()+fileOsName);
			File filePl=new File(getPathToDbf()+filePlName);
			
			if(fileArn.exists()==false){
				throw new Exception("�� ������ ����  ARN.DBF");
			}
			if(fileNpom.exists()==false){
				throw new Exception("�� ������ ����  Npom.DBF");
			}
			if(fileTn.exists()==false){
				throw new Exception("�� ������ ���� "+fileTnName);
			}
			if(fileOs.exists()==false){
				throw new Exception("�� ������ ���� "+fileOsName);
			}
			if(filePl.exists()==false){
				throw new Exception("�� ������ ���� "+filePlName);
			}
			if(fileUl.exists()==false){
				throw new Exception("�� ������ ����  ul.DBF");
			}
			if(fileViddil.exists()==false){
				throw new Exception("�� ������ ����  Viddil_js.DBF");
			}
			if(fileMfo.exists()==false){
				throw new Exception("�� ������ ����  mfo.DBF");
			}
			if(fileUraPp.exists()==false){
				throw new Exception("�� ������ ����  ura_pp.DBF");
			}
			if(fileUraWp.exists()==false){
				throw new Exception("�� ������ ����  ura_wp.DBF");
			}
			if(filePochta.exists()==false){
				throw new Exception("�� ������ ����  pochta.DBF");
			}
			//File logFile=new File("c:\\logger_gitloservice.txt");
			connection=connector.getConnection();
			connection.setAutoCommit(false);
			if(connection==null){
				throw new Exception("Connection is null");
			}
			Converter converter=new Converter(new File[]{fileArn, fileNpom, fileTn, fileUl,fileViddil,fileMfo,fileOs,filePl,fileUraPp,fileUraWp,filePochta},
											  connection,
											  null);
			//Converter converter=new Converter(new File[]{fileTn},connector.getConnection(),logFile);
			this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			if(converter.convert()==false){
				this.setCursor(cursor);
				this.table.clearData();
				System.out.println("Converter Error");
				JOptionPane.showMessageDialog(this, "�� ������� ��������� ������", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				this.setCursor(cursor);
				this.compareAndAddingViddilJs(this.connection);
				this.table.refreshData(this.dateReport.getDate(),
									   this.monthChooser.getMonth(),
									   this.yearChooser.getYear(),
									   this.buttonOne.isSelected(), 
									   this.buttonTwo.isSelected(),
									   (String)this.comboViddil.getSelectedItem(),
									   this.buttonPlataAll.isSelected(),
									   this.buttonPlataYes.isSelected(),
									   this.buttonPlataNo.isSelected(),
									   this.buttonPochta.isSelected()
									   );
				this.table.getParent().repaint();
				System.out.println("OK");
			}
		}catch(Exception ex){
			this.setCursor(cursor);
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("EnterPoint exception: "+ex.getMessage());
		}
	}
	
	/** ���������� ������� VIDDIL_JS �� ������� ���������� ������ �� NPOM*/
	private void compareAndAddingViddilJs(Connection connection){
		// FIXME
		try{
			// �������� ������ �� ������� NPOM
			ResultSet npom=this.connection.createStatement().executeQuery("SELECT DISTINCT NPOM.UL1,NPOM.DM1, NPOM.LT1,UL.NAIU FROM NPOM LEFT JOIN UL ON UL.UL=NPOM.UL1");
			ArrayList<ArrayList<Object>> writeData=new ArrayList<ArrayList<Object>>();
			// ��������� ��� ������ �� ������� ��������� �� � ������� VIDDIL_JS
			while(npom.next()){
				//System.out.println("Current record: <"+npom.getFloat("UL1")+">   <"+npom.getFloat("DM1")+">  <"+npom.getString("LT1")+">");
				String query="SELECT UL1 FROM VIDDIL_JS WHERE UL1="+npom.getFloat("UL1")+" AND DM1="+npom.getFloat("DM1")+" AND trim(LT1)='"+npom.getString("LT1").trim()+"'";
				//System.out.println("Query: "+query);
				ResultSet queryViddil=this.connection.createStatement().executeQuery(query);
				if(!queryViddil.next()){
					// ������ ������ �� ������� - �������� � ����
					ArrayList<Object> currentRecord=new ArrayList<Object>();
					System.out.println("New record into VIDDIL_JS: "+npom.getObject("UL1").toString()+"   "+npom.getObject("DM1"));
					currentRecord.add(npom.getObject("UL1"));
					currentRecord.add(npom.getObject("DM1"));
					currentRecord.add(npom.getObject("LT1"));
					currentRecord.add(npom.getObject("NAIU"));
					writeData.add(currentRecord);
				}
			}
			// �������� ����� ������ � ������� VIDDIL_JS
			if(writeData.size()>0){
				System.out.println("Data for write: "+writeData.size());
				//PreparedStatement insertViddil=this.connection.prepareStatement("INSERT INTO VIDDIL_JS(UL1,DM1,LT1,NAIU) VALUES(?,?,?,?)");
				PreparedStatement insertViddil=this.connection.prepareStatement("INSERT INTO VIDDIL_JS(UL1,DM1,LT1,NAIU) VALUES(?,?,?,?)");
				for(int counter=0;counter<writeData.size();counter++){
					try{
						insertViddil.clearParameters();
						for(int index=0;index<4;index++){
							if(writeData.get(counter).get(index)!=null){
								if(writeData.get(counter).get(index) instanceof String){
									insertViddil.setString(index+1, ((String)writeData.get(counter).get(index)).trim());
								}else{
									insertViddil.setObject(index+1, writeData.get(counter).get(index));
								}
							}else{
								insertViddil.setObject(index+1, writeData.get(counter).get(index));
							}
							
						}
						if(insertViddil.executeUpdate()>0){
							System.out.println("inserted");
						}
						this.connection.commit();
					}catch(Exception ex){
						System.err.println("insert record Exception:"+writeData.get(0)+"   "+ex.getMessage());
					}
				}
			}
			
		}catch(Exception ex){
			System.err.println("Dovidka#compareAndAddingViddilJs:"+ex.getMessage());
		}
		
		/*�������� ��� viddil_js	
		���� ������� ����� �� ������� npom �������	
		ul1_,dm1_,lt1_,naiu_____	
		�� ���� ������ �������� ���� viddil_js.dbf � ��������� ��� ������������ ��������	
		������� ��������� ����� ������� viddil_js.dbf - ����������� ��������������� ���� VIDDIL (��������� ������ 1 �� 9)
		*/
	}
	
	/** ���������� ������ �� ��������� ��� ����������� */
	private void onButtonRefresh(){
		Cursor cursor=this.getCursor();
		try{
			this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			this.table.refreshData(this.dateReport.getDate(),
					   this.monthChooser.getMonth(),
					   this.yearChooser.getYear(),
					   this.buttonOne.isSelected(), 
					   this.buttonTwo.isSelected(),
					   (String)this.comboViddil.getSelectedItem(),
					   this.buttonPlataAll.isSelected(),
					   this.buttonPlataYes.isSelected(),
					   this.buttonPlataNo.isSelected(),
					   this.buttonPochta.isSelected()
					   );
			this.table.getParent().repaint();
			this.setCursor(cursor);
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("EnterPoint exception: "+ex.getMessage());
		}
	}
	
	
	String[] monthName=new String[]{"�i����","�����","��������","��i����","�������","�������","������","�������","��������","�������","��������","�������"};
	/** reaction on striking button print Current */
	private void onButtonPrintCurrentClick(){
		// INFO ����� ������ ������� ������ ����-������� 
		this.table.printSelection(monthName[this.monthChooser.getMonth()],
								  Integer.toString(this.yearChooser.getYear()),
								  Calendar.getInstance(),
								  this.buttonOne.isSelected(),
								  this.buttonBorgYes.isSelected(),
								  this.buttonProplataYes.isSelected(),
								  this.getOutFile());
	}
	/** reaction on striking button print All*/
	private void onButtonPrintAllClick(){
		// INFO ����� ������ ���� ������� ����-������
		this.table.printAll(monthName[this.monthChooser.getMonth()],
				  			Integer.toString(this.yearChooser.getYear()),
				  			Calendar.getInstance(),
				  			this.buttonOne.isSelected(),
				  			this.buttonBorgYes.isSelected(),
				  			this.buttonProplataYes.isSelected(),
				  			this.getOutFile());
	}
	
	/** reaction on strking button "Print Current Akt"*/
	private void onButtonPrintCurrentAkt(){
		// INFO ����� ������ �������� ����  
		this.table.printSelectionAkt(monthName[this.monthChooser.getMonth()],
				  Integer.toString(this.yearChooser.getYear()),
				  Calendar.getInstance(),
				  this.getOutFile());
	}

	/** reaction on strking button "Print All Akt"*/
	private void onButtonPrintAllAkt(){
		// INFO ����� ������ ���� �����
		this.table.printAllAkt(monthName[this.monthChooser.getMonth()],
	  			Integer.toString(this.yearChooser.getYear()),
	  			Calendar.getInstance(),
	  			this.getOutFile());
	}
	
	/** ��������� � �������� ������ ���������� ����������� */
	private void onChangeVisualCriterion(){
		this.table.clearData();
		this.table.repaint();
	}
	
	/** */
	private void onButtonSquare(){
		try{
			this.table.printSquareToExcel(monthName[this.monthChooser.getMonth()],
	  				  					  Integer.toString(this.yearChooser.getYear()),
	  				  					  (this.buttonOne.isSelected())?"1":"2",
	  				  					   this.comboViddil.getSelectedItem().toString(),
	  				  					  Calendar.getInstance(),
	  				  					  this.getOutExcelFile());
		}catch(Exception ex){
			System.err.println("Error out report: "+ex.getMessage());
			JOptionPane.showMessageDialog(this, "���������� Excel ����","������",JOptionPane.ERROR_MESSAGE);
		}
	}

	/** ������� �� ������� ������ �������������� ������ */
	private void onButtonViddilEdit(){
		// ���������� ��������� ���� ��� �������������� ������
		JDialog dialog=new JDialog(this.parentFrame);
		dialog.add(new ViddilEdit(dialog,this.connection));
		dialog.setModal(true);
		dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		dialog.setTitle("�������������� ���������");
		Position.setDialogToCenterBySize(dialog, 400, 200);
		dialog.setVisible(true);
		this.table.clearData();
		this.table.repaint();
	}
	
	/** ������� �� ����� ������ JK */
	private void onChangeJK(){
		if(this.buttonOne.isSelected()){
			this.buttonPlataAll.setEnabled(true);
			this.buttonPlataYes.setEnabled(true);
			this.buttonPlataNo.setEnabled(true);
		}else{
			this.buttonPlataAll.setEnabled(false);
			this.buttonPlataYes.setEnabled(false);
			this.buttonPlataNo.setEnabled(false);
		}
	}

	/** ������� �� ����� ����� ��� ��������� */
	private void onChangePochta(){
		if(this.buttonPochta.isSelected()){
			this.labelPochta.setEnabled(true);
			this.comboViddil.setEnabled(false);
		}else{
			this.labelPochta.setEnabled(false);
			this.comboViddil.setEnabled(true);
		}
	}

	/** ������� �� ������� ������ "��������� ���������"*/
	private void onButtonOborotka(){
		Cursor cursor=this.getCursor();
		if(this.buttonOne.isSelected()){ //&&this.buttonBorgYes.isSelected()
			this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try{
				this.table.printOborotkaToExcel(monthName[this.monthChooser.getMonth()],
		  				  					  Integer.toString(this.yearChooser.getYear()),
		  				  					  (this.buttonOne.isSelected())?"1":"2",
		  				  					   this.comboViddil.getSelectedItem().toString(),
		  				  					  Calendar.getInstance(),
		  				  					  this.getOutExcelFile());
				this.setCursor(cursor);
			}catch(Exception ex){
				this.setCursor(cursor);
				System.err.println("Error out report: "+ex.getMessage());
				JOptionPane.showMessageDialog(this, "���������� Excel ����","������",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			this.setCursor(cursor);
			if(this.buttonTwo.isSelected()==true){
				this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				try{
					this.table.printOborotkaToExcel2(monthName[this.monthChooser.getMonth()],
			  				  					  Integer.toString(this.yearChooser.getYear()),
			  				  					  (this.buttonOne.isSelected())?"1":"2",
			  				  					   this.comboViddil.getSelectedItem().toString(),
			  				  					  Calendar.getInstance(),
			  				  					  this.getOutExcelFile());
					this.setCursor(cursor);
				}catch(Exception ex){
					this.setCursor(cursor);
					System.err.println("Error out report: "+ex.getMessage());
					JOptionPane.showMessageDialog(this, "���������� Excel ����","������",JOptionPane.ERROR_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(this, "��������������","�������� ���� �����",JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}
	
	
	/** �������� ���� Excel ��� ������ ������ */
	private String getOutExcelFile(){
		return "c:\\out.xls";
	}
	
	public void finalize(){
		if(this.connection!=null){
			try{
				this.connection.close();
			}catch(SQLException ex){
			}
		}
	}
}
