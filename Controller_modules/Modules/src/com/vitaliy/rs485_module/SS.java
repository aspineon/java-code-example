/*
 * SS.java
 *
 * Created on 27 ������� 2007 �., 10:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * ������ ���������
 * @author root
 */
public class SS {
    /**
     * ������ ����������� ������� �������� �� ������� - true, �������� - false
     */
    public static boolean watchdog_timer_main_is_set(String _SS){
        boolean result=false;
        if(utility.isHEX_byte(_SS)){
            int value=Integer.parseInt(_SS,16);
            if((value&(int)utility.pow(2,7))==1){
                result=true;
            }
        }
        else {
            // ������ ���������� � ���� int
        }
        return result;
    }

    /**
     * ���� ������������ ����������� ������� �������� �� ������� - true, �������� - false
     */
    public static boolean watchdog_timer_operate_main_is_set(String _SS){
        boolean result=false;
        if(utility.isHEX_byte(_SS)){
            int value=Integer.parseInt(_SS,16);
            if((value&(int)utility.pow(2,2))==1){
                result=true;
            }
        }
        else {
            // ������ ���������� � ���� int
        }
        return result;
    }
   
}
