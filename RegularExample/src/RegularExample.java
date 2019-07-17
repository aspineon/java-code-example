import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegularExample {
	
	public static void main(String[] args){
		// ������, �� �������� ����� ����� ������ ������������ ��������� ������
		// Pattern pattern=Pattern.compile("([0-9]){2,3}",Pattern.CASE_INSENSITIVE);
		Pattern pattern=Pattern.compile("([0-9]){2}",Pattern.CASE_INSENSITIVE);
		String findValue="����223jj234j";
		// ������, ������� ����� ������ �����������
		Matcher matcher=pattern.matcher(findValue);
		
		System.out.println("Find String:"+findValue+"   length("+findValue.length()+")");
		System.out.println("Find:(������� �� ������ ������ ������������������)"+matcher.find());
		
		System.out.println("RegionStart: "+matcher.regionStart());
		System.out.println("LookingAt:(������� �� ������������ � ������ ������ ������) "+matcher.lookingAt());
		System.out.println("Matches: (������ ������������ �������) "+matcher.matches());
		System.out.println("RegionEnd: "+matcher.regionEnd());
		System.out.println("GroupCount: "+matcher.groupCount());
		
		// System.out.println("Group 1 Start: "+matcher.start());
		// System.out.println("Group 2 End: "+matcher.end());
		
		
		//pattern.matches(regex, input)
	}
}
