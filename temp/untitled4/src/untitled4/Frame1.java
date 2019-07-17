package untitled4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// ��������� �������� Exception
class my_exception extends Exception
{
  my_exception(String s)
  {
    super(s);
  }
}

// ��������� �����, ������� �������� ������ ���� ����������� �����, ������� ������ ���� Override � ����������
// ��� ��� ��������� ����� ��� �� Abstract, ��� ��� � ���� �� ����� ���� �������� - ����� ���� ������ ���������, � ��� � ��� �������
//class begin_hello_world extends Object
abstract class begin_hello_world extends Object// �� ��������� ������ ������ ����� ������ "class hello_world extends Object"
{
  abstract String get_message();
}
// ���������, ������� ����� �������� � hello_world
interface begin_hello_world_add
{
  Integer variable_one=new Integer(10); // ���������
  String information=new String("Information"); // ���������
  public String get_message(); // �����, ������� ������ ���� ���������� � �������, ��� ����� �������� ������ ����������
}

class hello_world extends begin_hello_world implements begin_hello_world_add
{
  public static int counter=0;// ���������� ������, � �� �������
  String message="hello world";
  // ���������� �������
  int[] array_int=new int[10];
  int[] temp_array_int={1,2,3,4,5};
  int temp_array_int2[]={1,2,3,4,5};
  Integer[] array_Integer=new Integer[10];
  // constructor
  hello_world()
  {
    counter++; // ������� ��������� � ������� ������ (���-�� ��������� ��������)
    try
    {
      this.message="Hello world constructor set";
      for(int index=0;index<this.array_int.length;index++)
      {
        this.array_int[index]=index;
        this.array_Integer[index]=new Integer(index);
      }
    }
    catch (ArrayIndexOutOfBoundsException e)// ������� Exception Index of Bounds
    {
      System.out.println("Array Index of bounds: "+e);
    }
    catch (Exception e)// ������� ��� ��������� Exception
    {
      System.out.println("Detected Exception in constructor:"+e);
    }

  }
  // constructor overloading - whith arguments
  hello_world(String s)
  {
    this.message=s;
  }
  //�����, ������� ������� �� ������� ������ ��� ���� ��������
  void put_arrays()
  {
    cykle_1:for(int index=0;index<this.array_int.length;index++)
    {
     if(index>6){break cykle_1;};
      //if((index % 3)==0)System.out.println(this.array_int[index]);
      if((index%3)!=0){
        continue cykle_1;// ������������� �������� "continue" ��� ��������� ����� �� ����� "cykle_1"
      }
      else{
        System.out.println(this.array_int[index]);
      };
    }
  }
  // �����, ��������������� ���� ������� ������
  void set_message(String s)
  {
    this.message=s;
  }
  // �����, ������������ ���� ������� ������
  public String get_message()
  {
    return this.message;
  }
  // �����, ������� ������� �� ������� ���� ������� ������
  void print_message()
  {
    System.out.println(this.message);
  }
  // ��� System.out.println - ��� ��������� ������� ������ ���������� � ������ toString() ������� �������
  // override Object.toString()
  public String toString()
  {
    return "Override method toString: "+this.message+" : "+this.information;
  }
}
/*end class hello_world extends Object*/
// ������� ���������
interface put_method // extended <add_interface1>,<add_interface2>,<add_interface3>
{
  void print_variable(); // ������ ��������� �������� ���������� � ����������� ������ � �������, ������� ����� ��� ���������
  // ����� ������� ��� ����� ���������� ��� ����������� ����� ��� ������, ��� �� ������ ���� Overrided
}
// ������� �����, ���� �� ������� �������� ���������� ������ �� ������ ������ hello_world
class hello_world_dop
{
  // ��������� public �����, ������� ���������� ��� ������� hello_world_add � ��� ����� ������ get_hello_world, ��� �������� - ��� String
  public hello_world_add get_hello_world(String s)
  {
    return  new hello_world_add(s);
  }
}

// ��������� ����� hello_world_add, ������� �������� ���������� hello_world � �������� � ���� ��������� put_method
class hello_world_add extends hello_world implements put_method
{
  public void print_variable()
  {
    try
    {
      System.out.println("Hello_world_add implements put_method: "+super.message);
      throw new Exception("hello");// create Exception
    }
    catch(Exception e)// catch Exception
    {
      System.out.println("catch exception, Text of Exception:"+e); // print Exception
    }
    finally// finally block
    {
      System.out.println(" end of Exception");
    }
  }
  hello_world_add(String s)
  {
    super(s);// ���������� � ������������ �����������, ������� � �������� ���������� ��������� ������
  }
}
public class Frame1 extends JFrame {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();

  /**Construct the frame*/
  public Frame1() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**/

  /**Component initialization*/
  private void jbInit() throws Exception  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    contentPane.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        contentPane_mouseClicked(e);
      }
    });
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(400, 300));
    this.setTitle("Frame Title");
  }
  /**/

  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }
  /**/

  /* Event mouse click on the Panel*/
  void contentPane_mouseClicked(MouseEvent e){
  Object[] array_of_object=new Object[10];
  String[] array_of_string={"one","two","three"}; // �������� ������, � ����� �� ���������������� 3 ����������
  String[] array_of_string2=new String[3]; // �������� ������, � ��������������� ������ ��� 3 �������� String
  String[] array_of_string3;// �������� ������, �� �� ��������������� ������ ��� ������ �������

  hello_world temp  =new hello_world();
  hello_world temp2 =new hello_world();
  hello_world_dop temp4=new hello_world_dop();

  hello_world_add temp3;
  temp3=temp4.get_hello_world("hello_world_add_message_string");
  //hello_world_add temp3=new hello_world_add("hello_world_add_message_string");

  // array_of_object[0]==null
  array_of_object[1]=temp;//  hello_world      to object
  array_of_object[2]=temp2;// hello_world      to object
  array_of_object[3]=temp3;// hello_world_add  to object
  array_of_object[4]=temp4;// hello_world_dop  to object


  temp.set_message("This is my string");
  temp.print_message();
  System.out.println("call method toString: "+temp2);// call method "toString"
  System.out.println("counter of class hello_world: "+hello_world.counter); // output count of object of "hello_world" - STATIC hello_world.counter
  temp.put_arrays();
  //temp3.print_variable();
  //<class "sub-class">  <class "super">  <method of "sub-class">
  ( (hello_world_add) (array_of_object[3]) ).print_variable(); // hello_world from object
  // ����� �������� �������
  java.util.Date now=new java.util.Date();
  System.out.println("Time:"+now);

  }
}