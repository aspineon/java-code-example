package xml_byte_array_transmit;

/** ����� ���������� ��� ��������� ByteArray � ������ � ����� �������������� � ������ � �������(� ������������� ������ � XML) */
public class XmlByteArrayTransmit {
	public static void main(String[] args){
		byte[] array=new byte[]{ (byte)03, (byte)32, (byte)200, (byte)234 };
		System.out.print("Start Array: ");printByteArray(array);
		
		String valueForTranslate=Base64.encodeLines(array);
		System.out.println("ConvertedString: "+valueForTranslate);
		
		byte[] returnValue=Base64.decodeLines(valueForTranslate.trim());
		System.out.print("ReturnValue:");printByteArray(returnValue);
	}
	
	/** ������� �� ������� ������ �� ���� */
	private static void printByteArray(byte[] array){
		if(array!=null){
			for(int counter=0;counter<array.length;counter++){
				System.out.print( Integer.toString(0xFF&array[counter]) );
				System.out.print("  ");
			}
			System.out.println();
		}
	}
}
