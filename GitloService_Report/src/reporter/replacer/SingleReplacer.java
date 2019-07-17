package reporter.replacer;

/** ������ � �������� ������ ������ � ������� */
public class SingleReplacer extends ReplaceValue{
	private String value;
	/** ������, ���������� ���� ������ � ������� */
	public SingleReplacer(String value){
		this.value=value;
	}

	/** ������, ���������� ���� ������ � ������� */
	public SingleReplacer(int value){
		this.value=Integer.valueOf(value).toString();
	}
	
	@Override
	public String getReplaceValue() {
		return value;
	}

}
