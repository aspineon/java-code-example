import org.apache.commons.pool.BaseObjectPool;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;


/** ������������ ���������� Pool-a ��������,
 �������� ���������:
  public interface PoolableObjectFactory {
    Object makeObject();
    void activateObject(Object obj);
    void passivateObject(Object obj);
    boolean validateObject(Object obj);
    void destroyObject(Object obj);
}

public interface KeyedPoolableObjectFactory {
    Object makeObject(Object key);
    void activateObject(Object key, Object obj);
    void passivateObject(Object key, Object obj);
    boolean validateObject(Object key, Object obj);
    void destroyObject(Object key, Object obj);
}

	���������� ������ ��� ����������:
	BaseObjectPool (implements PoolableObjectFactory)
		-������� ������� �������, ������� ����� �������� � Pool
		 
	����, ������� ����� ������������ �������, ����������� ��������� PoolableObjectFactory
	StackObjectPool
	StackKeyedObjectPool
	
	GenericObjectPool
	GenericKeyedObjectPool
	
	SoftReferenceObjectPool

 * */
public class ApachePool_Example {
	
	public static void main(String[] args){
		/** ������� Pool �� ����������� �������� */
		StackObjectPool pool=new StackObjectPool(new PoolFactory());
		for(int counter=0;counter<10;counter++){
			try{
				System.out.println(counter+" "+pool.borrowObject().toString());
			}catch(Exception ex){
				System.out.println("Borrow object Exception:"+ex.getMessage());
			}
		}
		
		/** ������� Pool �� ������, ������� ������ ���� ������ ��������� ���-���, �������� ��������, �������� �����... */
		GenericObjectPool genericPool=new GenericObjectPool(new PoolFactory(),5);
		ObjectInPool[] queue=new ObjectInPool[10];
		for(int counter=0;counter<10;counter++){
			try{
				/** "��������" ������ �� Pool-a*/
				queue[counter]=(ObjectInPool)genericPool.borrowObject();
				System.out.println(counter+" "+queue[counter].toString());
				/** ���� ������ ���� ������, �� �� counter==4 ����� �������� � ������ �������� �������� ��������  */
				if(counter==4){ // ������ ��� ��� ���� ��������� ��������� ������ - 5-�� 
					System.out.println(">>> clean <<<"); 
					//genericPool.invalidateObject(queue[0]);// ������ ������ �� Pool
					//genericPool.invalidateObject(queue[1]);// ������ ������ �� Pool 
					genericPool.returnObject(queue[0]);// ������� ������ � Pool
					genericPool.returnObject(queue[1]);// ������� ������ � Pool 
				}
			}catch(Exception ex){
				System.out.println("Borrow object Exception:"+ex.getMessage());
			}
		}
	}
}

/** �������, ������� ����� ��������� ��������� ������ �������� */
class PoolFactory extends BasePoolableObjectFactory{
	@Override
	public Object makeObject() throws Exception {
		System.out.println("get Object");
		return new ObjectInPool();
	}

	@Override
	public void passivateObject(Object object){
		try{
			((ObjectInPool)object).resetObject();
		}catch(Exception ex){
			System.out.println("PoolFactory#passivateObject Exception:"+ex.getMessage());
		}
	}
}


/** �������, �� ������� ����� ������ Pool*/
class ObjectInPool{
	private static Integer value=new Integer(0);
	private Integer currentValue;
	public ObjectInPool(){
		currentValue=value++;
		System.out.println("create new object:Object in Pool");
	}
	
	@Override
	public String toString(){
		return "ObjectInPool ["+currentValue.toString()+"]";
	}
	
	public void resetObject(){
		System.out.println("resetObject:"+currentValue.toString());
	}
	
	@Override
	public void finalize(){
		System.out.println("finalizeObject:"+currentValue.toString());
	}
}
