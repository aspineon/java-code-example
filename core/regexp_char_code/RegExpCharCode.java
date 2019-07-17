package regexp_char_code;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpCharCode {
	public static void main(String[] args){
		System.out.println("begin");
		// ������, �� �������� ����� ����� ������ ������������ ��������� ������
		// Pattern pattern=Pattern.compile("([0-9]){2,3}",Pattern.CASE_INSENSITIVE);
		Pattern pattern=Pattern.compile("([0-9]){2}",Pattern.CASE_INSENSITIVE);
		pattern=Pattern.compile("(\\x30\\x31){1}");
		String findValue="����223j01j234j";
		// ������, ������� ����� ������ �����������
		Matcher matcher=pattern.matcher(findValue);
		
		System.out.println("Find String:"+findValue+"   length("+findValue.length()+")");
		System.out.println("Find:(������� �� ������ ������ ������������������)"+matcher.find());
		
		// System.out.println("RegionStart: "+matcher.regionStart());
		// System.out.println("LookingAt:(������� �� ������������ � ������ ������ ������) "+matcher.lookingAt());
		// System.out.println("Matches: (������ ������������ �������) "+matcher.matches());
		// System.out.println("RegionEnd: "+matcher.regionEnd());
		// System.out.println("GroupCount: "+matcher.groupCount());

		// System.out.println("012301230123".replaceAll("",""));
		
		System.out.println("Case2Process".matches("([A-z])*2([A-z])*"));
		System.out.println("-end-");
	}
	
}
