package com.cherkashin.vitaliy.rfid.web_server.client.view.edit;

import com.google.gwt.i18n.client.Constants;

public interface Edit_const extends Constants{
	/** ��������� ��� ������ */
	@DefaultStringValue(value="������������� ������ �� �����������")
	public String title();
	
	/** ������ "�������� ����������" */
	@DefaultStringValue(value="��������")
	public String buttonAdd();

	/** ������ "������������� ����������" */
	@DefaultStringValue(value="�������������")
	public String buttonEdit();

	/** ��������� ��� ������� Id */
	@DefaultStringValue(value="id")
	public String columnId();
	
	/** ��������� ��� ������� ���  */
	@DefaultStringValue(value="Name")
	public String columnName();
	
	/** ��������� ��� ������� �������  */
	@DefaultStringValue(value="Surname")
	public String columnSurname();

	/** ��������� ��� ������� ��������  */
	@DefaultStringValue(value="FatherName")
	public String columnFatherName();

	/** ��������� ��� ������� ����� ����� */
	@DefaultStringValue(value="CardNumber")
	public String columnCardNumber();

	/** ������ �������  */
	@DefaultStringValue(value="Remove")
	public String buttonRemove();

	/** ��������� ��� ������� ������� */
	public String titleRemove();

	/** ��������� ��� ������� ������� */
	public String messageRemove();

	/** ������ �������� */
	public String buttonCancel();
}
