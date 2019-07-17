import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import database.*;
import database.wrap.*;

/*
 * ������:
 *  get, createCriteria (.setCacheable(defalut=false)), CreateQuery - �������� ������ ������ POJO
 *  load � createCriteria (.setCacheable(true)) - �������� ���������� POJO � ��������� ���������� ( ���� �� Final, ������������ ������ � ������ �������� ������� Final  )
 * 		org.hibernate.proxy.HibernateProxy
 *  	javassist.util.proxy.ProxyObject
 *  
 *  ���������� ���������� ���, ������� ������������� �������� ��������, ���� ��� ������ load, 
 *  �� get, createCriteria, CreateQuery ��� �� ������ ���� � ��� �� ������ ( ����������� ���������� HibernateProxy, ProxyObject ) 
 * -------------------------------------
 * ���� �������� ������, � ����� �������� ��� ( � ���� �� ���������� (����� Transaction.begin()..Transaction.commit()) 
 * ��� ����������� �� ��������� �������-�������� ��� �� ������� ���� ��������
 *  ( � ������ ����������� �� ����� ����������� �������� ������, �� ����: ��� ��������� ������ � �������� ����� ���������� )
 * -------------------------------------
 * 	FetchMode ��������� �������� ��������� properties of elements ( EAGER, LAZY ) ��� ����������� ��������
		�� ���� ����� � ������� ������ ����� Properties ���������, � ����� �� ��������� ����� ��� �������
	LockMode
	FlushMode
 */
public class Test {
	private static SimpleDateFormat sdf=new SimpleDateFormat("HH_mm_ss");
	
	public static void main(String[] args){
		System.out.println("begin");
		
		Session session1=Connector.getSession();
		
		// ��������� ������ ���������
		List<Satellite> list1=getSatelliteList(session1);
		// �����������
		printSatelliteList(list1);
		
		// �������� ���� �������
		Session session2=Connector.getSession();
		Satellite satellite=readSatelliteElement(session2, 541);
		satellite.setName("new shop"+sdf.format(new Date()));
		updateElement(session2, satellite);
		session2.close();

		// �����������
		printSatelliteList(list1);
		session1.close();
		
		System.out.println("-end-");
	}
	
	private static List<Satellite> getSatelliteList(Session session){
		return session.createCriteria(Satellite.class).setLockMode(LockMode.WRITE).list();
	}
	
	private static void printSatelliteList(List<Satellite> list){
		System.out.println(" --- list begin --- ");
		if(list!=null)
			for(int counter=0;counter<list.size();counter++){
				System.out.println(String.format("  %d   %s", counter, list.get(counter).toString()));
			}
		System.out.println(" --- list  end  --- ");
	}
	
	private static Satellite readSatelliteElement(Session session, int key){
		return (Satellite)session.load(Satellite.class, key);
	}
	
	/** �������� ������� ��������� ��� � ������� ��������� ������������ */
	private static void readElements(){
		Session session=Connector.getSession();
		session.setCacheMode(CacheMode.IGNORE);
		
		int key=3;
		
		Satellite value=(Satellite)session.load(Satellite.class, key);
		System.out.println("Satellite 1 (load):"+value);// printObject(value);
		
		Satellite anotherValue=(Satellite)session.get(Satellite.class, key);
		System.out.println("Satellite 2 (get):"+anotherValue);// printObject(anotherValue);
		
		Satellite value3=(Satellite)session.createCriteria(Satellite.class).add(Restrictions.eq("id", key)).setCacheable(true).uniqueResult();
		System.out.println("Satellite 3 (criteria):"+value3);// printObject(value3);

		Satellite value4=(Satellite)session.createQuery("select s from Satellite as s where s=:kod").setInteger("kod", key).uniqueResult();
		System.out.println("Satellite 4 (hQuery):"+value4);// printObject(value4);
		
		session.close();
		
		// System.out.println("Satellite 1 (load):"+value.getId());printObject(value);
		System.out.println("Satellite 2 (get):"+anotherValue);// printObject(anotherValue);
		System.out.println("Satellite 3 (criteria):"+value3); // printObject(value3);
	}
	
	/** �������� �������  */
	private static void updateElement(Session sessionOriginal, Satellite satellite){
		try{
			if(sessionOriginal==null){
				Session session=Connector.getSession();
				session.beginTransaction();
				session.saveOrUpdate(satellite);
				session.getTransaction().commit();
				session.close();
				System.out.println("Elements was updated: "+satellite.toString());
			}else{
				sessionOriginal.beginTransaction();
				sessionOriginal.saveOrUpdate(satellite);
				sessionOriginal.getTransaction().commit();
				System.out.println("Elements was updated: "+satellite.toString());
			}
		}catch(Exception ex){
			System.err.println("Test#addElement: "+ex.getMessage());
		}
	}
	
	
	/** ������� � ������� ��� ���������� � ��������� �������  */
	private static void printObject(Object value){
		Class<?>[] interfaces=value.getClass().getInterfaces();
		System.out.println("   ------ Interfaces -------");
		if(interfaces!=null){
			
			for(int counter=0;counter<interfaces.length;counter++){
				System.out.println("   "+counter+"   :   "+interfaces[counter].getName());
			}
		}
		
		Class<?> superclass=value.getClass().getSuperclass();
		System.out.println("   ------ Superclass -------");
		if(superclass!=null){
			System.out.println("   Superclass:"+superclass.getName());
		}
	}
}
