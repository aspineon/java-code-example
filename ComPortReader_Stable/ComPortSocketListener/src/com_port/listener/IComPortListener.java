package com_port.listener;

/** ��������� COM-�����  */
public interface IComPortListener {
	/** ��������� � ����� ������, ������� ������ �� ���� */
	public void notifyDataFromPort(byte[] data);
}
