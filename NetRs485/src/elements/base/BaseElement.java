package elements.base;

import java.util.Date;

import net_exchange.Exchanger;

/** �����, ������� �������� ������� ��� ���� ����������, 
 * ������� ����� ������� ������������� ��� ������-��������� ��������� ����� ���� ��������� �� ����������� ���������� */
public abstract class BaseElement {
	private Exchanger exchanger;
	public final static byte SEND_OK=0x1;
	public final static byte SEND_CANCEL=0x2;
	public final static byte SEND_ERROR=0x3;
	
	//public final static byte FLAG_BEGIN_PACKAGE=0x0a;
	//public final static byte FLAG_END_PACKAGE=0x0c;
	/** ����� ������� ������ �������� �� ������� � ���� */
	public static byte FUNCTION_SOFT_LOCATOR=0x01;
	/** ��������� �������� �� ���������� */
	public static byte FUNCTION_SET_SETTINGS=0x02;
	/** ��������� �������� � ���������� */
	public static byte FUNCTION_GET_SETTINGS=0x03;
	/** ��������� ��������� �� ���������� */
	public static byte FUNCTION_SET_STATE=0x04;
	/** ��������� ��������� � ���������� */
	public static byte FUNCTION_GET_STATE=0x05;
	
	
	/** ��������� �������� */
	protected ElementSettings settings;
	/** ����� ��������� �������� */
	private Date timeSettings;
	/** ��������� �������� */
	protected ElementState state;
	/** ����� ��������� ��������� �������� */
	private Date timeState;
	/** ���������� ����� ���������� � ���� */
	private byte address;
	/** ���������� ����� ������ ������� */
	private Integer uniqueNumber;
	
	/** ������� �������
	 * @param exchanger - ������-���������, ������� ���� ������ � ��������� ������ 
	 * @param address - ����� ���������� � ����
	 * @param data (nullable) - ����� ��������� �������� ������������� �������� � ����������� ��������� ���������� ��� ����������� ������� ������  
	 * */
	public BaseElement(Integer uniqueNumber, Exchanger exchanger,Byte address, byte[] data ){
		this.exchanger=exchanger;
		this.address=address;
		this.uniqueNumber=uniqueNumber;
	}
	
	/** �������� ���������� ��� ���������� */
	public Integer getUniqueType(){
		return uniqueNumber;
	}
	/** �������� �������� ������� ���������� */
	public String getDescription(){
		return "";
	}
	
	/** ���������� ��������� �� ���������� - 
	 * <b>������ ����� �� ���������� ���������� �� ��������� ������ � �����������</b> 
	 * @param settings - ���������, ������� ������� ������� �� ���������� 
	 * */
	public byte setSettings(ElementSettings settings ){
		byte returnValue=SEND_ERROR;
		this.settings=null;
		this.timeSettings=null;
		// ������� �� ���������� �������� ������ � �������� ����� � ���� �������� ������������������
		try{
			byte[] answer=this.exchanger.sendData(this.createPackage(FUNCTION_SET_SETTINGS,settings.getByteData()));
			if(answer!=null){
				// ������ ������� ������� ����������� �������� - ������ ������� �� ����� 
				if(decodeSetSettingsAnswer(answer)==true){
					// ������ ������� �����������
					this.settings=settings;
					this.timeSettings=new Date();
				}else{
					// ������ �� �����������
					this.settings=null;
				}
			}else{
				// ������ �� �������� �� ����������� �������
				returnValue=SEND_ERROR;
			}
		}catch(Exception ex){
			// ������ �� �������� �� ����������� �������
			returnValue=SEND_ERROR;
		}
		return returnValue;
	}
	
	/** ���������� ��������� �� ���������� - 
	 * <b>������ ����� �� ���������� ���������� �� ��������� ������ � �����������</b> 
	 * @param settings - ���������, ������� ������� ������� �� ���������� 
	 * */
	public byte setState(ElementState state ){
		byte returnValue=SEND_ERROR;
		this.state=null;
		this.timeState=null;
		// ������� �� ���������� �������� ������ � �������� ����� � ���� �������� ������������������
		try{
			byte[] answer=this.exchanger.sendData(this.createPackage(FUNCTION_SET_STATE,state.getByteData()));
			if(answer!=null){
				// ������ ������� ������� ����������� �������� - ������ ������� �� ����� 
				if(decodeSetStateAnswer(answer)==true){
					// ������ ������� �����������
					this.state=state;
					this.timeState=new Date();
				}else{
					// ������ �� �����������
					this.state=null;
				}
			}else{
				// ������ �� �������� �� ����������� �������
				returnValue=SEND_ERROR;
			}
		}catch(Exception ex){
			// ������ �� �������� �� ����������� �������
			returnValue=SEND_ERROR;
		}
		return returnValue;
	}

	/** �������� �������� ����� �� ��������� ��������� 
	 * @data - ������ ���������� �� ��������
	 * @return 
	 * <li>true - ������ ������� ���������� </li>
	 * <li>false - ������ �� ���������� ��� ����������</li>
	 * */
	public abstract boolean decodeSetStateAnswer(byte[] data);
	
	
	/**
	 * �������� ��������� � ����������
	 * <b>������ ����� �� ���������� ���������� �� ��������� ������ � ����������</b>  
	 */
	public ElementSettings getSettingsFromDevice(){
		this.settings=null;
		// ������� �������-������ �� ��������� ��������
		byte[] request=new byte[]{this.address, FUNCTION_GET_SETTINGS}; 
		// ������� ������� �� ����������,
		try{
			byte[] response=this.exchanger.sendData(request);
			if(response!=null){
				this.settings=decodeElementSettingsFromResponse(response);
			}else{
				// ������� �� ������� �� ��������� ����������  
			}
		}catch(Exception ex){
			// ������ ������ � ����������� 
		}
		return this.settings;
	}

	/**
	 * �������� ��������� � ����������
	 * <b>������ ����� �� ���������� ���������� �� ��������� ������ � ����������</b>  
	 */
	public ElementState getStateFromDevice(){
		this.state=null;
		// ������� �������-������ �� ��������� ��������
		byte[] request=new byte[]{this.address, FUNCTION_GET_STATE}; 
		// ������� ������� �� ����������,
		try{
			byte[] response=this.exchanger.sendData(request);
			if(response!=null){
				this.state=decodeElementStateFromResponse(response);
			}else{
				// ������� �� ������� �� ������ ��������� ����������   
			}
		}catch(Exception ex){
			// ������ ������ � ����������� 
		}
		return this.state;
	}
	
	/** �������� ��������� � ���������� 
	 * @return ����������� �����-�� ���������� ����������  
	 * */
	public ElementState getState(){
		return this.state;
	}
	
	/** �������� ����� ��������� ��������� �������� */
	public Date getTimeState(){
		return this.timeState;
	}
	
	/** �������� ����� ��������� �������� ��� �������� */
	public Date getTimeSettings(){
		return this.timeSettings;
	}
	
	/** ������������ ���������� �� �������� ������ � ElementState
	 * @param response - ������, ���������� �� ��������
	 * */
	protected abstract ElementState decodeElementStateFromResponse(byte[] response);
	
	/** ������������ ���������� �� �������� ������ � ElementSettings 
	 * @param response - ������, ���������� �� ��������
	 * */
	protected abstract ElementSettings decodeElementSettingsFromResponse(byte[] response);
	
	
	/** �������� ���������, ������� �����-�� ���� ��������� � ���������� */
	public ElementSettings getSettings(){
		return this.settings;
	}
	
	/** ������� �� ������� ������������� �� ��������� ��������  
	 * @param response - ������, ������� �������� � ����� �� �������� �������� ���������� 
	 * */
	protected abstract boolean decodeSetSettingsAnswer(byte[] response);
	
	/** ������ �� ��������� �������� */
	
	
	/** ������ �� ��������� �������� */
	/** ������ �� ��������� ��������� */
	/** ������ �� ��������� ��������� */
	
	/* ������� ������� �� ��������� ���������� 
	 * @param data - ������, ������� ���������� �� ����������
	 * @return 
	 * <li> byte[] - ������ ������� �������� � ������� ����� </li>
	 * <li> null - ������ �� ��������, ����� �� ������� </li>
	protected byte[] sendFunction(byte[] data){
		byte[] returnValue=null;
		// ������� ����� ������ � ���� (�� ���� � �������� ����� � ������� �������������� �������, ��� ������� ������)
		System.out.println("BaseElement#sendFunction:");printArray(data);
		try{
			returnValue= exchanger.sendData(data);
		}catch(Exception ex){
			returnValue=null;
		}
		
		return returnValue;
	}*/
	

	/** ������� ����� ��� �������� �� �������
	 * @param functionNumber - ����� ������� ��� �������� 
	 * @param data - ������, ������� ������ ���� �������� � ������� 
	 * */
	private byte[] createPackage(byte functionNumber, byte[] data){
		if(data==null){
			data=new byte[]{};
		}
		byte[] returnValue=new byte[data.length+2];
		returnValue[0]=this.address;
		returnValue[1]=functionNumber;
		for(int counter=0;counter<data.length;counter++){
			returnValue[2+counter]=data[counter];
		}
		return returnValue;
	}

	/** �������� �� �������� ���� ���� ��� �������� */
	@SuppressWarnings("unused")
	private byte[] createByteArrayPackage(byte[] ... data){
		int length=0;
		for(int counter=0;counter<data.length;counter++){
			length+=data[counter].length;
		}
		byte[] returnValue=new byte[length];
		int currentPosition=0;
		for(int counter=0;counter<data.length;counter++){
			for(int index=0;index<data[counter].length;index++){
				returnValue[currentPosition]=data[counter][index];
				currentPosition++;
			}
		}
		return returnValue;
	}
}
