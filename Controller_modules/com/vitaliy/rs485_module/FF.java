/*
 * FF.java
 *
 * Created on 26 ������� 2007 �., 17:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * ��������� ������� ������
 */
public class FF {
    private static int value=0;
    /**
     * �������� �� ���������
     */
    public static String getDefaultKod(){
        return "00";
    }
    /**
     * ������� ����������
     */
    public static void clear(){
        FF.value=0;
    }
    /**
     * �������� �������� ����������
     */
    public static String getHexValue(){
        String result="";
        if(FF.value<16){
            result="0"+Integer.toHexString(FF.value);
        }
        else {
            result=Integer.toHexString(FF.value);
        }
        return result;
    }
    /**
     * �������� �������� ���������� 
     */
    public static int getIntValue(){
        return FF.value;
    }
    /**
     * ���������� �������� ����������
     */
     public static void setIntValue(String hex_string) throws Exception{
         if(utility.isHEX_byte(hex_string)){
             int value=Integer.parseInt(hex_string,16);
             FF.setIntValue(value);
         }
         else {
             throw new Exception("Error convert String to integer");
         }
     }
    /**
     * ���������� �������� ����������
     */
    public static void setIntValue(int value) throws Exception{
        if((value>=0)&&(value<=255)){
            FF.value=value;
        }
        else{
            throw new Exception("Error convert String to integer");
        }
    }
    
    /**
     * �������� �������� ���������� ������� 60��
     */
    public static boolean getSuppression60Hz(){
        boolean result=false;
        if((FF.value & (int)utility.pow(2,7))==0){
            result=true;
        }
        return result;
    }
    /**
     * �������� �������� ���������� ������� 50��
     */
    public static boolean getSuppression50Hz(){
        boolean result=false;
        if((FF.value & (int)utility.pow(2,7))!=0){
            result=true;
        }
        return result;
    }
    /**
     * �������� �������� ���� �������� ����� (true=��������, false=��������)
     */
    public static boolean getBitControlAmount(){
        boolean result=false;
        if((FF.value & (int)utility.pow(2,6))!=0){
            result=true;
        }
        return result;
    }
    /**
     * �������� �������� ���� �������� �����
     */
    public static int getBitFormatData(){
        int result=0;
        if((FF.value & (int)utility.pow(2,0))==1){
            result=result+1;
        }
        if((FF.value & (int)utility.pow(2,1))==1){
            result=result+2;
        }
        return result;
    }
    /**
     * ���������� �������� ���������� ������� 60��
     */
    public static void setSuppression60Hz(boolean value){
        if(value==true){
            // ��������� � "0" 7 ����
            if((FF.value&(int)utility.pow(2,7))==1){
                FF.value=FF.value-(int)utility.pow(2,7);
            }
        }
        else {
            // ��������� � "1" 7 ����
            FF.value=FF.value|(int)utility.pow(2,7);
        }
    }
    /**
     * ���������� �������� ���������� ������� 50��
     */
    public static void setSuppression50Hz(boolean value){
        if(value==true){
            FF.value=FF.value|(int)utility.pow(2,7);
        }
        else {
            // �������� �� ���������������
            if((FF.value&(int)utility.pow(2,7))!=0){
                FF.value=FF.value-(int)utility.pow(2,7);
            }
        }
        
    }
    /**
     * ���������� �������� ���� �������� ����� (true=��������, false=��������)
     */
    public static void setBitControlAmount(boolean value){
        if(value==true){
            FF.value=FF.value|(int)utility.pow(6,2);
        }
        else {
            // �������� �� ���������������
            if((FF.value&(int)utility.pow(6,2))!=0){
                FF.value=FF.value-(int)utility.pow(6,2);
            }
        }
    }
    /**
     * ���������� �������� ���� �������� �����
     */
    public static void setBitFormatData(int value){
        FF.value=FF.value|value;
    }
    /**
     * �������� �� �������������� � ����
     */
    public static boolean isFFkod(int value){
        boolean result=false;
        if((value>=0)&&(value<=255)){
            result=true;
        }
        return result;
    }
    /**
     * �������� �� �������������� � ����
     */
     public static boolean isFFkod(String hex_string){
         boolean result=false;
         if(utility.isHEX_byte(hex_string)){
             result=FF.isFFkod(Integer.parseInt(hex_string,16));
         }
         else{
             // �� ������� ������������� � ���� int
         }
         return result;
     }
}
