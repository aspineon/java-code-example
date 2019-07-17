import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;


/** ������������ ������������ JUnit */
@RunWith(SimpleRunner.class)// ������ �������� ���� ����������� �� ������� JUnit ����� ����������� Runner-��
public class JUnitExample {
	private int controlValue=0;
	
	public JUnitExample(){
	}
	
/*	public JUnitExample(int value){
		this.controlValue=value;
	}
*/
	public int getControlValue(){
		return this.controlValue;
	}

	@Before
	public void BeforeControl(){
		System.out.println("Run before test");
		this.controlValue=5;
	}

	@After
	public void AfterControl(){
		System.out.println("Run after test");
		this.controlValue=0;
	}
	
	@Test(timeout=5)
	public void Control(){
		assertTrue(this.controlValue==5);
		System.out.println("test was started");
	}
	
	public static void main(String[] args){
		// ������ ������ �� ��������� ������ 
		org.junit.runner.JUnitCore.main("JUnitExample");
	}
}


