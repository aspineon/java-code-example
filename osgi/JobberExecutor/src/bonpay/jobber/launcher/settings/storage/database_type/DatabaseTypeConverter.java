package bonpay.jobber.launcher.settings.storage.database_type;

/** ������� ��� ��� �������������� �� ������� � ������ � ������� */
public abstract class DatabaseTypeConverter {
	/** ��� ���� */
	private String type;

	public DatabaseTypeConverter(String type){
		this.type=type;
	}
	
	/** �������� ��� ������ � ���� ������ */
	public String getType(){
		return type;
	}
	
	/** �������� ��������� ������������� ����, ���� ������ ����� ��������� � ������� ���� */
	public abstract String getTypeFromObject(Object object);
	
	/** ��������� ��� �� ������������ */
	public boolean typeEquals(String controlType){
		boolean returnValue=false;
		try{
			String tempType=controlType.trim().substring(0,1);
			String currentType=type.trim().substring(0,1);
			if(tempType.equalsIgnoreCase(currentType)){
				returnValue=true;
			}else{
				returnValue=false;
			}
		}catch(Exception ex){
			System.err.println("DatabaseTypeConverter#typeEquals Exception: "+ex.getMessage());
		}
		return returnValue;
	}
	
	/** �������� ������ �� ������ */
	public abstract Object getObjectFromString(String value) throws Exception;
	
	/** �������� ������ �� �������  */
	public abstract String getStringFromObject(Object value) throws Exception;
}
