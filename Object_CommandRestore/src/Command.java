import java.io.Serializable;


/** �����, ������� �������� ������ ��������� ������������ ������ */
abstract public class Command implements Serializable{
	private final static long serialVersionUID=1L;
	private String commandName;
	
	public Command(String commandName){
		this.commandName=commandName;
	}
	
	/** ���������� ��������� �������� �������� */
	public abstract boolean action();
	/** �������� ��������� ���������� ��������� �������� */
	public abstract int getResult();
}
