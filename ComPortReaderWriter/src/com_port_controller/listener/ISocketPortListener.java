package com_port_controller.listener;

/** ��������� COM-�����  */
public interface ISocketPortListener {
	/** ��������� � ����� ������, ������� ������ �� ���� */
	public void notifyDataFromPort(byte[] data);
}
