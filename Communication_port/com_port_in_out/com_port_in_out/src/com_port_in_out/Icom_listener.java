/*
 * Icom_listener.java
 *
 * Created on 24 ������� 2007 �., 19:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com_port_in_out;

/**
 * ���������, ������� �������� ���������� ������ �� �����
 * @author root
 */
public interface Icom_listener {
    public void data_from_com(byte[] data);
}
