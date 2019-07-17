/*
 * AAN.java
 *
 * Created on 27 ������� 2007 �., 10:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * ������� �������� ������� �� ������ "N" ����������� �����
 * @author root
 */
public class AAN {
    private String value_AA;
    private String value_N;
    
    /**
     * �������� �� ���������� ��������� �������� ������, ����������� � ���� ������ �� ������ ��������
     */
    private boolean is_valid_N(String _N){
        boolean result=false;
        if(_N.length()==1){
            int value=Integer.parseInt(_N);
            if((value>=0)&&(value<=7)){
                result=true;
            }
        }
        return result;
    }
    /**
     * �������� �� ���������� ��������� �������� ������, ����������� � ���� int
     */
    private boolean is_valid_N(int _N){
        boolean result=false;
        if((_N>=0)&&(_N<=7)){
                result=true;
        }
        return result;
    }

    /**
     * ������������� ��� ����������
     * AA - ����� ������, ����������� ���������
     */
    public AAN(){
        this.value_AA="00";
        this.value_N="0";
    }
    public AAN(String _AA,String _N) throws Exception{
        if(utility.isHEX_byte(_AA)
         &&this.is_valid_N(_N)){
           this.value_AA=_AA;
           this.value_N=_N;
        }
    }
    public AAN(String _AA,int _N) throws Exception{
        if(this.is_valid_N(_N)){
           this.value_AA=_AA;
           this.value_N=Integer.toString(_N);
        }
    }
    
    /**
     * ��������� � ��������� �������� ���� ������
     */
    public void clear(){
        this.value_AA="00";
    }
    /**
     * ��������� ������ ������������� ������
     */
    public boolean set_AA(String _AA){
        boolean result=false;
        if(utility.isHEX_byte(_AA)){
            this.value_AA=_AA;
            result=true;
        }
        return result;
    }
    /**
     * ��������� ������ ������������� ������
     */
    public String get_AA(){
        return this.value_AA;
    }
    /**
     * ��������� ������� ������
     */
    public boolean set_N(String _N){
        boolean result=false;
        if(this.is_valid_N(_N)){
            this.value_N=_N;
            result=true;
        }
        return result;
    }
    /**
     * ��������� ������� ������
     */
    public boolean set_N(int _N){
        boolean result=false;
        if(this.is_valid_N(_N)){
            this.value_N=Integer.toString(_N);
            result=true;
        }
        return result;
    }

    /**
     * ��������� ������� ������
     */
    public String get_N(){
        return this.value_N;
    }

    /**
     * ������� ��� �������� � ����
     */
    public String get_command(){
        return "#"+this.get_AA()+this.get_N();
    }
    /**
      * ��������� ���������� ������
      */
    public boolean response_is_valid(String response){
        boolean result=false;
        if(response.length()>=3){
            if(response.charAt(0)=='>'){
                result=true;
            }
        }
        else{
            // �������� �������� � ������ - ��� ������ �� ������� - ������ ��������
        }
        return result;
    }
    
}
