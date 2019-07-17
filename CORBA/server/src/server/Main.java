/*
 * Main.java
 *
 * Created on 14 ������ 2008, 19:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package server;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

/**
 * �����, ������� ��������� ��������� ��� ���������� ������
 * �������� ���� ��� ������� �������
 */
class EnvPOAImpl extends EnvPOA{
    /** ���������� ���������� CORBA - ���������� ������ EnvPOA(org.omg.PortableServer.Servant.)
     * @param name ��������� ��������� �� ���������� �������
     * @return ����������� ��������� ���� ������ ���������� �������
     */
    public String getenv(String name) {
        System.out.println("server get argument:"+name);
        return "remote method say: "+name;
    }
}

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            // ����� � ������������� ORB �������
            System.out.println("Creating and initializing the ORB...");
            org.omg.CORBA.ORB orb=org.omg.CORBA.ORB.init(args,null);
            // 
            System.out.println("Registering server implementation with the ORB...");
            POA rootpoa=(POA)orb.resolve_initial_references("RootPOA");
            rootpoa.the_POAManager().activate();
            // �������� �������-�������
            EnvPOAImpl impl=new EnvPOAImpl();
            // �������������� �������-������� � CORBA ������
            org.omg.CORBA.Object ref=rootpoa.servant_to_reference(impl);
            // ������� IOR(���������� ������������� �������) ������ � ������� ������ object_to_string
            System.out.println(orb.object_to_string(ref));
            // ��������� ������ �� ������ ����������
            org.omg.CORBA.Object namingContextObj=orb.resolve_initial_references("NameService");
            NamingContext namingContext=NamingContextHelper.narrow(namingContextObj);
            // ��������� ������ ��� ������� 
            NameComponent[] path={new NameComponent("Env","Object")};
            // ���������� ������� � ��� ������
            System.out.println("Binding server implementation to name service...");
            namingContext.rebind(path,ref);
            // ��������� � ��������� �������� �� ������� ��������
            System.out.println("Waiting for invocations from clients...");
            orb.run();
        }catch(Exception e){
            System.out.println("error in create or work CORBA Server "+e.getMessage());
        }
    }
    
}
