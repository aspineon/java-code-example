/*
 * table.java
 *
 * Created on 15 ������ 2008, 14:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cherkashin.vitaliy.sql;
import javax.swing.*;
import com.cherkashin.vitaliy.inifiles.IniFile;
import javax.swing.table.TableColumn;
/**
 * ����� ��� ������ � �������� JTable
 */
public class table {
    /**
     * ����� ������� ������� �� ������� �������
     *(�������� ���������� ������ � �����������, � ������ ������� ��������)
     * @param table �������� �������, � ������� ����� ���������� ��������
     * @param delete_column_name ������ �����, ������� �������� ����� ���� ������, ������� ����� �������
     */
    public static void delete_from_table(JTable table,String[] delete_column_name){
        for(int i=0;i<delete_column_name.length;i++){
            try{
                table.removeColumn(table.getColumn(delete_column_name[i]));
            }catch(Exception e){
                System.out.println("error in delete column by name or id:"+delete_column_name[i]);
            }
        }
    }
    /**
     * �����, ������� ������������� �� ������� �� �� ������ �������� ������
     * @param table �������, � ������� ����� �������� �������
     * @param column_name ������ ���� ��������
     * @param column_width ������ �������� ������ ��� ��������
     */
    public static void set_width_on_name(JTable table,String[] column_name,int[] column_width){
        for(int i=0;i<column_name.length;i++){
            if(i<column_width.length){
                try{
                    table.getColumn(column_name[i]).setPreferredWidth(column_width[i]);
                }catch(Exception e){
                    System.out.println(" �� ������� ���������� ������ ��� �������� "+column_name[i]);
                }
            }else{
                System.out.println("����������� ������ ���-�� ����������");
            }
        }
    }
    /**
     * �����, ������� ������������� ����� ��� �������� �� �� ������ � ������
     * @param table �������, � ������� ����� ����������� ���������
     * @param column_name ����� �������� � ������
     * @param column_title �����, ������� ����� ���������� � ������� ����������
     */
    public static void set_column_title_on_name(JTable table,
                                                String[] column_name,
                                                String[] column_title){
        for(int i=0;i<column_name.length;i++){
            if(i<column_title.length){
                try{
                    table.getColumn(column_name[i]).setHeaderValue(column_title[i]);
                }catch(Exception e){
                    System.out.println("������ ��� ��������� �������� ��� �������"+column_name[i]);
                }
            }else{
                System.out.println("��������� ����������� ��������");
            }
        }
    }
    /** ����������� ����� ��� ��������� ���������� �������� � "�������" ������� 
     * @param table - �������, � ������� ����� ����������� ���������
     * @param titles - ��������� ��� �������
     */
    public static void set_column_titles(JTable table,
                                         String[] titles){
        for(int counter=0;counter<table.getColumnCount();counter++){
            try{
                table.getColumnModel().getColumn(counter).setHeaderValue(titles[counter]);
            }catch(Exception e){
                System.out.println("setColumnTitles Exception:"+e.getMessage());
            }
        }
    }
    
    /**
     * �����, ������� ��������� � ��������� ���� (INI file) ������ ��� ���� �������� �������
     * @param table �������, � ������� ����� ������� ������
     * @param path_to_file ���� � �����
     * @param prefix_name ���������, ������� ����� ������ ������ � ���� prefix_name+"."+field_name
     */
    public static void save_column_width_and_position_to_ini(JTable table,
                                                             String path_to_file,
                                                             String section_name){
        com.cherkashin.vitaliy.inifiles.IniFile inifile=new com.cherkashin.vitaliy.inifiles.IniFile(path_to_file);
        for(int counter=0;counter<table.getColumnCount();counter++){
            try{
                String key_name=table.getColumnName(counter);
                String value_name=Integer.toString(table.getColumn(counter).getMinWidth());
                inifile.setValue(section_name,
                                 key_name,
                                 value_name);
            }catch(Exception e){
                System.out.println("INI file error in save "+e.getMessage());
            };
        }
        inifile.Update();
    }
    /**
     * �����, ������� ������ ������ �� ����� (INI) � ������ ����������� � �������� �������
     * @param table ������� � ������� ����� ����������� �������
     * @param path_to_file ���� � ����� � ������� ��������� ������
     * @param section_name ��� ������, � ������� �������� ������
     */
    public static void get_column_width_and_position_from_ini(JTable table,
                                                              String path_to_file,
                                                              String section_name){
        com.cherkashin.vitaliy.inifiles.IniFile inifile=new com.cherkashin.vitaliy.inifiles.IniFile(path_to_file);
        for(int counter=0;counter<table.getColumnCount();counter++){
            try{
                table.getColumn(counter).setMinWidth(Integer.parseInt(inifile.getValue(section_name,
                                                                                       table.getColumnName(counter),
                                                                                       "50")
                                                                      )
                                                     );
            }catch(Exception e){
                System.out.println("Ini file error in read");
            }
        }
        
    }
}
















