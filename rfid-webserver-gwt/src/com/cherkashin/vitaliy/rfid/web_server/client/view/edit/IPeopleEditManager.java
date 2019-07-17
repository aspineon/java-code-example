package com.cherkashin.vitaliy.rfid.web_server.client.view.edit;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("people_edit_manager")
public interface IPeopleEditManager extends RemoteService{
	/** ��������� ������ ������������
	 * @param name - ���
	 * @param surname - �������
	 * @param fatherName - ��������
	 * @param cardNumber - ���������� ����� ����� 
	 * @return 
	 * <li> <b>null</b> - ���������� ������ �������</li>
	 * <li> <b>text</b> - ������ ���������� ������ </li>
	public String savePeople(String name, String surname, String fatherName, String �ardNumber);
	 */
	
	/** �������� ������ ���� ���������� */
	public People[] getAllPeople();
	
	/** ������� ��������� �� ����
	 * @param kod - ���������� ��� ��������� 
	 * @return - 
	 * <li> <b>null</b> - �������� ������ �������</li>
	 * <li> <b>text</b> - ������ �������� ������ </li>
	 */
	public String removePeople(int kod);

	/** ��������� People, ���� �� �������� ���, ���� kod is null 
	 * @param kod if null then UPDATE else INSERT
	 * @param name
	 * @param surname
	 * @param fatherName
	 * @param cardNumber
	 * @return
	 */
	public String savePeople(Integer kod, String name, String surname, String fatherName, String cardNumber);
	
	/** �������� ��������� �����, ������� �� ��������� �� � ������ ������������ */
	public Card[] getLastCardWithoutUser();

	/** �������� People �� ����������� ������ */
	public People getPeople(Integer kod);
}
