package settings;
import java.awt.Color;

public abstract class Settings{
	
	public void setInteger(String path, Integer value){
		this.setStringIntoStorage(this.convertUserPathToStoragePath(path), value.toString());
	}
	public void setDouble(String path, Double value){
		this.setStringIntoStorage(this.convertUserPathToStoragePath(path), value.toString());
	}

	public void setString(String path, String value){
		this.setStringIntoStorage(this.convertUserPathToStoragePath(path), value);
	}
	
	
	public Integer getInteger(String path, Integer defaultValue){
		String value=this.getStringFromStorage(this.convertUserPathToStoragePath(path), null);
		if(value==null){
			return defaultValue;
		}else{
			return new Integer(value);
		}
	}
	
	public Double getDouble(String path, Double defaultValue){
		String value=this.getStringFromStorage(this.convertUserPathToStoragePath(path), null);
		if(value==null){
			return defaultValue;
		}else{
			return new Double(value);
		}
		
	}
	public String getString(String path, String defaultValue){
		return this.getStringFromStorage(this.convertUserPathToStoragePath(path), defaultValue);
	}
	
	/** ������������� ���������������� ���� � ������������� ���� ��������� 
	 * @param value - ������ ������� ����� ���� ���������� ����:
	 *  "//parent/child" - XPath ������ <br>
	 *  "parent.child"
	 * */
	protected String convertUserPathToStoragePath(String path){
		path=path.replaceAll("/", ".");
		while(path.startsWith(".")==true){
			path=path.substring(1);
		}
		return path;
	}
	
	
	/** ��������� ���� 
	 * @param path - ���� 
	 * @param color - ����, ������� ����� ��������� 
	 */
	public void setColor(String path, Color color){
		this.setStringIntoStorage(this.convertUserPathToStoragePath(path), getStringValueFromColor(color));
	}
	
	/** ��������� ���� 
	 * @param path ���� � ����������
	 * @param defaultColor �������� �� ���������, ���� �� ������ ���� �� ���������� ���� 
	 * */
	public Color getColor(String path, Color defaultColor){
		Color returnValue=defaultColor;
		try{
			returnValue=Color.decode(getStringFromStorage(this.convertUserPathToStoragePath(path),null));
		}catch(Exception ex){
		}
		return returnValue;
	}

	/** �������� ��������� �������� �� Color 
	 * @param color - ����, ������� ����� ��������� 
	 * */
	private String getStringValueFromColor(Color color){
		return "0x"+Integer.toHexString((color.getRGB() & 0xffffff) | 0x1000000).substring(1); 
	}
	
	
	/** ��������� ������ �� ���������� ���� 
	 * @param path ����, �� �������� ����� �������� ������ ������ (Example:   "root.parent.value" )
	 * @param value ��������, ������� ���������� ��������� ��� ������� �������
	 * */
	protected abstract void setStringIntoStorage(String path, String value);

	
	/** �������� ������ �� ���������� ���� 
	 * @param path ����, �� �������� ����� ����������� ������ ������� (Example:   "root.parent.value" )
	 * @param defaultValue ��������, ������� ����� ���������� � ������ �� ���������� ���������� ���� 
	 * */
	protected abstract String getStringFromStorage(String path, String defaultValue);
	
	/** ������������� ��� ��������� ��������� */
	public abstract boolean commitChange();
	
}
