package com_port.listener;

/** ��������� COM-�����  */
public interface ISocketPortListener {
	/** ��������� � ����� ������, ������� ������ �� ���� */
	public void notifyDataFromPort(byte[] data);
}
