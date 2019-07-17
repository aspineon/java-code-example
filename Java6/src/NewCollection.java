import java.util.ArrayDeque;


public class NewCollection {
	
/**
 * Deque � �������� ���������� Queue. �������� ���������������� ��� �double ended queue� � ������������ ��� ���. ��� �� ������� ���������� �������� ���� � ���, ����� ������������ �������, ������� �������� � ����� ������ � ��� FIFO (��������� Queue) � LIFO (��������� Stack)

BlockingDeque � ��������� ��������� ����������� ���� ��� �������� ����������� � ������� ��������.

NavigableSet, NavigableMap � ���������� ��������� ������ �������� ������� �������� � ������.

ConcurrentNavigableMap � ����, ��� � NavigableMap, �� ��� ������������ �������� � ��������. �� ������� ����� �������� � ������������ ��� ������� ��� �����.
 */
	private static void exampleDeque(){
		ArrayDeque<String> deque=new ArrayDeque<String>();
		
		deque.add("Middle object");
		
		deque.addFirst("First");
		deque.addLast("Last");
		
		for(String element:deque){
			System.out.println(element);
		}
	} 
	
	public static void main(String[] args){
		exampleDeque();
	}
	
}
