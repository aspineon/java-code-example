/*
 * ComListener.java
 *
 * Created on 9 ������� 2007 �., 8:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * ���������, � ������� ���������� ����������, ��������� � COM �����
 */
public interface ComListener {
    public void data_from_port(String s);
}
