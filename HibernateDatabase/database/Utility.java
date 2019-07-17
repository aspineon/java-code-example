package pay.database;

import java.util.Random;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import pay.database.wrap.Clients;
import pay.database.wrap.Satellite;

public class Utility {

	protected static void debug(String value){
		System.out.print("Utility");
		System.out.print(" DEBUG: ");
		System.out.println(value);
	}
	protected static void error(String value){
		System.out.print("Utility");
		System.out.print(" ERROR: ");
		System.out.println(value);
	}
	
	/** �������� ��������� ���� ������� � ������� Service ��� ��������-�������� (Satellite)<br>
	 * ������������� ����� �������� SATELLITE.KEY_FOR_SERVICE
	 * @param satelliteName - ���������� ��� �������� ( Satellite )
	 * @throws Exception - ���� �� ������� ������ �� ������� �������� 
	 * @return ����, ������� ������ ���� ������� ��� ��������� ���������� � ������ Satellite
	 * */
	public static String getNextAccessKey(String satelliteName) throws Exception {
		Session session=null;
		String key=null;
		boolean returnKey=true;
		try{
			session=Connector.getSession();
			// �������� ������ �� ���������� SatelliteId
			Satellite satellite=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name",satelliteName)).uniqueResult());
			// ������������� ����� ���������� ����
			key=getUniqueChar(10)+Integer.toString(satellite.getId());
			// �������� ���� � ������
			satellite.setKey_for_service(key);
			Transaction transaction=session.beginTransaction();
			session.save(satellite);
			transaction.commit();
			returnKey=true;
			// ������� ���� 
		}catch(Exception ex){
			returnKey=false;
		}finally{
			if(session!=null){
				Connector.closeSession(session);
			}
		}
		if(returnKey==false){
			throw new Exception("getNextAccessKey error in create Next Access Key");
		}else{
			return key;
		}
	}

	
	/** �������� ���� ������� � ������� Service ��� ��������-�������� (Satellite)<br>
	 * SATELLITE.KEY_FOR_SERVICE
	 * @param satelliteName - ���������� ��� �������� ( Satellite )
	 * @throws Exception - ���� �� ������� ������ �� ������� �������� 
	 * @return ����, ������� ������ ���� ������� ��� ��������� ���������� � ������ Satellite
	 * */
	public static String getAccessKey(String satelliteName){
		Session session=null;
		String key=null;
		try{
			session=Connector.getSession();
			// �������� ������ �� ���������� SatelliteId
			Satellite satellite=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name",satelliteName)).uniqueResult());
			// ������������� ����� ���������� ����
			key=satellite.getKey_for_service();
		}catch(Exception ex){
			error("getAccessKey:"+ex.getMessage());
		}finally{
			if(session!=null){
				Connector.closeSession(session);
			}
		}
		return key;
	}
	
	/** ��������� ���������� ����� (KEY_FOR_SERVICE) �������, ������� ������� Satellite 
	 * @param satelliteName ��� Satellite
	 * @param accessKey - ����, ������� ������ ���� �������� � ���� ��� ���� ������� ��� ������� Satellite
	 * */
	public static boolean checkAccesKeyBySatelliteId(String satelliteName, String accessKey){
		Session session=null;
		boolean return_value=false;
		try{
			session=Connector.getSession();
			// �������� ������ �� ���������� SatelliteId
			Satellite satellite=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name",satelliteName)).uniqueResult());
			return_value=satellite.getKey_for_service().equals(accessKey);
		}catch(Exception ex){
			error("checkAccessKeyBySatelliteId Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				Connector.closeSession(session);
			}
		}
		return return_value;
	}
	
	/** ������������� ���������� ���� (��� ���������� � ���� ) ������� ��� Satellite <br>
	 * c ������� (������) ������ ������ ������ �������������� "�����" � ��� �� HTML ������ */
	public static String generateUniqueKeyForSatellite(String satelliteName){
		try{
			return getUniqueChar(10)+Integer.toString(getSatelliteIdFromSatelliteName(satelliteName));
		}catch(Exception ex){
			return "";
		}
	}
	
	
	/** ������� ���������� ��� ��� ������� - ������ � ������� CLIENTS 
	 * @param satelliteName - ���������� ��� �������� (Satellite)
	 * @param unique_key_satellite - ���������� ���� ��� ������� ������� � �������� Satellite 
	 * @param unique_key_service - ���������� ���� ��� ������� ������� � �������� Service 
	 * @param returnPath - ����, �� �������� ����� ��������� ������� ������� ��� �������� ��� �� ������ Satellite
	 * @return true - ���� ���������� ������ ������ �������
	 */
	public static boolean createClients(String satelliteName,
									    String unique_key_satellite,
									    String unique_key_service,
									    String returnPath
									    ){
		boolean return_value=false;
		Session session=null;
		try{
			session=Connector.getSession();
			Transaction transaction=session.beginTransaction();
			// create Clients 
			int satelliteId=getSatelliteIdFromSatelliteName(satelliteName);
			Clients newClients=new Clients();
			newClients.setId_client_state(0);
			newClients.setId_satellite(satelliteId);
			newClients.setReturn_url(returnPath);
			newClients.setUnique_key_satellite(unique_key_satellite);
			newClients.setUnique_key_service(unique_key_service);
			session.save(newClients);
			transaction.commit();
			return_value=true;
		}catch(Exception ex){
			error("createClients Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				Connector.closeSession(session);
			}
		}
		return return_value;	
	}
	
	/** �������� ���������� ��� � �������� ���� ������(�������) Satellite */
	public static int getSatelliteIdFromSatelliteName(String satelliteName) throws Exception {
		int return_value=(-1);
		Session session=null;
		try{
			session=Connector.getSession();
			return_value=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name", satelliteName)).uniqueResult()).getId();
		}catch(Exception ex){
			error("getSatelliteIdFromSatelliteName is not finded");
		}finally{
			if(session!=null){
				Connector.closeSession(session);
			}
		}
		// return result
		if(return_value==(-1)){
			throw new Exception("Satellite Id is not finded: "+satelliteName);
		}else{
			return return_value;
		}
	}
	
	public final static String hexChars[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	/** ������������� ��������� ������������������ �� Hex �����, ��������� ������ 
	 * @param count - ������ ��������� ������������������, ������� ���������� �������� 
	 * */
	public static String getUniqueChar(int count){
        StringBuffer return_value=new StringBuffer();
        Random random=new java.util.Random();
        int temp_value;
        for(int counter=0;counter<count;counter++){
            temp_value=random.nextInt(hexChars.length);
            return_value.append(hexChars[temp_value]);
        }
        return return_value.toString();
    }
	
}
