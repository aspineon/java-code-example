package bonpay.jobber.launcher;

import org.apache.log4j.Logger;

import bonpay.jobber.launcher.executor.IExecutor;
import bonpay.jobber.launcher.executor.IExecutorAware;
import bonpay.jobber.launcher.settings.ISettingsAware;
import bonpay.jobber.launcher.settings.Settings;
import bonpay.jobber.launcher.settings.SettingsEnum;

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
	
	public void run(){
		this.flagSettingsChange=true;
		while(true){
			// ��������� �� ���������� ���������� 
			if(this.flagSettingsChange==true){
				this.updateSettings();
			}
			// �������� ������ ��������, ������� ����� ��������� 
			IExecutor[] executors=this.executorAware.getExecutors();
			if((executors!=null)&&(executors.length>0)){
				// ������ �������� 
				for(int index=0;index<executors.length;index++){
					if(executors[index]!=null){
						executors[index].execute();
					}
				}
			}else{
				// ��� executors ��� ����������
				// logger.debug("no executors for execute");
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
