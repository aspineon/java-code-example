package com.cherkashin.vitaliy.rfid.web_server.client.view.show;

import com.google.gwt.i18n.client.Constants;

public interface Show_const extends Constants{
	/** ��������� ��� ������ */
	@DefaultStringValue(value="������ �� �����������")
	public String title();
	
	/** ������ "����������" */
	@DefaultStringValue(value="����������")
	public String buttonShow();
	
	/** ������� ��� ����� ������ */
	@DefaultStringValue(value="���� ������")
	public String dateBegin();

	/** ������� ��� ����� ���������*/
	@DefaultStringValue(value="���� ���������")
	public String dateEnd();
}
