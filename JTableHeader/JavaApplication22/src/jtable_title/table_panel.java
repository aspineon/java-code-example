/*
 * table_panel.java
 *
 * Created on 28 ������ 2008, 15:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jtable_title;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 *
 * @author Technik
 */
public class table_panel extends JPanel{
    /** �������*/
    private JTable field_table;
    /** ������ � �����������*/
    private JPanel field_panel_title;
    /** ������ �� ���������*/
    private java.awt.Cursor field_default_cursor=this.getCursor();
    /** ������ ��� ��������������*/
    private java.awt.Cursor field_drag_cursor=new java.awt.Cursor(java.awt.Cursor.W_RESIZE_CURSOR);
    /** ���� �������������� */
    private boolean field_drag_enabled=false;
    /** ������ ������*/
    private TableModel field_table_model;
    /** ������ ��������� ������*/
    private title_panel field_title_panel;
    /** ��������� ��� ������� ������ Caption*/
    private String[] field_column_caption;
    /** ����������� ��� ��������*/
    private int[] field_column_width;

    /** 
     * �������������� �������� Title_panel - �� �����
     * @param table_model - ������ ������
     * @param column_caption - ��������� ��� �������
     * @param width - ������ �� �������� ������
     */
    public table_panel(TableModel table_model,String[] column_caption,int[] width) {
        super();
        this.field_table_model=table_model;
        this.field_column_caption=column_caption;
        this.field_column_width=width;
        //this.field_table.updateUI();
        this.create_and_place_components(true);
    }
    
    /** 
     * �������� ������ � ����������� �� ��������� ������ c ���������� � ������ 
     *
     */
    public table_panel(JTable table,title_panel panel_with_title) {
        super();
        this.field_table=table;
        this.field_title_panel=panel_with_title;
        this.create_and_place_components(false);        
    }
    
    private MouseListener get_mouse_listener(){
        return new MouseListener(){
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if(field_title_panel.is_dragged_object(e.getSource(),e.getX())!=null){
                    field_drag_enabled=true;
                    setCursor(field_drag_cursor);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if(field_drag_enabled==true){
                    field_title_panel.set_dragged_object_width(e.getSource(),e.getX());
                    field_table.updateUI();
                    field_drag_enabled=false;
                    setCursor(field_default_cursor);
                }
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
            
        };
    }
    
    
    /** create and place components */
    private void create_and_place_components(boolean autocreate_title){
        // create component's
        
        
        if(autocreate_title){
            this.field_table=new JTable(this.field_table_model);
            this.field_table.setTableHeader(null);
            this.field_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            this.field_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            this.field_title_panel=new title_panel(this.field_table.getColumnModel(),this.field_column_caption,this.field_column_width);
        }
        //this.field_title_panel.reconstruct_column();
        // set listener's
        this.field_title_panel.add_mouse_listener(this.get_mouse_listener());        
        
        // set layout
        this.setLayout(new BorderLayout());
        this.add(this.field_title_panel,BorderLayout.NORTH);
        this.add(this.field_table,BorderLayout.CENTER);
    }
    
}

