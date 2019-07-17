package logger.utility;
import java.io.FileInputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import logger.utility.condition.DateChanger;
import logger.utility.condition.TimerCondition;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;


/** �����, ������� ���������� ����, �������� ����� ��������� */
public class LoggerActivator {
	private String defaultTimeStamp="yyyy_MM_dd_HH_mm_ss";
	/** ���� ��� ��������� ���������� ���� ���� ������ */
	String propertiesLevel="logger.level";
	/** ���� ��� ��������� ������ ������� */
	String propertiesPattern="logger.pattern";
	/** ���� ��� ��������� ���� � ����� */
	String propertiesFile="logger.file";
	/** ���� ��� ��������� ���� � ����� */
	String propertiesFileMaxSize="logger.file.max_size";
	/** ���������� ������������ */
	String propertiesFileMaxIndex="logger.file.max_index";
	/** ����� ������� */
	String propertiesFileTimeStamp="logger.file.time_stamp";
	
	private LoggerSettings settings=new LoggerSettings();

	/** �����, ������� ���������� ����, �������� ����� ��������� 
	 * @param logPackage - �����, �� �������� ����� ������� ��� (null - RootLog)
	 * @param pathToFile - ���� � �����, ���� ������� ���������� ���� 
	 * @param delay - ����� �������� ����� ��������� ���������� ����� ����� (5000)
	 */
	public LoggerActivator(String logPackage, 
			   			   String pathToFile,
			   			   final int delay){
		readSettingsFromFile(logPackage, pathToFile);
		this.refreshSettings();
		new LoggerWatcher(this,new TimerCondition(delay),1000).start();
	}

	/** �����, ������� ���������� ����, �������� ����� ��������� 
	 * @param logPackage - �����, �� �������� ����� ������� ��� (null - RootLog)
	 * @param pathToFile - ���� � �����, ���� ������� ���������� ���� 
	 * @param delay - ����� �������� ����� ��������� ���������� ����� ����� (5000)
	 */
	public LoggerActivator(String logPackage, 
			   			   String pathToFile){
		readSettingsFromFile(logPackage, pathToFile);
		this.refreshSettings();
		new LoggerWatcher(this,new DateChanger(),60000).start();
	}
	/** 
	 * ������, ������� ��������� ���� ��� ���������� ������ �������� ������, ������� ��������� � �����
	 * @param logPackage - �����, �� �������� ��������������� ������������ (null - RootLogger )
	 * @param pathToFile - properties ����, ������� �������� ��� ����������� ���������� �� ����� 
	 * */
	public void readSettingsFromFile(String logPackage, String pathToFile){
		// 
		Properties properties=new Properties();
		try{
			this.settings=new LoggerSettings();
			properties.load(new FileInputStream(pathToFile));
			// �������� ������, ������� ����� ����������������
			if((logPackage==null)||(logPackage.trim().equals(""))){
				this.settings.setLogger(Logger.getRootLogger());
			}else{
				this.settings.setLogger(Logger.getLogger(logPackage));
			}
			// �������� ������� ����
			this.settings.setLevel(this.getLevel(properties.getProperty(propertiesLevel)));
			
			// �������� ������ ���� 
			String pattern=PatternLayout.DEFAULT_CONVERSION_PATTERN;
			if(properties.getProperty(this.propertiesPattern)!=null){
				pattern=properties.getProperty(this.propertiesPattern);
			}
			this.settings.setPattern(pattern);
			
			// �������� ��� �����
			String logFileName=properties.getProperty(propertiesFile);
			if(   (logFileName==null)
					||(logFileName.trim().equalsIgnoreCase("empty"))
					||(logFileName.trim().equalsIgnoreCase("null"))
					||(logFileName.trim().equalsIgnoreCase(""))){
					logFileName=null;
				
			};
			this.settings.setFileName(logFileName);
			
			// �������� ������������ ������ �����, ���� ��� ������  
			try{
				this.settings.setMaxFileSize("");
				this.settings.setMaxFileSize(properties.getProperty(this.propertiesFileMaxSize));
			}catch(Exception ex){};
			// �������� ������������ ���-�� ������
			try{
				this.settings.setMaxBackupIndex(0);
				Integer maxFileSize=Integer.parseInt(properties.getProperty(this.propertiesFileMaxIndex));
				if(maxFileSize>0){
					this.settings.setMaxBackupIndex(maxFileSize);
				}
			}catch(Exception ex){};
			// �������� ����� ������� ��� ������ ������ 
			String timeStamp=properties.getProperty(propertiesFileTimeStamp);
			if((timeStamp==null)||(timeStamp.trim().equals(""))){
				timeStamp="yyyy_MM_dd";
			}
			this.settings.setTimeStamp(timeStamp);
			 
			// ���� ���� �� ������
			// logger.addAppender(new ConsoleAppender(new PatternLayout(logPattern)));

			
			/*try{
				//DailyRollingFileAppender fileAppender=new DailyRollingFileAppender(new PatternLayout(logPattern), logFileName, timeStamp);
				RollingFileAppender fileAppender=new RollingFileAppender(new PatternLayout(logPattern), 
																		logFileName);
				logger.addAppender(fileAppender);
			}catch(Exception ex){
				logger.addAppender(new ConsoleAppender(new PatternLayout(logPattern)));
				System.err.println("LoggerActivator logger settings Exception: "+ex.getMessage()+"  file: "+logFileName + " logger put to Console");
			}*/
		}catch(Exception ex){
			System.err.println("�� ������� ��������� ���� - ��������� �� ��������� [ERROR, Console, %m%n]");
			this.settings=new LoggerSettings();
			this.settings.setFileName(null);
			this.settings.setLevel(Level.ERROR);
			if((logPackage==null)||(logPackage.trim().equals(""))){
				this.settings.setLogger(Logger.getRootLogger());
			}else{
				this.settings.setLogger(Logger.getLogger(logPackage));
			}
			this.settings.setPattern(PatternLayout.DEFAULT_CONVERSION_PATTERN);
		};
	}
	
	public void refreshSettings(){
		LogManager.resetConfiguration();
		
		this.settings.getLogger().setAdditivity(false);
		this.settings.getLogger().setLevel(this.settings.getLevel());
		if(this.settings.getFileName()!=null){
			// this.settings.getLogger().removeAllAppenders(); because resetConfiguration();
			String fileName=this.generateFileName(this.settings.getFileName(), this.settings.getTimeStamp());
			try{
				RollingFileAppender fileAppender=new RollingFileAppender(new PatternLayout(this.settings.getPattern()), 
																						   fileName
																		 );
				if(this.settings.getMaxBackupIndex()>0){
					fileAppender.setMaxBackupIndex(this.settings.getMaxBackupIndex());
				}else{
					fileAppender.setMaxBackupIndex(-2);
				}
				if(this.settings.getMaxFileSize()!=null){
					fileAppender.setMaxFileSize(this.settings.getMaxFileSize());
				}
				this.settings.getLogger().addAppender(fileAppender);
			}catch(Exception ex){
				System.err.println("�� ������� ���������������� ��� ");
				this.settings.getLogger().addAppender(new ConsoleAppender(new PatternLayout(this.settings.getPattern())));
			}
		}else{
			// this.settings.getLogger().removeAllAppenders(); because resetConfiguration();
			this.settings.getLogger().addAppender(new ConsoleAppender(new PatternLayout(this.settings.getPattern())));
		}
	}
	
	/** ������������� ����� ����  */
	private String generateFileName(String fileName, String timeStamp){
		// �������� ��� ����� � ��� ����������
		int dotPosition=fileName.lastIndexOf(".");
		if(dotPosition>0){
			String name=fileName.substring(0,dotPosition);
			String extension="";
			if(dotPosition<(fileName.length()-1)){
				extension=fileName.substring(dotPosition+1);
			}
			try{
				SimpleDateFormat sdf=new SimpleDateFormat(timeStamp);
				return name+sdf.format(new Date())+"."+extension;
			}catch(Exception ex){
				System.err.println(" Error create file Pattern ");
				ex.printStackTrace(System.err);
				SimpleDateFormat sdf=new SimpleDateFormat(this.defaultTimeStamp);
				return name+sdf.format(new Date())+"."+extension;
			}
		}else{
			try{
				SimpleDateFormat sdf=new SimpleDateFormat(timeStamp);
				return fileName+sdf.format(new Date());
			}catch(Exception ex){
				System.err.println(" Error create file Pattern ");
				ex.printStackTrace(System.err);
				SimpleDateFormat sdf=new SimpleDateFormat(this.defaultTimeStamp);
				return fileName+sdf.format(new Date());
			}
		}
	}
	
	/** �������� ������� ����, ������� ����� ���������� */
	private Level getLevel(String levelString){
		Level level=Level.DEBUG;
		if(levelString==null){
			level=Level.DEBUG;
		}else if(levelString.equalsIgnoreCase("debug")){
			level=Level.DEBUG;
		}else if(levelString.equalsIgnoreCase("info")){
			level=Level.INFO;			
		}else if(levelString.equalsIgnoreCase("warn")){
			level=Level.WARN;			
		}else if(levelString.equalsIgnoreCase("error")){
			level=Level.ERROR;			
		}else if(levelString.equalsIgnoreCase("fatal")){
			level=Level.FATAL;			
		}else if(levelString.equalsIgnoreCase("all")){
			level=Level.ALL;			
		}else if(levelString.equalsIgnoreCase("off")){
			level=Level.OFF;
		}
		return level;
	}
}


