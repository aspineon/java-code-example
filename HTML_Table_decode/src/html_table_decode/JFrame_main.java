/*
 * JFrame_main.java
 *
 * Created on 13 ������ 2008, 17:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package html_table_decode;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;        
import java.io.*;
import javax.swing.table.TableModel;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame{
    /** ������ �� ������� ����� ��������� ��� ����������*/
    private JPanel field_panel_main;
    /** ������ ��������� ������ ������������*/
    private JButton field_button_scan;
    /** ������ ��������� �������� �� �������*/
    private JButton field_button_export;
    /** ���� � �����*/
    private JTextField field_textfield_path_scan;
    /** ���� � �����, � ������� ����� ������������ ����������*/
    private JTextField field_textfield_path_export;
    /** �������, � ������� ��������� ��� ������*/
    private JTable field_table_main;
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super();
        this.create_and_location_components();
        this.setBounds(100,100,500,500);
        this.setTitle("������������ ������� HTML �� �����");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
    }
    /** ������� � ���������� ����������*/
    private void create_and_location_components(){
        // ������� ����������
        this.field_button_scan=new JButton("Scan");
        this.field_button_export=new JButton("Export");
        this.field_panel_main=new JPanel(new BorderLayout());
        this.field_table_main=new JTable();
        this.field_textfield_path_scan=new JTextField("D:\\all_cartridge.csv");
        this.field_textfield_path_export=new JTextField("D:\\all_cartridge_export.csv");
        // ��������� ����������
        this.field_button_scan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                on_button_scan_click();
            }
        });
        this.field_button_export.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_export_click();
            }
        });
        // ���������� ����������
        JPanel panel_wrap_textfield=new JPanel(new GridLayout(1,1));
        panel_wrap_textfield.setBorder(javax.swing.BorderFactory.createTitledBorder("���� � ����� � HTML ��������"));
        panel_wrap_textfield.add(this.field_textfield_path_scan);
        JPanel panel_scan=new JPanel(new GridLayout(2,1));
        panel_scan.add(panel_wrap_textfield);
        panel_scan.add(this.field_button_scan);

        JPanel panel_wrap_textfield_export=new JPanel(new GridLayout(1,1));
        panel_wrap_textfield_export.setBorder(javax.swing.BorderFactory.createTitledBorder("���� � ��� ��������"));
        panel_wrap_textfield_export.add(this.field_textfield_path_export);
        JPanel panel_export=new JPanel(new GridLayout(2,1));
        panel_export.add(panel_wrap_textfield_export);
        panel_export.add(this.field_button_export);
        
        this.field_panel_main.add(panel_scan,BorderLayout.NORTH);
        this.field_panel_main.add(new JScrollPane(this.field_table_main),BorderLayout.CENTER);
        this.field_panel_main.add(panel_export,BorderLayout.SOUTH);
        this.getContentPane().add(this.field_panel_main);
    }
    /** ������� �� ������� ������ ������������*/
    private void on_button_scan_click(){
        // �������� �� ������������� �����
        File file=new File(this.field_textfield_path_scan.getText());
        if(file.exists()){
            try{
                // ������ ������ �� �����
                FileReader file_reader=new FileReader(file);
                BufferedReader buffered_reader=new BufferedReader(file_reader);
                StringBuffer string_buffer=new StringBuffer();
                String temp_string;
                while( (temp_string=buffered_reader.readLine())!=null){
                    string_buffer.append(temp_string);
                }
                file_reader.close();
                // ������� ������� �� ������ � �����                
                this.field_table_main.setModel((new Decode_HTML_Table("<tr","</tr>","<td","</td>",string_buffer)).get_table_model());
                this.field_table_main.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }catch(IOException iox){
                System.out.println("JFrame_main#on_button_scan_click: IOException:"+iox.getMessage());
                javax.swing.JOptionPane.showMessageDialog(this,"������ ������ ������ �� �����","������", JOptionPane.ERROR_MESSAGE);
            }catch(Exception ex){
                System.out.println("JFrame_main#on_button_scan_click: Exception:"+ex.getMessage());
                javax.swing.JOptionPane.showMessageDialog(this,"�� ������� ������������� ���� � �������","������", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            javax.swing.JOptionPane.showMessageDialog(this,"���� �� ��������","������",JOptionPane.ERROR_MESSAGE);
        }
    }
    /** ������� �� ������� ������� ��������*/
    private void on_button_export_click(){
        try{
            TableModel_to_ODF exporter=new TableModel_to_ODF();
            //exporter.set_valid_column(new int[]{0,1,2,3});
            //exporter.set_column_header(new String[]{"one","two","three","four"});
            exporter.to_calc(this.field_table_main.getModel(),"table");
        }catch(Exception ex){
            System.out.println("Export to ODF Exception:"+ex.getMessage());
        }
        
    }
}
