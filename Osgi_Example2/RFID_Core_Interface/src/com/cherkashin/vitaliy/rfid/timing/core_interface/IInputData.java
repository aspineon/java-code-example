package com.cherkashin.vitaliy.rfid.timing.core_interface;

/** ��������� ��� �������� ������ �� ������� ���������� */
public interface IInputData {
	/** ���������� ���������� � �������� ������ �� ��������� ���������� */
	public void notifyAboutInputData(byte[] data);
}
