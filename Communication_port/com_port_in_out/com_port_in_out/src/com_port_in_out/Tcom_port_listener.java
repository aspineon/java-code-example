/*
 * Tcom_port_listener.java
 *
 * Created on 24 ������� 2007 �., 20:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com_port_in_out;

import java.io.InputStream;

/**
 *
 * @author root
 */
public class Tcom_port_listener implements Runnable{
    private Thread thread;
    private InputStream inputstream;
    private Icom_listener com_listener;
    private volatile boolean flag_run=false;
    /**  ����������� ������ ��� ������������� */
    public Tcom_port_listener(InputStream this_inputstream, Icom_listener this_com_listener) {
        this.inputstream=this_inputstream;
        this.com_listener=this_com_listener;
        this.thread=new Thread(this);
        this.flag_run=true;
        this.thread.start();
    }
    /**
     * �������� �� ������������ ������
     */
    public boolean isRun(){
        return this.thread.isAlive();
    }
    /**
     * ��������� ������
     */
    public void stop(){
        this.flag_run=false;
    }
    /**
     * ��������� �����, ������� ��������� ���������� ������������� ����� �� ����������� ������
     */
    public void run() {
        while(this.flag_run=true){
            try{
                if(this.inputstream.available()>0){
                    // ������ �� ����� ������������
                    byte[] temp_data=new byte[this.inputstream.available()];
                    this.inputstream.read(temp_data);
                    this.com_listener.data_from_com(temp_data);
                }
                else {
                    // ������ �� ����� �����������
                }
            }
            catch(Exception e){
                System.out.println("Error in read data from InputStream "+e.getMessage());
            }
        }
        System.out.println("��������� ������������� ������");
    }
}
