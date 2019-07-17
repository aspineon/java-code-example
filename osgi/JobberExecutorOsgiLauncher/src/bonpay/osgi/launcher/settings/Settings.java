package bonpay.osgi.launcher.settings;

import bonpay.osgi.launcher.settings.storage.IStorage;

/** ���������  */
public class Settings {
	/** ����� ���������� ������/������ ������ */
	private IStorage storage;
	
	public Settings(IStorage storage){
		this.storage=storage;
		storage.loadAllRecords();
	}
	
	/** ���������� �������� */
	public void setInteger(String parameterName, Integer value){
		this.storage.putRecord(parameterName, value);
		this.storage.saveAllRecords();
	}

	/** ���������� �������� */
	public void setString(String parameterName, String value){
		this.storage.putRecord(parameterName, value);
		this.storage.saveAllRecords();
	}

	/** ���������� �������� */
	public void setFloat(String parameterName, Float value){
		this.storage.putRecord(parameterName, value);
		this.storage.saveAllRecords();
	}
	
	/** �������� �������� ��� Integer
	 * @param parameterName - ��� ��������� 
	 * @return null, ���� ������ �� ������ ��� ��� �� �������������  
	 */
	public Integer getInteger(String parameterName){
		try{
			return (Integer)this.storage.getRecord(parameterName);
		}catch(Exception ex){
			return null;
		}
	}
	
	/** �������� �������� ��� Float
	 * @param parameterName - ��� ��������� 
	 * @return null, ���� ������ �� ������ ��� ��� �� �������������  
	 */
	public Float getFloat(String parameterName){
		try{
			return (Float)this.storage.getRecord(parameterName);
		}catch(Exception ex){
			return null;
		}
	}
	
	/** �������� �������� ��� String
	 * @param parameterName - ��� ��������� 
	 * @return null, ���� ������ �� ������ ��� ��� �� �������������  
	 */
	public String getString(String parameterName){
		try{
			return (String)this.storage.getRecord(parameterName);
		}catch(Exception ex){
			return null;
		}
	}
	
}
