package com.cherkashin.vitaliy.rfid.web_server.client.view;

import com.google.gwt.i18n.client.Constants;

public interface MainMenu_const extends Constants{
	/** ��������� ��� ������ */
	@DefaultStringValue(value="���� �������� �������")
	public String title();
	
	/** ������ "����������" */
	@DefaultStringValue(value="����������")
	public String buttonShow();
	
	/** ������ "�������������"*/
	@DefaultStringValue(value="�������������")
	public String buttonEdit();
}
