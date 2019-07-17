package com.cherkashin.vitaliy.modbus.core.schedule_read;

import java.util.ArrayList;

/** ���������� � ���� ModBus  */
public class Device {
	/** ����� ���������� � ���� ModBus  */
	private int address;
	/** ������ ������ ��������� */
	private ArrayList<DeviceRegisterBlock> blocks=new ArrayList<DeviceRegisterBlock>();
	
	/** ���������� � ���� ModBus  
	 * @param address - ����� ���������� � ���� 
	 */
	public Device(int address){
		this.address=address;
	}
	
	/** �������� � ���������� ���� ��� ������ */
	public void addReadBlock(DeviceRegisterBlock block){
		this.blocks.add(block);
	}
	
	/** ������� �� ���������� ���� ��� ������ */
	public void removeBlock(DeviceRegisterBlock block){
		this.blocks.remove(block);
	}
	
	/** ������� �� ���������� ���� ��� ������ �� ��� ������  */
	public void removeBlock(int index){
		this.blocks.remove(index);
	}
	
	/** �������� ���-�� ���� ������ � �������  */
	public int getBlockCount(){
		return this.blocks.size();
	}
	
	/** �������� ���� �� ��� ������  */
	public DeviceRegisterBlock getBlock(int index){
		return this.blocks.get(index);
	}

	/** �������� ����� ���������� � ���� ModBus  */
	public int getAddress() {
		return address;
	}
	
	/** ���������� ����� ���������� � ���� ModBus */
	public void setAddress(int address) {
		this.address = address;
	}
	
	
}

