
class class_parent {
    int field_a=10;
    int field_b=20;
    
    {System.out.println("class_parent: ��।������ ����� ������� �����");
    }

    static{
    	System.out.println("class_parent: ����᪨� ���樠������ � �����");
    }
    
    public static void method_a(){
        System.out.println("class_parent: ����᪨� ��⮤ �����");
    }
    
    public class_parent(){
        System.out.println("class_parent: ���������");
    }
}

class class_child extends class_parent{
    int field_a=10;
    int field_b=20;
    
    {System.out.println("class_child: ��।������ ����� ������� �����");
    }

    static{
    	System.out.println("class_child: ����᪨� ���樠������ � �����");
    }
    
    public static void method_a(){
        System.out.println("class_child: ����᪨� ��⮤ �����");
    }
    
    public class_child(){
        super();
	System.out.println("class_child: ���������");
    }
    
}

public class parent{
    public static void main(String[] args){
        System.out.println("begin");
	new class_child();
	System.out.println("end");
    }
}