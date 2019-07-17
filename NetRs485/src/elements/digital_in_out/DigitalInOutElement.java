package elements.digital_in_out;

import net_exchange.Exchanger;
import elements.base.BaseElement;
import elements.base.ElementSettings;
import elements.base.ElementState;

public class DigitalInOutElement extends BaseElement{
	/** ���-�� ��������� �����-������ */
	private int pinCount=0;
	
	public int getPinCount(){
		return pinCount; 
	}
	
	/** ����������� ��� ������ ��������� �����-������ 
	 * ������ ������ ����� �������� ���-�� �������, ������� ����� ���� ���������������� �� ����(0), ��� �� �����(1),<br>
	 * � ��� �� �������� ������ �� ���� ��������� ( 0 ��� 1) <br>
	 * ��� �� ����� ���� ��� ������ �������� (0 ��� 1)<br>
	 * @param exchanger - ������, ������� ������������ ����������� � ����� 
	 * @param address - ����� ������� ������� 
	 * @param data - ������ � ���-�� ��������� �����-������ 
	 */
	public DigitalInOutElement(Integer uniqueNumber, Exchanger exchanger, byte address, byte[] data) {
		super(uniqueNumber, exchanger, address,data);
		// ���������������� ������ ������� �������
		pinCount=data[0];
		this.settings=new DigitalInOutSettings(this.pinCount);
		this.state=new DigitalInOutState(this.pinCount);
	}
	
	@Override
	public String getDescription(){
		return "DigitalInOutElement";
	}

	@Override
	protected ElementSettings decodeElementSettingsFromResponse(byte[] response) {
		// response[0] - address
		// response[1] - function number
		DigitalInOutSettings tempSettings=new DigitalInOutSettings(this.pinCount);
		try{
			for(int counter=0;counter<pinCount;counter++){
				tempSettings.setPinSettings(counter, response[2+counter]>0);
			};
		}catch(Exception ex){};
		return tempSettings;
	}

	@Override
	protected ElementState decodeElementStateFromResponse(byte[] response) {
		DigitalInOutState tempState=new DigitalInOutState(this.pinCount);
		try{
			// response[0] - address
			// response[1] - function number
			for(int counter=0;counter<pinCount;counter++){
				tempState.setPinState(counter,response[2+counter]>0);
			}
		}catch(Exception ex){
		}
		return tempState;
	}

	@Override
	protected boolean decodeSetSettingsAnswer(byte[] response) {
		try{
			if(response[2]==BaseElement.SEND_OK){
				return true;
			}else{
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

	@Override
	public boolean decodeSetStateAnswer(byte[] data) {
		try{
			if(data[2]==BaseElement.SEND_OK){
				return true;
			}else{
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

}
