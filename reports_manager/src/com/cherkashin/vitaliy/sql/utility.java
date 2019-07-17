/*
 * utility.java
 *
 * Created on 9 ������ 2008, 9:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cherkashin.vitaliy.sql;
import java.sql.*;
import java.net.*;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import javax.swing.JComboBox;
/**
 * ����� ��� ������ ��������� ��������������� �������� ��� ���� ������
 */
public class utility {
    /** Creates a new instance of utility */
    public utility() {
    }
        /**
         * �������� �� ������� �� ���� ���� ��� ������������ 
         *(��� ������� �������, ���� ���� ���� �������� ������(������ �� ������))
         * @result ������������ ���������� ��������
         * @param connection - ���������� � �����, � �������� ����� ����������
         * @param table_name - ��� ������� � ���� ������, � ������� ����� ����� ��������
         * @param field_name_source - ��� ���� �� �������� ����� ������������� �����
         * @param value - ��������, �� �������� ����� ������������� �����
         * @param field_name_destination - ��� ����, ������� ����� ����������
         */
   public static String get_name_on_code(java.sql.Connection connection,
                                         String table_name,
                                         String field_name_source,
                                         String value,
                                         String field_name_destination){
        String return_value="";
        try{
            if((value!=null)&&(!value.trim().equals(""))){
                Statement statement=connection.createStatement();
                StringBuffer sql_text=new StringBuffer();
                sql_text.append("SELECT "+field_name_destination+" FROM "+table_name+" WHERE RUPPER("+field_name_source+")=");
                sql_text.append('\''+value.toUpperCase()+'\'');
                //System.out.println("text:"+sql_text.toString());
                ResultSet resultset=statement.executeQuery(sql_text.toString());
                if(resultset!=null){
                    // ������� �� ������ ������
                    if(resultset.next()){
                        return_value=resultset.getString(1);
                    }else{
                        return_value="";
                    }
                }else{
                    // ��� ������ ��� �����������
                }
                statement.close();
            }
        }catch(SQLException ex){
            System.out.println("utility.get_name_on_code SQL ������ ��� ���������� �������:"+ex.getMessage());
            while(ex.getNextException()!=null){
                System.out.println("utility.get_name_on_code SQL ������ ��� ���������� �������:"+ex.getMessage());
            }
        }
        catch(Exception e){
            
            System.out.println("utility.get_name_on_code ����� ������ ��� ���������� �������:"+e.getMessage());
        } 
        return return_value;
   };
   /**
    * �������� ������������ �������� ���� 
    * @result ���������� 0 � ������, ���� ��������� �������������� �������� ��� ���� �������� ���, ������ ��������, ���� �������� ����
    * @param connection ���������� � ����� ������
    * @param table_name ��� �������, � ������� ����� ���������� ������������ �������� �� ����
    * @param field_name ��� ���� � ������� � ������� ������������� �������� �� ����� ����
    */
    public static int get_max_int_field(Connection connection,String table_name,String field_name){
        int return_value=0;
        try{
            ResultSet resultset=(connection.createStatement()).executeQuery("SELECT MAX("+field_name+") FROM "+table_name);
            if(resultset!=null){
                resultset.next();
                return_value=resultset.getInt(1);
            }else{
                System.out.println("utility.get_max_int_field  ������ �� ������ ����������");
            }
        }catch(SQLException ex){
            System.out.println("utility.get_max_int_field ������ ���������� ������� "+ex.getMessage());
        }catch(Exception e){
            System.out.println("utility.get_max_int_field ������ ������ "+e.getMessage());
        }
        return return_value;
   }
   /**
    * ��������� JComboBox �� ������� �� ����
    * @param connection ������� ���������� � ����� ������
    * @param combobox JComboBox � ������� ����� ��������� �������
    * @param table_name ��� ������� � ���� ������
    * @param table_field ��� ���� � ������� ���� ������
    * @param order_by_field ���� ���������� � �������
    */
    public static boolean fill_combobox_from_table_from_field(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String order_by_field){
        boolean return_value=false;
        // ������� Combobox
        combobox.removeAllItems();
        try{
            // ��������� ������� � ���� ������
            java.sql.Statement statement=connection.createStatement();
            ResultSet resultset;
            if(!order_by_field.trim().equals("")){
                resultset=statement.executeQuery("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
            }else{
                resultset=statement.executeQuery("SELECT DISTINCT "+table_field+" FROM "+table_name);
            }
            // ���������� ������� JComboBox
            while(resultset.next()){
                combobox.addItem(resultset.getString(1));
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR");
        }
        return return_value;
    }
   /**
    * ��������� JComboBox �� ������� �� ���� � �������� ������ ������
    * @param connection ������� ���������� � ����� ������
    * @param combobox JComboBox � ������� ����� ��������� �������
    * @param table_name ��� ������� � ���� ������
    * @param table_field ��� ���� � ������� ���� ������
    * @param order_by_field ���� ���������� � �������
    */
    public static boolean fill_combobox_from_table_from_field_with_empty(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String order_by_field){
        boolean return_value=false;
        // ������� Combobox
        combobox.removeAllItems();
        try{
            // ��������� ������� � ���� ������
            java.sql.Statement statement=connection.createStatement();
            ResultSet resultset;
            if(!order_by_field.trim().equals("")){
                resultset=statement.executeQuery("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
            }else{
                resultset=statement.executeQuery("SELECT DISTINCT "+table_field+" FROM "+table_name);
            }
            // ���������� ������� JComboBox
            combobox.addItem("");
            while(resultset.next()){
                if(! (resultset.getString(1).trim().equals("")) ){
                    combobox.addItem(resultset.getString(1));
                }
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR:"+e.getMessage());
        }
        return return_value;
    }
    
   /**
    * ��������� JComboBox �� ������� �� ����
    * @param connection ������� ���������� � ����� ������
    * @param combobox JComboBox � ������� ����� ��������� �������
    * @param table_name ��� ������� � ���� ������
    * @param table_field ��� ���� � ������� ���� ������
    */
    public static boolean fill_combobox_from_table_from_field(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field){
        return fill_combobox_from_table_from_field(connection,combobox,table_name,table_field,"");
    }
   /**
    * ��������� JComboBox �� ������� �� ���� � �������� ������ ������
    * @param connection ������� ���������� � ����� ������
    * @param combobox JComboBox � ������� ����� ��������� �������
    * @param table_name ��� ������� � ���� ������
    * @param table_field ��� ���� � ������� ���� ������
    */
    public static boolean fill_combobox_from_table_from_field_with_empty(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field){
        return fill_combobox_from_table_from_field_with_empty(connection,combobox,table_name,table_field,"");
    }
    
   /**
    * ��������� JComboBox �� ������� �� ����
    * @param connection ������� ���������� � ����� ������
    * @param combobox JComboBox � ������� ����� ��������� �������
    * @param table_name ��� ������� � ���� ������
    * @param table_field ��� ���� � ������� ���� ������
    * @param not_in_list ������ �� String, ������� �� ������ ������� � �������
    */
    public static boolean fill_combobox_from_table_from_field(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String order_by_field,
                                                              String[] not_in_list){
        boolean return_value=false;
        // ������� Combobox
        combobox.removeAllItems();
        try{
            // ��������� ������� � ���� ������
            java.sql.PreparedStatement statement;
            ResultSet resultset;
            StringBuffer where_string=new StringBuffer();
            where_string.append("");
            // ������� ������ ����������
            if((not_in_list!=null)&&(not_in_list.length>0)){
                where_string.append(" WHERE RUPPER("+table_field+") NOT IN(\n");
                for(int counter=0;counter<not_in_list.length;counter++){
                    if(counter!=(not_in_list.length-1)){
                        where_string.append("?,\n");
                    }else{
                        where_string.append("?");
                    }
                }
                where_string.append(") ");
            }
            // ��������� �������
            if(!order_by_field.trim().equals("")){
                if(where_string.toString().equals("")){
                    //System.out.println("Where string:"+where_string.toString());
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString()+" ORDER BY "+order_by_field);
                    //System.out.println("Where string:"+where_string.toString());
                    for(int counter=0;counter<not_in_list.length;counter++){
                        //System.out.println(counter+"  : "+not_in_list[counter]);
                        statement.setString(counter+1,not_in_list[counter].toUpperCase());
                    }
                }
            }else{
                if(where_string.toString().equals("")){
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString());
                    for(int counter=0;counter<not_in_list.length;counter++){
                        System.out.println(counter+"  : "+not_in_list[counter]);
                        statement.setString(counter+1,not_in_list[counter].toUpperCase());
                    }
                }
            };
            resultset=statement.executeQuery();
            // ���������� ������� JComboBox
            while(resultset.next()){
                combobox.addItem(resultset.getString(1));
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR "+e.getMessage());
        }
        return return_value;
    }

   /**
    * ��������� JComboBox �� ������� �� ����
    * @param connection ������� ���������� � ����� ������
    * @param combobox JComboBox � ������� ����� ��������� �������
    * @param table_name ��� ������� � ���� ������
    * @param table_field ��� ���� � ������� ���� ������ ������� ����� ��������� � JComboBox
    * @param table_field_list ��� ���� � ������� ���� ������, �� �������� ����� ���������� �������� �� ������
    * @param in_list ������ �� String ������� ������ ������� � ������ �������
    * @param order_by_field ������ ��� �������������� �������
    */
    public static boolean fill_combobox_from_table_from_field(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String table_field_list,
                                                              String[] in_list,
                                                              String order_by_field){
        boolean return_value=false;
        // ������� Combobox
        combobox.removeAllItems();
        try{
            // ��������� ������� � ���� ������
            java.sql.PreparedStatement statement;
            ResultSet resultset;
            StringBuffer where_string=new StringBuffer();
            where_string.append("");
            // ������� ������ ����������
            if((in_list!=null)&&(in_list.length>0)){
                where_string.append(" WHERE RUPPER("+table_field_list+") IN(\n");
                for(int counter=0;counter<in_list.length;counter++){
                    if(counter!=(in_list.length-1)){
                        where_string.append("?,\n");
                    }else{
                        where_string.append("?");
                    }
                }
                where_string.append(") ");
            }
            // ��������� �������
            if(!order_by_field.trim().equals("")){
                if(where_string.toString().equals("")){
                    //System.out.println("Where string:"+where_string.toString());
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString()+" ORDER BY "+order_by_field);
                    //System.out.println("Where string:"+where_string.toString());
                    for(int counter=0;counter<in_list.length;counter++){
                        //System.out.println(counter+"  : "+in_list[counter]);
                        statement.setString(counter+1,in_list[counter].toUpperCase());
                    }
                }
            }else{
                if(where_string.toString().equals("")){
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString());
                    for(int counter=0;counter<in_list.length;counter++){
                        //System.out.println(counter+"  : "+in_list[counter]);
                        statement.setString(counter+1,in_list[counter].toUpperCase());
                    }
                }
            };
            resultset=statement.executeQuery();
            // ���������� ������� JComboBox
            while(resultset.next()){
                combobox.addItem(resultset.getString(1));
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR "+e.getMessage());
        }
        return return_value;
    }


    /**
    * ��������� JComboBox �� ������� �� ����
    * @param connection ������� ���������� � ����� ������
    * @param combobox JComboBox � ������� ����� ��������� �������
    * @param table_name ��� ������� � ���� ������
    * @param table_field ��� ���� � ������� ���� ������
    * @param not_in_list ������ �� String, ������� �� ������ ������� � �������
    */
    public static boolean fill_combobox_from_table_from_field_with_empty(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String order_by_field,
                                                              String[] not_in_list){
        boolean return_value=false;
        // ������� Combobox
        combobox.removeAllItems();
        try{
            // ��������� ������� � ���� ������
            java.sql.PreparedStatement statement;
            ResultSet resultset;
            StringBuffer where_string=new StringBuffer();
            where_string.append("");
            // ������� ������ ����������
            if((not_in_list!=null)&&(not_in_list.length>0)){
                where_string.append(" WHERE RUPPER(NAME) NOT IN(\n");
                for(int counter=0;counter<not_in_list.length;counter++){
                    if(counter!=(not_in_list.length-1)){
                        where_string.append("?,\n");
                    }else{
                        where_string.append("?");
                    }
                }
                where_string.append(") ");
            }
            // ��������� �������
            if(!order_by_field.trim().equals("")){
                if(where_string.toString().equals("")){
                    //System.out.println("Where string:"+where_string.toString());
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString()+" ORDER BY "+order_by_field);
                    //System.out.println("Where string:"+where_string.toString());
                    for(int counter=0;counter<not_in_list.length;counter++){
                        //System.out.println(counter+"  : "+not_in_list[counter]);
                        statement.setString(counter+1,not_in_list[counter].toUpperCase());
                    }
                }
            }else{
                if(where_string.toString().equals("")){
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString());
                    for(int counter=0;counter<not_in_list.length;counter++){
                        System.out.println(counter+"  : "+not_in_list[counter]);
                        statement.setString(counter+1,not_in_list[counter].toUpperCase());
                    }
                }
            };
            resultset=statement.executeQuery();
            // ���������� ������� JComboBox
            combobox.addItem("");
            while(resultset.next()){
                if(!(resultset.getString(1).trim().equals(""))){
                    combobox.addItem(resultset.getString(1));
                }
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR "+e.getMessage());
        }
        return return_value;
    }
    
    /**
     * �������� �� ���� ������ ���������� �� ��������� ��������������� ��������
     * @param connection ���������� � ����� ������
     * @param table_name ��� ������� � ���� ������
     * @param field_name ��� ��������� � ���� ������
     * @param value �������� ��������� � ���� ������
     * @param autocommit ����� �� ���������� ����������
     */
    public static boolean delete_on_table_on_field(java.sql.Connection connection,
                                                   String table_name,
                                                   String field_name,
                                                   String value,
                                                   boolean autocommit){
        boolean return_value=false;
        String query="DELETE FROM "+table_name+" WHERE RUPPER("+field_name+")=?";
        try{
            java.sql.PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1,value.toUpperCase());
            statement.executeUpdate();
            if(autocommit==true){
                connection.commit();
            }
            return_value=true;
        }catch(SQLException ex){
            System.out.println("utility.delete_on_table_on_field   SQL Exception "+ex.getMessage());
        }
        return return_value;
    }
    /**
     * �������� �� ���� ������ ���������� �� ��������� ��������������� ��������
     * @param connection ���������� � ����� ������
     * @param table_name ��� ������� � ���� ������
     * @param field_name ��� ��������� � ���� ������
     * @param value �������� ��������� � ���� ������
     * @param autocommit ����� �� ���������� ����������
     */
    public static boolean delete_on_table_on_field(java.sql.Connection connection,
                                                   String table_name,
                                                   String field_name,
                                                   int value,
                                                   boolean autocommit){
        boolean return_value=false;
        String query="DELETE FROM "+table_name+" WHERE "+field_name+"=?";
        try{
            java.sql.PreparedStatement statement=connection.prepareStatement(query);
            statement.setInt(1,value);
            statement.executeUpdate();
            if(autocommit==true){
                connection.commit();
            }
            return_value=true;
        }catch(SQLException ex){
            System.out.println("utility.delete_on_table_on_field   SQL Exception "+ex.getMessage());
        }
        return return_value;
    }
    /**
     * �����, ������� ������������ ������ INSERT ��� ����� � ��� ����� �����
     * @param connection ������� ���������� � ����� ������
     * @param table_name ������ ��� �������
     * @param fields_name ������ ��������������� ����� ����� � �������
     * @param values ������, ������� ����� �������� � ������ (Byte,Integer,Long,Float,String,Date)
     */
    public static boolean insert(java.sql.Connection connection,
                                 String table_name,
                                 String[] fields_name,
                                 Object[] values,
                                 boolean autocommit) throws Exception{
        boolean return_value=false;
        StringBuffer text_query=new StringBuffer();
        if(values.length==fields_name.length){
            // ���������� ������ �������
            text_query.append("INSERT INTO "+table_name+" (\n");
            for(int counter=0;counter<fields_name.length;counter++){
                if(counter==(fields_name.length-1)){
                    text_query.append(fields_name[counter]+")\n");
                }else{
                    text_query.append(fields_name[counter]+",\n");
                }
            }
            text_query.append("VALUES(\n");
            for(int counter=0;counter<fields_name.length;counter++){
                if(counter==(fields_name.length-1)){
                    text_query.append("?)\n");
                }else{
                    text_query.append("?,\n");
                }
            }
            // �������� PreparedStatement
            java.sql.PreparedStatement preparedstatement=connection.prepareStatement(text_query.toString());
            // ������� �������� ��� ����������
            for(int counter=0;counter<fields_name.length;counter++){
                preparedstatement.setObject(counter+1,values[counter]);
            }
            //System.out.println("utility.insert: \n"+text_query.toString()+"\n");            
            preparedstatement.executeUpdate();
            // ����������� ����������, ���� ��� ����������
            if(autocommit){
                connection.commit();
            }
            return_value=true;
        }else{
            throw new Exception("error in array dimension");
        }
        return return_value;
    }
    /**
     * �����, ������� ������������ ������ UPDATE ��� ������ � ����� �����
     * 
     */
    public static boolean update(java.sql.Connection connection,
                                 String table_name,
                                 String[] unique_fields_name,
                                 Object[] unique_values,
                                 String[] fields_name,
                                 Object[] values,
                                 boolean autocommit) throws Exception{
        boolean return_value=false;
        // ���������� ������ �������
        if((unique_fields_name.length==unique_values.length)&&(fields_name.length==values.length)){
            StringBuffer text_query=new StringBuffer();
            text_query.append("UPDATE "+table_name+"\n SET ");
            for(int counter=0;counter<fields_name.length;counter++){
                if(counter==(fields_name.length-1)){
                    text_query.append(fields_name[counter]+"=? \n");
                }else{
                    text_query.append(fields_name[counter]+"=?, \n");
                }
            }
            text_query.append("WHERE \n");
            for(int counter=0;counter<unique_fields_name.length;counter++){
                if(counter==(unique_fields_name.length-1)){
                    text_query.append(unique_fields_name[counter]+"=? \n");
                }else{
                    text_query.append(unique_fields_name[counter]+"=? AND \n");
                }
            }
            //System.out.println("UPDATE text query:"+text_query.toString());
            // ���������� PreparedStatement
            PreparedStatement prepared_statement=connection.prepareStatement(text_query.toString());
            // ������������ ����������
            for(int counter=0;counter<fields_name.length;counter++){
                prepared_statement.setObject(counter+1,values[counter]);
                //System.out.println((counter+1)+":"+values[counter]);
            }
            
            for(int counter=0;counter<unique_fields_name.length;counter++){
                prepared_statement.setObject(fields_name.length+counter+1,unique_values[counter]);
                //System.out.println((unique_fields_name.length+counter+1)+":"+unique_values[counter]);
            }
            // ��������� �������
            if(prepared_statement.executeUpdate()>0){
                if(autocommit){
                    connection.commit();
                }
                return_value=true;
            }else{
                return_value=false;
            }
        }else{
            // ������������ �������� ���������� 
            throw new Exception("parameters Error");
        }
        return return_value;
    }
    /**
     * �������������� ���� �� java.util.Date � ���� java.sql.Date
     */
    public static java.sql.Date convert_to_sql_date(java.util.Date value){
        java.text.SimpleDateFormat sql_date_format=new java.text.SimpleDateFormat("yyyy-MM-dd");
        try{
            return java.sql.Date.valueOf(sql_date_format.format(value));
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * �������������� ���� �� java.sql.Date � ���� java.util.Date
     */
    public static java.util.Date convert_to_util_date(java.sql.Date value){
        java.text.SimpleDateFormat sql_date_format=new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            return sql_date_format.parse(value.toString());
        } catch (ParseException ex) {
            return null;
        }
    }
    /**
     * �������������� ���� �� java.util.Date �� ��������� ����� java.sql.TimeStamp
     */
    public static java.sql.Timestamp convert_to_sql_timestamp(java.util.Date value){
        java.text.SimpleDateFormat sql_date_format=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try{
            return java.sql.Timestamp.valueOf(sql_date_format.format(value));
        }catch(Exception e){
            return null;
        }
    }
    /**
     * �������������� ���� �� java.sql.TimeStamp � ���� java.util.Date
     */
    public static java.util.Date convert_to_util_date(java.sql.Timestamp value){
        java.text.SimpleDateFormat sql_date_format=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            return sql_date_format.parse(value.toString());
        } catch (ParseException ex) {
            return null;
        }
    }

    
}













