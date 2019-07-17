package reporter.replacer;

import java.text.MessageFormat;
import java.util.ArrayList;

/** ���������� ���� ����������� �������� � ���� ���������� � ������������ ������ ��� ��������� MessageFormat*/
public class MultiReplacer extends ReplaceValue{
	/** ������, ������� �������� �������� ��� ���������� MessageFormat.format*/
	private String messageFormatString;
	/** ����� �������� �� ���������, � ������ �� ���������� �� ����� ������ ��� ������*/
	private String defaultValue=null;
	/** ������� ��� ������ � ������ */
	private ArrayList<Object[]> listOfObject=new ArrayList<Object[]>();
	
	/** ���������� ���� ����������� �������� � ���� ���������� � ����������� ������ ��� ��������� MessageFormat
	 * @param messageFormatString - ������, ������� �������� �������� ��� MessageFormat.format
	 * */
	public MultiReplacer(String messageFormatString,String defaultValue){
		this.messageFormatString=messageFormatString;
		this.defaultValue=defaultValue;
	}
	
	/** �������� ��� ������-�������*/
	public void clearObjects(){
		this.listOfObject.clear();
	}
	
	public void add(Object ... objects){
		this.listOfObject.add(objects);
	}
	@Override
	public String getReplaceValue() {
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<listOfObject.size();counter++){
			returnValue.append(MessageFormat.format(messageFormatString, this.listOfObject.get(counter)));
		}
		if(returnValue.length()==0){
			return defaultValue;
		}else{
			return returnValue.toString();
		}
	}

}
