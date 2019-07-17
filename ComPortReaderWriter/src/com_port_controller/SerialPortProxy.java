package com_port_controller;

import java.io.OutputStream;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import com_port_controller.listener.ISocketPortListener;
import com_port_controller.listener.SocketHeartBeatProgramRunner;
import com_port_controller.listener.SocketPortListener;

/** ����� ������� ������� ������� ������ � �������� SerialPort, ������� ����������� � ��������� �������� � ��������������� ������ ����������� TCP ������
 * (�������������� ������ ��������, � ������ �� ����������� ������� ������������)*/
public class SerialPortProxy implements ISocketPortListener {
	private Logger logger=Logger.getLogger(this.getClass());
	private SerialPortProxyOutputStream outputStream;
	private ArrayList<IDataNotify> listOfListener=new ArrayList<IDataNotify>();
	
	/** ����� ������� ������� ������� ������ � �������� SerialPort, ������� ����������� � ��������� �������� � ��������������� ������ ����������� TCP ������
	 * (�������������� ������ ��������, � ������ �� ����������� ������� ������������)
	 * @param portWrite - ����, � ������� ����� ������������ ������ ��� ������ � ������ (20100)
	 * @param portRead - ���� �� �������� ����� �������� ������, ��������� ���������� ������ � ������ (20101)
	 * @param portHeartBeat - ����, ������� ����� �������������� �� ������� ������� ������� "������������" (20102) 
	 * @param heartBeatDelay - ������������ ����� �������� ������� "������������" (5000)
	 * @param executeProgram - ������ ��� ������� ��������� � ��������� ������ ��� ������������� COM ����� {@link com_port.ComPort}
	 *  java -jar ComPortReaderWriter.jar COM4 9600 8 1 none 127.0.0.1 20100 20101 20102 4000
	 *  <br>
	 *  ��� ��������� ������� � SerialPort ��������� �������� ����� ������-������� ������, �.�. ��� ����� ��������� � ��������� ������ ��������� ( ����� ���� - ���� ��� ),
	 *  ����� ������� ������� ��������� �� 1000 �� - ��� ������� ����� �� ����� ������ � ���������� ����� ���������
	 *   @throws - ���� �� ������� ���������� ������������� ������ 
	 */
	public SerialPortProxy(int portRead, int portWrite, int portHeartBeat, int heartBeatDelay, String executeProgram, IDataNotify ... listeners) throws Exception{
		if(listeners!=null){
			for(int counter=0;counter<listeners.length;counter++){
				listOfListener.add(listeners[counter]);
			}
		}
		if((listeners==null)||(listeners.length==0)){
			System.err.println("��������� �������� ������ �� ����������!!!");
		}
		// �����, ������� ����� ���������� ������ � ����
		this.outputStream=new SerialPortProxyOutputStream(portWrite);
		(new SocketPortListener(portRead)).addDataListener(this);
		new SocketHeartBeatProgramRunner(portHeartBeat, heartBeatDelay, executeProgram);
	}
	@Override
	public void notifyDataFromPort(byte[] data) {
		if(data!=null){
			logger.debug("�������� ������ � �����"+data.length);
			for(IDataNotify listener:listOfListener){
				listener.notifyDataFromPort(data);
			}
		}
	}
	
	public OutputStream getOutputStream(){
		return this.outputStream;
	}
}
