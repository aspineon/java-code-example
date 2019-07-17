package bonpay.osgi.scan_bundles;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/** ������, ������� ��������� ������� �� ��������� ���������-������������ Bundle � �������������� �������������/������������� Bundle */
public class FileScaner extends Thread{
	/** ����, �� �������� ���������� ������������ ������ �� ������� ����� ��� �� �������� ������������� */
	private String pathForScan;
	/** �������� OSGi � ������� ���������� ������� */
	private BundleContext context;
	/** ����� ��������� ������, ����� ��������� ������� ������ */
	private long delayScan;
	/** ��������� ����������� ������ */
	private String separator=System.getProperty("file.separator");
	/** ������� � ������� ����� ������������� ���������� ����� �������� */
	private File directory;
	/** ������� ������ ������, �� ������� ��������� Bundle � ����������������� */
	private ArrayList<String> listOfFileName=new ArrayList<String>();
	/** ��������������� ������ ������ ������ Bundles */
	private ArrayList<Bundle> listOfBundle=new ArrayList<Bundle>();
	/** ������ */
	private Logger logger=Logger.getLogger(this.getClass());
	
	private void debug(Object value){
		logger.debug(value);
		// System.out.println("Debug FileScaner#"+value);
	}

	private void error(Object value){
		logger.error(value);
		// System.out.println("Error FileScaner#"+value);
	}
	
	public FileScaner(String pathForScan, BundleContext context, long delayScan){
		this.pathForScan=pathForScan.trim();
		this.delayScan=delayScan;
		if(this.pathForScan.endsWith(separator)){
			// path is ends for separator
			this.pathForScan=this.pathForScan.substring(0,this.pathForScan.length()-separator.length());
		}
		this.directory=new File(this.pathForScan);
		if(this.directory.isDirectory()==false){
			error("Path is not directory:"+this.pathForScan);
			this.directory=null;
		}
		this.context=context;
		this.start();
	}
	
	@Override
	public void run(){
		while(this.isInterrupted()==false){
			if(this.directory!=null){
				// ������������ ���� ������ � ��������
				File[] files=this.directory.listFiles();
				// ����� ��� ����� �����
				for(int counter=0;counter<files.length;counter++){
					if(files[counter].isFile()){
						String fileName=files[counter].getName();
						// String fileNameAbsolute=files[counter].getAbsolutePath();
						if(listOfFileName.indexOf(fileName)<0){
							// ���� ����� - ���������� ���������� 
							String pathToBundle=this.pathForScan+this.separator+fileName;
							try{
								debug("Attempt to install bundle: "+pathToBundle);
								//Bundle bundle=this.context.installBundle("file: "+fileNameAbsolute);
								// INFO not working : this.context.installBundle(fileName);this.context.installBundle("file: "+fileName);
								Bundle bundle=this.context.installBundle(fileName,new FileInputStream(pathToBundle));
								this.listOfBundle.add(bundle);
								this.listOfFileName.add(fileName);
								debug("Attempt to start bundle: "+pathToBundle);
								bundle.start();
								debug("start ok:"+bundle.getState());
							}catch(Exception ex){
								error("Bundle exception: "+ex.getMessage());
								ex.printStackTrace();
							}
						}
					}else{
						// file is directory 
					}
				}
				// ����� ��� ��������� �����
				int counter=this.listOfBundle.size()-1;
				while(counter>=0){
					// ���������������� ���������
					if(this.getPositionIntoArray(files, this.listOfFileName.get(counter))<0){
						// ���� ������ - ����������������
						try{
							this.listOfBundle.get(counter).uninstall();
							this.listOfBundle.remove(counter);
							String fileRemove=this.listOfFileName.remove(counter);
							debug("Bundle is Uninstalled: "+fileRemove);
						}catch(Exception ex){
							error("Uninstall files Exception: "+ex.getMessage());
						}
					}
					counter--;
				}
					
			}else{
				error(" FileScanner Error: ");
			}
			try{
				Thread.sleep(this.delayScan);
			}catch(InterruptedException ie){};
		}
	}
	
	/** �������� ������� �������� � ������� �� ����� ����� 
	 * @param files - �����, � ������ ������� ����� ��������� ����� 
	 * @param findName - ������� ��� �����, ������� ����� ����� � files 
	 * @return ������� ��������, ���� �� -1
	 */
	private int getPositionIntoArray(File[] files, String findName){
		int returnValue=(-1);
		for(int counter=0;counter<files.length;counter++){
			if(files[counter].getName().equals(findName)){
				returnValue=counter;
				break;
			}
		}
		return returnValue;
	}
}
