package pay_desk.commons.combobox;

/** �����,������� �������� ������� ��� Combobox*/
public abstract class AbstractComboboxModel {
	/** �������� ��� ��������, ������� ��������� � ������ ������ */
	public abstract Object[] getAllElements();
	
	/** �������� ���������� � ������ ������ �������*/
	public abstract Object getSelectdElement();
	
	/** ���������� ���������� � ������ ������ ������� */
	public abstract void setSelectedElement(Object object);
	
	/** �������� �������� �������, �� ��������� ����������� �������� */
	public abstract Object getKeySelectedElement();
}
