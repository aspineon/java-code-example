/*
 * Main.java
 *
 * 
 */

package client;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

/**
 * ������ ���������� CORBA
 */
public class Main {
    /**
     * �������� ��� �������, ������� �������� ��� ���������
     */
    public static void getServices(String[] args){
        try{
            /**   ������������� �������*/
            ORB orb=ORB.init(args,null);
            /**  ������ ��������� ��������*/
            String[] services=orb.list_initial_services();
            /**  ������� �� ������ ��������� �������*/
            for(int i=0;i<services.length;i++){
                System.out.println(i+" : "+services[i]);
            }
        }catch(Exception e){
            System.out.println("Error in trying get all Services");
        }
    }
    /**
     * ����� ����� � ���������
     */
    public static void main(String[] args) {
        try{
            getServices(args);
            /**  ������������� ������� ORB*/
            ORB orb=ORB.init(args,null);
            /**  ��������� ������ �� ������ ������� �������, 
             *   �� ���� �� ������, ������� ��������� ���� �� �������� */
            System.out.println("get reference on object of service");
            org.omg.CORBA.Object namingContextObj=orb.resolve_initial_references("NameService");
            /** �������������� ������ � ���� NamingContext ��� �������������� ����������� ������ ������� ���������� ��������� ������������ 
             * ���������� ������, ������� ������ � ������� ������
             */
            System.out.println("returns object, which is connected with current name");
            NamingContext namingContext=NamingContextHelper.narrow(namingContextObj);
            /**  ��������� ������ ������� �������� ���������� */
               // ���������-��� ������� ��   ��������������(id),����(kind)                     id,kind
            System.out.println("reception of the class of the reference of the target interface");
            NameComponent[] path={//new NameComponent("corejava","Context"),
                                  new NameComponent("Env","Object")};
            /** ��������� �������, ���������� � ��������� ������ */
            System.out.println("reception of the object, connected with specified by name");
            org.omg.CORBA.Object envObj=namingContext.resolve(path);
            Env env=EnvHelper.narrow(envObj);
            System.out.println("The Reception of the result removed to functions:"+env.getenv("value of remote PATH"));
        }catch(Exception e){
            System.out.println("CORBA Client Exception: "+e.getMessage());
        }
    }
    
}
