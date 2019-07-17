/*
 * Tcom_port.java
 *
 * Created on 24 ������� 2007 �., 19:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com_port_in_out;

import javax.comm.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author root
 */
public class Tcom_port {
    private Icom_listener com_listener; // ������, �������� ����� ���������� ������ � �����
    private String port_name;// ��� �����
    private boolean flag_open=false;// ������� �������� �����
    private InputStream inputstream;// ������� �����
    private OutputStream outputstream;// �������� �����
    private SerialPort serialport;// ����
    private CommPort commport;
    private CommPortIdentifier cpi;
    private Tcom_port_listener com_port_listener;
    
    /** ����������� ������, ������� ������ �� ������� �� ����� � ���������� ������ � ���� */
    public Tcom_port(String this_port_name,Icom_listener this_com_listener) {
        this.com_listener=this_com_listener;
        this.port_name=this_port_name;
    }
    /**
     * ���������� ������� �������� ����� � ��� �������������
     */
    public boolean isOpen(){
        return this.flag_open;
    }
    /**
     * ���������� ������������ �����
     */
    public String getPortName(){
        return this.port_name;
    }
    /**
     * �������� � ������������� �����, ���������� ������ ��������� �� ������ ��� �������������, 
     * ��� �� ���������� null - � ������ ��������� �������� � �������������
     */
    public String open(){
        String result="";
        try{
            System.out.println("try open port, is name="+this.port_name);
            cpi=javax.comm.CommPortIdentifier.getPortIdentifier(this.port_name);
            commport=cpi.open("com_port",100);
            //serialport=(SerialPort) cpi.open("Test",100);
            outputstream=commport.getOutputStream();
            inputstream=commport.getInputStream();
            com_port_listener=new Tcom_port_listener(this.inputstream,this.com_listener);
            this.flag_open=true;
        }
        catch(Exception e){
            System.out.println("Error in open/initialize COM port:"+e.getMessage());
            result=e.getMessage();
            this.flag_open=false;
        }
        return result;
    }
    /**
     * �������� ����� � ������� ������
     */
    public void close(){
        if(this.flag_open==true){
            // ���� ������, ����� ������� ��� ������
            try{
                this.inputstream.close();
            }
            catch(Exception e){}
            try{
                this.outputstream.close();
            }
            catch(Exception e){}
            try{
                this.commport.close();
            }
            catch(Exception e){}
            try{
                if(this.com_port_listener.isRun()){
                    this.com_port_listener.stop();
                }
            }
            catch(Exception e){}
        }
        else {
            // ���� �� ������
        }
    }
    /**
     * ������ ������ � ����, ������� ������ � ���� ������
     */
    public String write(String s){
        String result=null;
        if(this.isOpen()){
            try{
                System.out.println(" put String to COM1:"+s);
                byte[] temp_array=s.getBytes();
                for(int i=0;i<temp_array.length;i++){
                    System.out.println(i+":"+temp_array[i]);
                }
                this.outputstream.write(temp_array);
            }
            catch(Exception e){
                result=e.getMessage();
            }
        }
        else{
            result="Error write to port: port not open";
        }
        return result;
    }
    /**
     * ������ ������ � ����, ������� ������ � ���� ������
     */
    public String write(byte[] array_of_byte){
        String result=null;
        if(this.isOpen()){
            try{
                this.outputstream.write(array_of_byte);
            }
            catch(Exception e){
                result=e.getMessage();
            }
        }
        else{
            result="Error write to port: port not open";
        }
        return result;
    }
    /**
     * ������ � ���� ������ � ��������� �������� ������, ������� ������ � ���� ������
     */
    public String writeln(String s){
        return this.write(s+(char)13+(char)10);
    }
    /**
     * ������ � ���� ������ � ��������� �������� ������, ������� ������ � ���� ������
     */
    public String writeln(byte[] array_of_byte){
        byte[] temp_array=new byte[array_of_byte.length+2];
        temp_array[temp_array.length-2]=13;
        temp_array[temp_array.length-1]=10;
        return this.write(temp_array);
    }
}
