package reader;

import java.util.ArrayList;

/** ������, ������� ��������� �������� ������������������, � ���������� � ��� ����� ������ �� ��������� � ����������, �������� � ���������� */
public class ReaderAsync implements IInputDataListener, Runnable{
	/** ��������� ����� */
	private byte[] preambule;
	/** ���������� ����� */
	private byte[] postambule;
	
	
	/** ��������� ��������� ������  */
	private ArrayList<IOutputBlockListener> list=new ArrayList<IOutputBlockListener>();
	
	/** �������� ��������� �������� ��������� ������ */
	public void addOutputBlockListener(IOutputBlockListener listener){
		this.list.add(listener);
	}
	
	/** ������� ��������� �������� ��������� ������ */
	public void removeOutputBlockListener(IOutputBlockListener listener){
		this.list.remove(listener);
	}
	
	/** ���������� � ����� ����� ������ ���� ������������������ ���������� */
	private void notifyAboutNewBlock(byte[] array){
		for(IOutputBlockListener listener: list){
			listener.notifyBlock(array);
		}
	}
	
	/** ������� ������� */
	private byte[] addArray(byte[] ... values){
		int size=0;
		for(int counter=0;counter<values.length;counter++){
			if(values!=null){
				size+=values[counter].length;
			}
		}
		byte[] result=new byte[size];
		
		int index=0;
		for(int counter=0;counter<values.length;counter++){
			if(values[counter]!=null){
				for(int counterInner=0;counterInner<values[counter].length;counterInner++){
					result[index]=values[counter][counterInner];
					index++;
				}
			}
		}
		return result;
	}
	
	/** 
	 * @param array - ������, �� �������� ����� ������� ����� 
	 * @param indexBegin - ������ ������ 
	 * @param indexEnd - ������ ��������� 
	 * @return <b>[</b> indexBegin, indexEnd <b>)</b>
	 */
	private byte[] subArray(byte[] array, int indexBegin, int indexEnd){
		byte[] returnValue=new byte[indexEnd-indexBegin];
		for(int counter=indexBegin;counter<indexEnd;counter++){
			returnValue[counter-indexBegin]=array[counter];
		}
		return returnValue;
	}
	
	/** ������, ������� ��������� �������� ������������������, � ���������� � ��� ����� ������ �� ��������� � ����������, �������� � ���������� 
	 * @param preambule - ��������� �����
	 * @param postambule - ���������� �����
	 * ����������� ����� ������� ����� {@link #startReader()}
	 */
	public ReaderAsync(byte[] preambule, byte[] postambule){
		this.preambule=preambule;
		this.postambule=postambule;
		
	}
	
	private boolean flagRun=false;
	
	/** ��������� ����� */
	public void startReader(){
		this.flagRun=true;
		(new Thread(this)).start();
	}
	
	/** ���������� ����� */
	public void stopReader(){
		synchronized(this.signalFlag){
			this.signalFlag.notify();
		}
		this.flagRun=false;
	}

	/** ������� ������ ������, ������� �������� ��� ���������� ������������������ */
	private byte[] mainData=new byte[]{};
	
	/** ������, ������� ������� ��������� � ������������� ������� ��������� ������ ������ */
	private Object signalFlag=new Object();
	
	
	@Override
	public void inputData(byte[] data) {
		synchronized(this.signalFlag){
			mainData=this.addArray(mainData,data);
			this.signalFlag.notify();
		}
	}

	private int indexOfPreambule;
	@Override
	public void run() {
		while(this.flagRun){
			synchronized(this.signalFlag){
				indexOfPreambule=this.indexArrayInArray(this.mainData, this.preambule);
				if(indexOfPreambule<0){
					try{
						this.signalFlag.wait();
					}catch(Exception ex){};
				}
			}
			// ��������� ������ - ����������
			while(true){
				// int indexOfPreambule=this.indexArrayInArray(this.mainData, this.preambule);
				if(indexOfPreambule>=0){
					int indexOfPostambule=this.indexArrayInArray(this.mainData, this.postambule);
					if(indexOfPostambule>=0){
						byte[] body=null;
						synchronized(this.mainData){
							body=this.subArray(this.mainData, indexOfPreambule, indexOfPostambule+this.preambule.length);
							this.mainData=this.subArray(this.mainData, indexOfPostambule+this.preambule.length, this.mainData.length);
						}
						if((body!=null)&&(body.length>0)){
							this.notifyAboutNewBlock(body);
						}
					}else{
						break;
					}
				}else{
					break;
				}
			}
		}
	}

	/** �������� ������ ��������� ������ ������� � ������ 
	 * @param source - ��������, � ������� ���������� ����� 
	 * @param findPart - ������������������, ������� ������ ���� ������� 
	 * @return -1, ���� ������������������ �� �������
	 */
	private int indexArrayInArray(byte[] source, byte[] findPart){
		int returnValue=(-1);
		if((findPart!=null)&&(source!=null)&&(source.length>=findPart.length)){
			for(int counter=0;counter<source.length-findPart.length+1;counter++){
				if(source[counter]==findPart[0]){
					returnValue=counter;
					for(int innerCounter=0;innerCounter<findPart.length;innerCounter++){
						if(findPart[innerCounter]!=source[counter+innerCounter]){
							returnValue=(-1);
							break;
						}
					}
					if(returnValue==counter){
						return returnValue;
					}
				}
			}
		}
		return returnValue;
	}
}
