package com_port;

import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

/** ������, ������� ���������� �� ��������� ���� ������ � ���� �������� ������������������ ����� ��������� ���������� ������� */
public class TcpHeartBeat extends Thread{
	private Logger logger=Logger.getLogger(this.getClass());
	private String serverName;
	private int port;
	private int delay;
	private byte[] arrayForSend;
	private IEventNotify eventNotify;
	
	/** ������, ������� ���������� �� ��������� ���� ������ � ���� �������� ������������������ ����� ��������� ���������� �������
	 * @param serverName - IP �����, ��� �� ��� �������
	 * @param port - ����, �� ������� ������� ���������� ������ 
	 * @param delay - �������� ����� ����������� ���������  
	 * @param arrayForSend - ������ ��� �������� 
	 */
	public TcpHeartBeat(String serverName, int port, int delay, byte[] arrayForSend, IEventNotify eventNotify){
		this.serverName=serverName;
		this.port=port;
		this.delay=delay;
		this.arrayForSend=arrayForSend;
		this.eventNotify=eventNotify;
	}
	
	private boolean flagRun=false;
	
	/** ���������� ���������� ������ */
	public void stopThread(){
		this.flagRun=false;
	}
	
	@Override
	public void run(){
		logger.debug("start heart beat");
		this.flagRun=true;
		Socket socket=null;
		OutputStream os=null;
		while(this.flagRun){
			try{
				logger.debug("attempt to send HeartBeatData into port:"+this.port);
				// ������� ����
				socket=new Socket(this.serverName, this.port);
				// ������ ������
				os=socket.getOutputStream();
				os.write(this.arrayForSend);
				os.flush();
				os.close();
			}catch(Exception ex){
				logger.error("TcpHeartBeat pulse Exception:"+ex.getMessage());
				logger.error("ComPort will be closed, System.Exit");
				this.eventNotify.needClosePort();
			}finally{
				// ������� �����
				try{
					os.close();
				}catch(Exception innerException){};
				// ������� ����
				try{
					socket.close();
				}catch(Exception innerException){};
			}
			try{
				Thread.sleep(this.delay);
			}catch(Exception ex){}
		}
	}
}
