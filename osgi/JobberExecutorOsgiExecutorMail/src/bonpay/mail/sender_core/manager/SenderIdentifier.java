package bonpay.mail.sender_core.manager;

/** ���������� ������������� Sender-�, �� �������� ����� ������ ����� ������ ������ �� ������������ */
public class SenderIdentifier {
	private int id;
	
	/** ���������� ������������� Sender-�, �� �������� ����� ������ ����� ������ ������ �� ������������ 
	 * @param recordId - ���������� ������������� � ���� ������ (id ������ )
	 * */
	public SenderIdentifier(int recordId){
		this.id=recordId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(obj instanceof SenderIdentifier){
			return ((SenderIdentifier)obj).id==this.id;
		}
		return false;
	}
	
	/** �������� ���������� ������������� � �������� ���� ������ */
	public int getId(){
		return this.id;
	}
}
