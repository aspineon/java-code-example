package bonpay.mail.sender_core.letter;

/** ���������� ������������� ������ */
public class LetterIdentifier {
	private int id;
	/** ���������� ������������� ������ */
	public LetterIdentifier(int id){
		this.id=id;
	}
	/**
	 * @return �������� ���������� ������������� ������ 
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param ���������� ���������� ������������� ������ 
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
}
