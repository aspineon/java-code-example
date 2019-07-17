package database;
import java.sql.*;
import java.util.Calendar;

import javax.swing.JComboBox;

/** �����, ������� �������� static public method ��� ������ � ����� ������ */
public class Utility {
	public static String[] monthName=new String[]{"������", "�������","����","������","���","����","����","������","��������","�������","������","�������"};
	
	public static java.sql.Date getSqlDate(int day, int month, int year){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		java.util.Date date=calendar.getTime();
		return new java.sql.Date(date.getTime());
	}
	
	
    /** 
     * ��������� JComboBox �� ��������� ResultSet � ����� ���� �� ����� ResultSet
     * @param combobox ���������� �������, ������� ����� ���������
     * @param resultset ����� ������ ������ ���������� �� ����� ������ ������ (beforeFirst)
     * @param fieldname ��� ���� � ������� �� ResultSet, �������� ����� �������� Combobox
     * @param with_empty ����, ������� ������� � ������������� ���������� � ������ ������ ������
     * @result TRUE ���� ��� ������ �������, FALSE - ���������� ������ ��������
     */
    public static boolean fill_combobox_from_resultset(JComboBox combobox,ResultSet resultset,String fieldname,boolean with_empty){
        boolean return_value=false;
        try{
            if(combobox!=null){
                combobox.removeAllItems();
                if((resultset!=null)&&(fieldname!=null)&&(!fieldname.trim().equals(""))){
                    if(with_empty){
                        combobox.addItem("");
                    }
                    while(resultset.next()){
                        combobox.addItem(resultset.getString(fieldname));
                    }
                }else{
                    // resultset==null or fielname==null or fieldname.equals("")
                }
            }else{
                // combobox is null
            }
        }catch(Exception ex){
            System.out.println("fill_combobox_from_resultset Exception:"+ex.getMessage());
        }
        return return_value;
    }
	
    /**
     * �������� �� ��������� ������� �������� ������ ����, ������� � �������� ��������� ������ 
     * @param connection - ������� ���������� � ����� ������ 
     * @param tableName - ��� ������� 
     * @param fieldName -��� ���� 
     * @param value - �������� ����
     * @param fieldDestination - ���� ��� ��������� �����������
     * @return ���������� ������ �� ���������� ���� ��� null � ������ ������
     */
    public static String getObjectFromTable(Connection connection,
    									    String tableName, 
    									    String fieldName, 
    									    Object value, 
    									    String fieldDestination){
    	String returnValue=null;
    	PreparedStatement statement=null;
    	ResultSet rs=null;
    	try{
    		statement=connection.prepareStatement("SELECT "+fieldDestination+" FROM "+tableName+" WHERE "+fieldName+"=?");
    		statement.setObject(1, value);
    		rs=statement.executeQuery();
    		if(rs.next()){
    			returnValue=rs.getString(fieldDestination);
    		}
    	}catch(Exception ex){
    		System.err.println("getObjectFromTable Exception:"+ex.getMessage());
    	}finally{
    		if(rs!=null){
    			try{
    				rs.close();
    			}catch(Exception ex){};
    		}
    		if(statement!=null){
    			try{
    				statement.close();
    			}catch(Exception ex){};
    		}
    	}
    	return returnValue;
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

}
