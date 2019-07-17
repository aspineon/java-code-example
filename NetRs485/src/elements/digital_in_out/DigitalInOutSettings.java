package elements.digital_in_out;

import java.util.Arrays;

import elements.base.ElementSettings;

public class DigitalInOutSettings extends ElementSettings{
	private byte[] settings;
	
	/** ��������� ��� ������ ��������� �����-������ 
	 * @param pinCount - ���-�� ����������� pin-��
	 * */
	public DigitalInOutSettings(int pinCount){
		this.settings=new byte[pinCount];
	}
	
	/** �������� �������� ��������� ��� ������ */
	public byte[] getSettings(){
		return this.getSettings();
	}
	
	/** ���������� �������� �������� ��������� ��� ������ */
	public void setSettings(byte[] value){
		Arrays.fill(this.settings, (byte)0);
		try{
			for(int counter=0;counter<this.settings.length;counter++){
				this.settings[counter]=value[counter];
			}
		}catch(Exception ex){}
	}
	
	/** ���������� �������� ��������� ������ �� Pin-�� */
	public void setPinSettings(int pinNumber, boolean isOn){
		try{
			if(isOn){
				this.settings[pinNumber]=1;
			}else{
				this.settings[pinNumber]=0;
			}
		}catch(Exception ex){};
	}
	
	
	/** �������� �������� ������ �� Pin-�� */
	public byte getPinSettings(int pinNumber){
		try{
			return this.settings[pinNumber];
		}catch(Exception ex){
			return 0;
		}
	}
	
	
	@Override
	public byte[] getByteData() {
		return this.settings;
	}

}
