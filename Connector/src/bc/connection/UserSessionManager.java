package bc.connection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserSessionManager implements IUserSession{
	
	private void debug(Object information){
		StackTraceElement element[]=(new Throwable()).getStackTrace();
		System.out.print(element[1].getClassName());
		System.out.print(" DEBUG ");
		System.out.print(element[1].getMethodName());
		System.out.println(information);
	}
	
	/** ������, ������� ������ ������ User - SessionId
	 * <b>�� ������ ������������ ����� ���� ��������� SessionId</b> 
	 * */
	private HashMap<String,List<String>> store=new HashMap<String, List<String>>();
	
	@SuppressWarnings("unused")
	@Override
	public boolean clear(String sessionId) {
		boolean returnValue=false;
		Set<String> users=store.keySet();
		List<String> sessions;
		USERS: for(String user:users){
			sessions=this.store.get(user);
			for(int counter=0;counter<sessions.size();counter++){
				if(sessions.get(counter).equals(sessionId)){
					sessions.remove(counter);
					returnValue=true;
					//break USERS;
				}
			}
		}
		return returnValue;
	}

	@Override
	public String getUser(String sessionId) {
		String returnValue=null;
		Set<String> users=store.keySet();
		List<String> sessions;
		USERS: for(String user:users){
			sessions=this.store.get(user);
			if(sessions.contains(sessionId)){
				returnValue=user;
				break USERS;
			}
		}
		return returnValue;
	}

	@Override
	public boolean put(String user, String sessionId) {
		// ��������� ������� ������������ �� ������������� � ���������
		List<String> list=store.get(user);
		if(list!=null){
			// ������������ ���������� - ��������
			if(!list.contains(sessionId)){
				list.add(sessionId);
			}else{
				// ������ ������ ��� ���������� � �� ���������� ������������ 
			}
		}else{
			// ������������ �� ���������� - ������� � ��������
			List<String> newList=new ArrayList<String>();
			newList.add(sessionId);
			store.put(user, newList);
		};
		return true;
	}

	@Override
	public boolean hasSessionsByUser(String user) {
		try{
			debug("user:"+user+"   size:"+this.store.get(user).size());
			return this.store.get(user).size()>0;
		}catch(NullPointerException ex){
			// ������������ ������, ���� ������������
			return false;
		}
	}

}
