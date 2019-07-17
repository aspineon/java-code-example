/*
 * CC.java
 *
 * Created on 26 ������� 2007 �., 17:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * ��������� �������� ��������
 */
public class CC {
    /**
     * �������� �� ���������
     */
    public static String getDefaultKod(){
        return "06";
    }
    /**
     * �������� �� �������������� � �������� ��������
     */
    public static boolean isCCkod(String hex_string){
        boolean result=false;
        if(utility.isHEX_byte(hex_string)){
           // �������� ������������, �������� �� �������������
           int value=Integer.parseInt(hex_string);
           switch(value){
               case 3:result=true;break;
               case 4:result=true;break;
               case 5:result=true;break;
               case 6:result=true;break;
               case 7:result=true;break;
               case 8:result=true;break;
               case 9:result=true;break;
               case 0x0a:result=true;break;
               default:result=false;
           }
        }
        else {
            // �� ������� ������������� �������� � ��������� ��������
            result=false;
        }
        return result;
    }
    /**
     * �������� �� �������������� �������� � �������� ��������
     */
    public static boolean isCCkod(int value){
        boolean result=false;
        if((value>=0)&&(value<=10)){
            result=true;
        }
        return result;
    }
    /**
     * �������� �������� �������� �� ��������� ���� ��������
     */
    public static int getSpeedFromKod(String string_kod){
        int result=0;
        if(utility.isHEX_byte(string_kod)){
           // �������� ������������, �������� �� �������������
           int value=Integer.parseInt(string_kod);
           switch(value){
               case 3:result=1200;break;
               case 4:result=2400;break;
               case 5:result=4800;break;
               case 6:result=9600;break;
               case 7:result=19200;break;
               case 8:result=38400;break;
               case 9:result=57600;break;
               case 0x0a:result=115200;break;
               default:result=0;
           }
        }
        else {
            // �� ������� ������������� �������� � ��������� ��������
            result=0;
        }
        return result;
    }
    /**
     * �������� �������� �������� �� ��������� ���� ��������
     */
    public static int getSpeedFromKod(int value){
        int result=0;
        switch(value){
            case 3:result=1200;break;
            case 4:result=2400;break;
            case 5:result=4800;break;
            case 6:result=9600;break;
            case 7:result=19200;break;
            case 8:result=38400;break;
            case 9:result=57600;break;
            case 0x0a:result=115200;break;
            default:result=0;
        }
        return result;
    }
    /**
     * �������� ��� �� ��������� ��������
     */
    public static String getKodFromSpeed(int speed){
        String result="00";
        switch(speed){
            case 1200: result="03";break;
            case 2400: result="04";break;
            case 4800: result="05";break;
            case 9600: result="06";break;
            case 19200: result="07";break;
            case 38400: result="08";break;
            case 57600: result="09";break;
            case 115200: result="0A";break;
            default: result="00";
        }
        return result;
    }
    /**
     * �������� ��� �� ��������� ��������
     */
    public static String getKodFromSpeed(String hex_speed){
        String result="00";
        if(utility.isHEX_byte(hex_speed)){
           result=CC.getKodFromSpeed(Integer.parseInt(hex_speed,16));
        }
        else {
            // �� ������� ������������� ������ � �����
        }
        return result;
    }
}
