package settings;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

/** ���������� ������ � ������� ������� Properties */
public class PropertiesSettings extends Settings{
	private String pathToFile;
	private Properties properties;
	
	/** ������ ��� ���������� ������ �� ��������� .properties ����� 
	 * @param ���� � ����� 
	 * */
	public PropertiesSettings(String pathToFile){
		this.pathToFile=pathToFile;
		properties=new Properties();
		try{
			File f=new File(pathToFile);
			if(f.exists()){
				properties.load(new FileInputStream(f));
			}
		}catch(Exception ex){
			System.out.println("Load error:"+ex.getMessage());
		}
	}
	
	@Override
	public boolean commitChange() {
		try{
			this.properties.store(new FileOutputStream(this.pathToFile), "");
			return true;
		}catch(Exception ex){
			return false;
		}
	}

	@Override
	protected String getStringFromStorage(String path, String defaultValue) {
		return this.properties.getProperty(path);
	}

	@Override
	protected void setStringIntoStorage(String path, String value) {
		this.properties.setProperty(path, value);
	}

}
