package bonpay.osgi.launcher;

import org.apache.log4j.Logger;

import bonpay.osgi.launcher.executor.IExecutorAware;
import bonpay.osgi.launcher.settings.ISettingsAware;
import bonpay.osgi.launcher.settings.Settings;
import bonpay.osgi.launcher.settings.SettingsEnum;
import bonpay.osgi.service.interf.IExecutor;


/** ������, ������� ��������� �� ���������� �������  */
public class Launcher extends Thread implements ISettingsChangeNotify{
	private Logger logger=Logger.getLogger(this.getClass());
	/** ������, ������� ������� {@link Settings}*/
	private ISettingsAware settingsAware;
	/** ������, ������� ������� � ��� ��� ��������� ��������� � ���������� ��� ������ */
	private Boolean flagSettingsChange=false;
	/** ����� �������� ����� ��������� ������� � ������������ */
	private int timeWait;
	
	/** ������, ������� ������ ������������� ��� ������� */
	private IExecutorAware executorAware;
	
	/** ������, ������� ��������� �������
	 * @param settingsAware - ��������� ��� {@link Settings}
	 * @param executorAware - ��������� ��� 
	 */
	public Launcher(ISettingsAware settingsAware, IExecutorAware executorAware){
		logger.debug("start Launcher");
		this.settingsAware=settingsAware;
		this.executorAware=executorAware;
	}
	
	
	private boolean flagRun=false;
	/** ������ �� ���������� ���������� ������ */
	public void stopThread(){
		this.flagRun=false;
	}
	
	public void run(){
		this.flagSettingsChange=true;
		this.flagRun=true;
		while(this.flagRun){
			logger.debug("��������� �� ���������� ���������� ");
			if(this.flagSettingsChange==true){
				this.updateSettings();
			}
			logger.debug("�������� ������ ��������, ������� ����� ���������"); 
			IExecutor[] executors=this.executorAware.getExecutors();
			if((executors!=null)&&(executors.length>0)){
				// ������ �������� 
				for(int index=0;index<executors.length;index++){
					if(executors[index]!=null){
						logger.info("execute.begin "+(index+1)+"/"+executors.length);
						executors[index].execute();
						logger.info("execute.end "+(index+1)+"/"+executors.length);
					}else{
						logger.info("executors[index] is null");
					}
				}
			}else{
				// ��� executors ��� ����������
				logger.debug("no executors for execute");
			}
			try{
				Thread.sleep(this.timeWait);
			}catch(Exception ex){};
		}
	}
	
	private void updateSettings(){
		synchronized(this.flagSettingsChange){
			// INFO ����� �������� �������� ��� Launcher
			Settings settings=this.settingsAware.getSettings();
			this.timeWait=settings.getInteger(SettingsEnum.time_wait.toString());
			this.flagSettingsChange=false;
		}
	}

	@Override
	public void notifySettingsChanged() {
		synchronized(this.flagSettingsChange){
			this.flagSettingsChange=true;
		}
	}
}
