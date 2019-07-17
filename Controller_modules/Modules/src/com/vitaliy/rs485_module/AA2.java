/*
 * AA2.java
 *
 * Created on 26 ������� 2007 �., 19:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * ������ ���������� ������������
 */
public class AA2 {
    private String value_AA;
    private String value_TT;
    private String value_CC;
    private String value_FF;
    /**
     * ������������� ��� ����������
     * AA - ����� ������, ����������� ���������
     * TT - ��� ���� ����������� ����� ������
     * CC - ��� �������� �������� ������
     * FF - ������ ������ ������
     */
    public AA2(){
        this.value_AA="00";
        this.value_TT="00";
        this.value_CC="00";
        this.value_FF="00";
    }
    public AA2(String _AA, String _TT, String _CC, String _FF) throws Exception{
        if(utility.isHEX_byte(_AA)
         &&TT.isTTkod(_TT)
         &&CC.isCCkod(_CC)
         &&FF.isFFkod(_FF)){
           this.value_AA=_AA;
           this.value_TT=_TT;
           this.value_CC=_CC;
           this.value_FF=_FF;
        }
    }
    /**
     * ��������� � ��������� �������� ���� ������
     */
    public void clear(){
        this.value_AA="00";
        this.value_TT="00";
        this.value_CC="00";
        this.value_FF="00";
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
     * ��������� ���� ����������� �����
     */
    public boolean set_TT(String _TT){
        boolean result=false;
        if(TT.isTTkod(_TT)){
            this.value_TT=_TT;
            result=true;
        }
        return result;
    }
    /**
     * ��������� ���� ����������� �����
     */
    public String get_TT(){
        return this.value_TT;
    }
    /**
     * ��������� �������� �������� ������
     */
    public boolean set_CC(String _CC){
        boolean result=false;
        if(CC.isCCkod(_CC)){
            this.value_CC=_CC;
            result=true;
        }
        return result;
    }
    /**
     * ��������� �������� �������� ������
     */
    public String get_CC(){
        return this.value_CC;
    }
    /**
     * ��������� ������� ������ ������
     */
    public boolean set_FF(String _FF){
        boolean result=false;
        if(FF.isFFkod(_FF)){
            this.value_FF=_FF;
        }
        return result;
    }
    /**
     * ��������� ������� ������ ������
     */
    public String get_FF(){
        return this.value_FF;
    }
    /**
     * ������� ��� �������� � ����
     */
    public String get_command(){
        return "$"+this.get_AA()+"2";
    }
    /**
      * ��������� ���������� ������
      */
    public boolean response_is_valid(String response){
        boolean result=false;
        if(response.length()>=9){
            if(response.charAt(0)=='!'){
                result=true;
            }
        }
        else{
            // �������� �������� � ������ - ��� ������ �� ������� - ������ ��������
        }
        return result;
    }
}
