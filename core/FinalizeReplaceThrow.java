
public class FinalizeReplaceThrow {
	public static void main(String[] args) throws Exception{
		System.out.println("begin");
		getValue(0);
		getValue(-23);
		System.out.println("-end-");
	}
	
	/**
	 * ������ ����� ������ ���������� 0, Exception ������������
	 * @param value
	 * @return
	 * @throws Exception
	 */
	private static int getValue(int value) throws Exception{
		try{
			if(value<0)throw new Exception();
		}finally{
			return 0;
		}
	}
}

