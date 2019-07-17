/*
 * Decode_HTML_Table.java
 *
 * Created on 13 ������ 2008, 17:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package html_table_decode;
import java.util.ArrayList;
import javax.swing.table.*;
/**
 *
 * @author Technik
 */
public class Decode_HTML_Table {
    private String field_column_begin;
    private String field_column_end;
    private String field_row_begin;
    private String field_row_end;
    private StringBuffer field_data;
    private TableModel field_table_model;
    /** 
     * @param row_begin
     * @param row_end
     * @param column_begin
     * @param column_end
     */
    public Decode_HTML_Table(String row_begin,
                             String row_end,    
                             String column_begin,
                             String column_end,
                             StringBuffer data) {
        this.field_column_begin=column_begin;
        this.field_column_end=column_end;
        this.field_row_begin=row_begin;
        this.field_row_end=row_end;
        this.field_data=data;
        create_table_model();
    }
    
    /** �������� ��������� ������ �� �������
     * @param begin_position ������ ������������
     * @param value ������, �� ������� ���������� ������� ������
     * @return ������ <String> ��� <null>
     */
    private String get_next_column(String value,int begin_position){
        if(begin_position>=value.length()){
            return null;
        }else{
            int column_begin=value.indexOf(this.field_column_begin,begin_position);
            int column_end=value.indexOf(this.field_column_end,column_begin);
            if((column_begin>=0)&&(column_begin<column_end)){
                return value.substring(column_begin+this.field_column_begin.length(),column_end);
            }else{
                if(column_begin>=0){
                    return null;//value.substring(column_begin+this.field_column_begin.length());
                }else{
                    return null;
                }
            }
        }
    }
    /** �������� ArrayList<String> �� ������ - ������ �� ��������*/
    private ArrayList<String> get_columns(String value){
        ArrayList<String> return_value=new ArrayList();
        String current_value;
        int current_position=0;
        while((current_value=get_next_column(value,current_position))!=null){
            return_value.add(current_value);
            //System.out.println(current_value);
            current_position+=this.field_column_begin.length()+current_value.length()+this.field_column_end.length()-1;
        }
        return return_value;
    }
    
    /** ���������� ��������� ������ � ���� ArrayList<String>*/
    private ArrayList<String> get_next_row(){
        ArrayList<String> return_value=new ArrayList();
        // �������� ������� ������ ������
        int row_begin=this.field_data.indexOf(this.field_row_begin);
        int row_end=this.field_data.indexOf(this.field_row_end);
        if(row_begin<row_end){
            // ���������� �������  
            return_value=this.get_columns(this.field_data.substring(row_begin+this.field_row_begin.length(),row_end));
            this.field_data.delete(0,row_end+this.field_row_end.length());
        }else{
            // ������������ ������� - ��������� ������������, ���� row_begin>0
            if(row_begin>=0){
                return_value=null;//this.get_columns(this.field_data.substring(row_begin));
            }else{
                return_value=null;
            }
        }
        // �������� ������� ����� ������
        return (return_value.size()==0)?null:return_value;
    }
    /** �������� ������ ��� �������*/
    private void create_table_model(){
        // �������� ArrayList �� Array_list<String> � ������� ����� ������ ������
        ArrayList<ArrayList> row_array_list=new ArrayList();
        ArrayList<String> current_row_list;
        while((current_row_list=this.get_next_row())!=null){
           row_array_list.add(current_row_list);
        };
        this.field_table_model=new arraylist_table_model(row_array_list);
    }
    
    public TableModel get_table_model(){
        return this.field_table_model;
    }
    
    // ���������� �����, implements TableModel
    class arraylist_table_model extends DefaultTableModel{
        /** ������ �� ��������� ������� �������� ������*/
        private ArrayList field_data;
        /** ���-�� �����*/
        private int field_row_count;
        /** ���-�� ��������*/
        private int field_column_count;
        /** 
         * �������� ������ ������ �� ��������� ���������
         * @param data - ArrayList<ArrayList<String>>
         */
        arraylist_table_model(ArrayList data){
            super();
            this.field_data=data;
            calculate_limits();
        };
        /** ���������� ������������� ���-�� ����� � ��������*/
        private void calculate_limits(){
            this.field_row_count=this.field_data.size();
            this.field_column_count=0;
            for(int counter=0;counter<this.field_row_count;counter++){
                if(((ArrayList)this.field_data.get(counter)).size()>this.field_column_count){
                    this.field_column_count=((ArrayList)this.field_data.get(counter)).size();
                }
            }
        }
        
        public int getColumnCount(){
            return this.field_column_count;
        };
        public Class getColumnClass(int columnIndex){
            return String.class;
        };
        public int getRowCount(){
            return this.field_row_count;
        };
        public String getColumnName(int columnIndex){
            return Integer.toString(columnIndex);
        };
        public Object getValueAt(int row_index,int columnIndex){
            return ((ArrayList)this.field_data.get(row_index)).get(columnIndex);
        };
        
    }
}











