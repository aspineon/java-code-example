package database.serializer.common;

import java.io.Serializable;

/** �����, ������� ������ ��� ������ �� ���������� ������ */
public class Answer implements Serializable{
	private final static long serialVersionUID=1L;
	
	/** ���������� ����� ������� ��������� */
	public static final int OK=0;
	/** ������ ��������� ����������� ������, ��������, �� ������� ������������ ���������� ����� �� ����� �� ������ ������������ */
	public static final int CANCEL=1;
	/** ������ ��������� ����������� ������ */
	public static final int ERROR=2;

}
