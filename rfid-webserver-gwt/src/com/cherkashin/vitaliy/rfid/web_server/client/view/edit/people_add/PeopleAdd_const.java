package com.cherkashin.vitaliy.rfid.web_server.client.view.edit.people_add;

import com.google.gwt.i18n.client.Constants;

public interface PeopleAdd_const extends Constants{
	/** ��������� ��� ������ */
	@DefaultStringValue(value="������������� ������ �� �����������")
	public String title();
	
	/** ��������� "�������" */
	@DefaultStringValue(value="�������")
	public String surname();

	/** ��������� "���" */
	@DefaultStringValue(value="���")
	public String name();
	
	/** ������ "���������" */
	@DefaultStringValue(value="���������")
	public String buttonSave();

	/** ������ "��������" */
	@DefaultStringValue(value="��������")
	public String buttonCancel();

	/** ��������� � ������ ������� */
	@DefaultStringValue(value="Input Surname")
	public String alertEmptySurname();
	
	/** ������ ���������� */
	@DefaultStringValue(value="������ ����������")
	public String titleSaveError();

	/** ��������� � ������ ����� */
	@DefaultStringValue(value="Input name")
	public String alertEmptyName();

	/** ��������� �������� */
	@DefaultStringValue(value="Father Name")
	public String fatherName();

	/** ��������� ����� ����� */
	@DefaultStringValue(value="Number of Card")
	public String cardNumber();
	
	/** ���������� ����� ����� */
	@DefaultStringValue(value="Card Number")
	public String alertEmptyCardNumber();
	
	/** ��������� ��� ������� Id */
	@DefaultStringValue(value="id")
	public String columnId();

	/** ��������� ��� ������� ����� ����� */
	@DefaultStringValue(value="card number")
	public String columnCardNumber();

	/** ��������� ��� ������� ���� */
	@DefaultStringValue(value="date")
	public String columnDate();
	
}
