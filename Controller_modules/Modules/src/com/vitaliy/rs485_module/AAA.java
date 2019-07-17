/*
 * AAA.java
 *
 * Created on 27 ������� 2007 �., 10:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * ������ ���� ���������� ����������
 * @author root
 */
public class AAA {
    private String value_AA;
    /**
     * ������������� ��� ����������
     * AA - ����� ������, ����������� ���������
     */
    public AAA(){
        this.value_AA="00";
    }
    public AAA(String _AA) throws Exception{
        if(utility.isHEX_byte(_AA)){
           this.value_AA=_AA;
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
     * ������� ��� �������� � ����
     */
    public String get_command(){
        return "$"+this.get_AA()+"A";
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
