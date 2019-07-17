package bc.data_terminal.editor.utility;
import java.util.*;
import java.util.Map.Entry;

public class toHtmlConverter{
	/** ��������� �� ��������� Map HTML ���� SELECT
	 * ������ ����� <OPTION value="map.key"> map.value </OPTION>
	 *  */
	public static String convertMapToSelectBody(Map<String,String> map){
		StringBuffer return_value=new StringBuffer();
		Set<Entry<String,String>> entry=map.entrySet();
		Iterator <Entry<String,String>> iterator=entry.iterator();
		Entry<String,String> current_entry;
		while(iterator.hasNext()){
			current_entry=iterator.next();
			return_value.append("<OPTION ");
			return_value.append(" value=\"");
			return_value.append(current_entry.getKey());
			return_value.append("\">");
			return_value.append(current_entry.getValue());
			return_value.append("</OPTION>");			
		}
		return return_value.toString();
	}
	
	/**
	 * ��������� �� ������ HashMap HTML ���� � ��������� ���������� 
	 * @param map - ������ ��� Select
	 * @param option_value - ������, ������� ������ ���� ��������, ���� ��� ���� � ����� 
	 */
	public static String convertMapToSelectBodyWithSelected(HashMap<String,String> map, String option_value){
		StringBuffer return_value=new StringBuffer();
		Set<Entry<String,String>> entry=map.entrySet();
		Iterator <Entry<String,String>> iterator=entry.iterator();
		Entry<String,String> current_entry;
		while(iterator.hasNext()){
			current_entry=iterator.next();
			return_value.append("<OPTION ");
			return_value.append(" value=\"");
			return_value.append(current_entry.getKey());
			return_value.append("\" ");
			if(current_entry.getKey().equalsIgnoreCase(option_value)){
				return_value.append("selected=\"selected\"");
			}
			return_value.append(">");
			return_value.append(current_entry.getValue());
			return_value.append("</OPTION>");			
		}
		return return_value.toString();
	}
	
	/**
	 * ��������� �� ������ HashMap HTML ���� SELECT � ������ ��������� ������ � ������
	 * @param map - �� ������ �������� ����� �������� ������
	 * @return ����� ��� ������� �� HTML ��������
	 */
	public static String convertMapToSelectBodyWithEmpty(Map<String,String> map){
		StringBuffer return_value=new StringBuffer();
		Set<Entry<String,String>> entry=map.entrySet();
		Iterator <Entry<String,String>> iterator=entry.iterator();
		Entry<String,String> current_entry;
		return_value.append("<OPTION ");
		return_value.append(" value=\"");
		return_value.append("");
		return_value.append("\" selected >");
		return_value.append("");
		return_value.append("</OPTION>");			
		while(iterator.hasNext()){
			current_entry=iterator.next();
			return_value.append("<OPTION ");
			return_value.append(" value=\"");
			return_value.append(current_entry.getKey());
			return_value.append("\">");
			return_value.append(current_entry.getValue());
			return_value.append("</OPTION>");			
		}
		return return_value.toString();
	}
	
}
