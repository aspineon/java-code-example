package database.serializer.unpack;

import java.sql.Connection;
import java.util.ArrayList;

import database.serializer.common.Answer;
import database.serializer.common.RecordWrap;
import database.serializer.unpack.interceptors.Interceptor;

/** �����, ������� ������ ��� ���������� ���������� ������ �� ������� ���������, � ��������� � ���� ������ */
public class UnpackRecordFactory {
	/** ������ ������������� */
	private ArrayList<Interceptor> listOfInterceptors=new ArrayList<Interceptor>();
	
	public UnpackRecordFactory(Interceptor ... interceptors ){
		for(int counter=0;counter<interceptors.length;counter++){
			listOfInterceptors.add(interceptors[counter]);
		}
	}

	/** �������� ��� ���� ����������� � ������ ������������� */
	public void addInterceptor(Interceptor interceptor){
		this.listOfInterceptors.add(interceptor);
	}
	
	/** ������� ����������� �� ������ ������������� */
	public void removeInterceptor(Interceptor interceptor){
		this.listOfInterceptors.remove(interceptor);
	}
	
	/** ������� ��� ������������ */
	public void removeAllInterceptors(){
		this.listOfInterceptors.clear();
	}
	
	/** �������� ���-�� ���� ������������� � ������� */
	public int getInterceptorCount(){
		return this.listOfInterceptors.size();
	}
	
	/** ���������� ���������� ����� ������ � �������� ������ �� ������� �� ���
	 * <b>!!! ����� �� �������������� Connection.commit()</b>
	 * @param records - ������, ������� ����� ����������
	 * @return - ������ �� �������� ({@link Answer#OK}, {@link Answer#ERROR}, {@link Answer#CANCEL}), ������� ������� �� ������������ ������ 
	 */
	public int[] processPackage(RecordWrap[] records, Connection connection){
		if(records!=null){
			int[] returnValue=new int[records.length];
			// ��������� ��� ���������� ������ � �������� 
			for(int recordCounter=0;recordCounter<records.length;recordCounter++){
				try{
					boolean processOk=false;
					// ��������� ��� ������������������ ������������ ��� ������� ����������� ������ 
					for(int counter=0;counter<this.listOfInterceptors.size();counter++){
						// �������� �� ������ ����� ��� ������������ ?
						if(this.listOfInterceptors.get(counter).isValidRecord(records[recordCounter])){
							// ������� ��������� ������
							processOk=this.listOfInterceptors.get(counter)
							                                 .processRecord(records[recordCounter],connection);
							break;
						}
					}
					// ��������, ��� �� ��������� ������ ����� 
					if(processOk==false){
						// ���, ����� �� ��� ��������� 
						returnValue[recordCounter]=Answer.CANCEL;
					}else{
						// ��, ����� ��� ��������� 
						returnValue[recordCounter]=Answer.OK;
					}
				}catch(Exception ex){
					System.err.println("UnpackRecordFactory#processPackage Exception: "+ex.getMessage());
					returnValue[recordCounter]=Answer.ERROR;
				}
			};
			return returnValue;
		}else{
			return null;
		}
	}
}
