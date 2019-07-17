package pay_desk.questionary;

import java.awt.BorderLayout;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pay_desk.commons.PanelButtonTextField;
import pay_desk.commons.PanelDate;
import pay_desk.commons.PanelTextField;
import pay_desk.commons.combobox.AbstractComboboxModel;
import pay_desk.commons.combobox.DatabaseComboboxModel;
import pay_desk.commons.combobox.PanelCombobox;

import database.ConnectWrap;
import database.Connector;
import database.wrap.Customer;

import swing_framework.AbstractInternalFrame;
import swing_framework.FrameMain;
import swing_framework.messages.IMessageSender;
import swing_framework.messages.WindowMessage;
import swing_framework.messages.WindowMessageType;

public class QuestionaryCustomer extends AbstractInternalFrame{
	private final static long serialVersionUID=1L;

	private AbstractComboboxModel modelCustomerKind;
	private PanelButtonTextField panelAddress;
	private PanelButtonTextField panelAddressHome;
	private PanelTextField panelPhoneMobile;
	private PanelTextField panelPhoneHome;
	private PanelDate panelBirthDay;
	private PanelTextField panelEmail;
	private PanelTextField panelSurname;
	private PanelTextField panelName;
	private PanelTextField panelPenName;
	private ConnectWrap connector;
	private boolean flagEdit=false;
	
	public QuestionaryCustomer(String caption, 
							   IMessageSender messageSender,
							   boolean flagEdit) {
		super(caption, 
			  messageSender, 
			  300, 
			  300, 
			  true, 
			  false);
		this.flagEdit=flagEdit;
		connector=((Connector)FrameMain.getObject("CONNECTOR")).getConnector();
		initComponents();
	}

	
	private void initComponents(){
		JPanel panelManager=new JPanel(new GridLayout(1,2));
		JButton buttonSave=new JButton("���������");
		buttonSave.addActionListener(this.getActionListener(this, "onButtonSave"));
		JButton buttonCancel=new JButton("��������");
		buttonCancel.addActionListener(this.getActionListener(this, "onButtonCancel"));
		panelManager.add(buttonSave);
		panelManager.add(buttonCancel);
		
		JPanel panelMain=new JPanel(new GridLayout(10,1));
		
		modelCustomerKind=new DatabaseComboboxModel(connector.getConnection(), "TEMP_KIND","ID","NAME",1);
		PanelCombobox panelCustomerKind=new PanelCombobox("��� ������������",modelCustomerKind);
		panelSurname=new PanelTextField("�������");
		panelName=new PanelTextField("���");
		panelPenName=new PanelTextField("���������");
		panelBirthDay=new PanelDate("���� ��������");
		panelAddress=new PanelButtonTextField("����� ��������");
		panelAddress.addActionLitener(this.getActionListener(this, "onButtonAddress"));
		panelAddressHome=new PanelButtonTextField("����� ��������");
		panelAddressHome.addActionLitener(this.getActionListener(this, "onButtonAddressHome"));
		panelPhoneMobile=new PanelTextField("��������� �������");
		panelPhoneHome=new PanelTextField("�������� �������");
		panelEmail=new PanelTextField("E-mail");
		panelMain.add(panelCustomerKind);
		panelMain.add(this.panelSurname);
		panelMain.add(this.panelName);
		panelMain.add(this.panelPenName);
		panelMain.add(this.panelBirthDay);
		panelMain.add(this.panelAddress);
		panelMain.add(this.panelAddressHome);
		panelMain.add(this.panelPhoneMobile);
		panelMain.add(this.panelPhoneHome);
		panelMain.add(this.panelEmail);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelManager,BorderLayout.SOUTH);
		this.getContentPane().add(new JScrollPane(panelMain),BorderLayout.CENTER);
	}
	
	public void onButtonSave(){
		System.out.println("Save");
		if(this.flagEdit==false){
			System.out.println("insert");
			Customer customer=new Customer();
			customer.setId_customer_kind((Integer)this.modelCustomerKind.getKeySelectedElement());
			customer.setPhone_mobile(this.panelPhoneMobile.getText());
			customer.setPhone_home(this.panelPhoneHome.getText());
			customer.setBirth_day(this.panelBirthDay.getDate());
			customer.setE_mail(this.panelEmail.getText());
			customer.setSurname(this.panelSurname.getText());
			customer.setName(this.panelName.getText());
			customer.setPen_name(this.panelPenName.getText());
			Session session=this.connector.getSession();
			Transaction transaction=session.beginTransaction();
			session.save(customer);
			transaction.commit();
			JOptionPane.showInternalMessageDialog(this, "������ ���������");
			this.sendMessage(this, new WindowMessage(WindowMessageType.PARENT_NOTIFIER,"INSERT", "OK"));
			this.close();
		}else{
			System.out.println("edit");
		}
	}
	
	public void onButtonAddress(){
		new Address("�����",this,"ADDRESS");
	}
	public void onButtonAddressHome(){
		new Address("����� ��������",this,"ADDRESS_HOME");
	}
	
	public void onButtonCancel(){
		this.close();
	}

	protected void beforeClose(){
		try{
			this.connector.close();
		}catch(NullPointerException ex){};
	}

	@Override
	protected void parentNotifier(WindowMessage message) {
		if(message.getAdditionalIdentifier().equals("ADDRESS")){
			System.out.println("�������� �����:");
			this.panelAddress.setText((String)message.getArgument());
		}
		if(message.getAdditionalIdentifier().equals("ADDRESS_HOME")){
			System.out.println("�������� �����");
		}
	}
}
