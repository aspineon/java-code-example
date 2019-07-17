/*
 * work.java
 *
 * Created on 27 ������� 2007 �., 11:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * ������������� �������� �� �������� - �������� ������ � ��������� ���� ������
 * @author root
 */
public class work implements ComListener{
    private COM_port comport;
    private volatile String string_from_port;
    private volatile boolean flag_run=false;
    public work() {
        try{
            // �������� �����
            this.comport=new COM_port("com0",9600,this);
            this.flag_run=true;
            // ��������� �������
            this.process();
            
        }
        catch(Exception e){
            System.out.println("Error in open port");
        }
    }
    public void stop(){
        if(this.flag_run==true){
            this.flag_run=false;
        }
    }
    /**
     * ����������� ���� ���������
     */
    private void process(){
        while(this.flag_run){
            // ������ ������
            synchronized(this.string_from_port){
                if(!this.string_from_port.equals("")){
                    if(comport.write_to_port("Answer:"+this.string_from_port)!=0){
                        this.string_from_port="";
                    }
                }
            }
            comport.write_to_port("ATI");
            comport.write_to_port("AT^SCFG?");
            try{
                Thread.sleep(1000);
            }
            catch(Exception e){}
        }
    }
    /**
     * ��������� ������ � �����
     */
    public void data_from_port(String s) {
        synchronized(this.string_from_port){
            this.string_from_port=this.string_from_port+s;
        }
    }
    
}
