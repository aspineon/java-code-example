package bonpay.mail.sender_core.letter;

/** ������, ������� �������������� �������������� �����  */
public class LetterAttachment {
	private String pathToFile;
	private String originalFileName;
	
	/** ������, ������� �������������� �������������� �����  
	 * @param originalName - ������������ ��� �����
	 * @param pathToFile - ������ ���� � �����
	 */
	public LetterAttachment(String originalName, String pathToFile){
		this.originalFileName=originalName;
		this.pathToFile=pathToFile;
	}

	/** ������ ���� � ����� */
	public String getPathToFile() {
		return pathToFile;
	}

	/** ������������ �������� �����  */
	public String getOriginalFileName() {
		return originalFileName;
	}

	/** ���������� ������������ ��� �����  */
	public void setOriginalFileName(String name) {
		this.originalFileName=name;
	}
	
}
