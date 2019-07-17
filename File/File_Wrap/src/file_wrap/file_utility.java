/*
 * file_utility.java
 *
 * Created on 21 ������ 2008, 10:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package file_wrap;
import java.io.*;
/**
 * ������� ��� File
 */
public class file_utility {
    private File field_file;
    private String field_path_to_file;
    /** 
     * @param path_to_file ���� � �����
     */
    public file_utility(String path_to_file) {
        this.field_file=new File(path_to_file);
        this.field_path_to_file=path_to_file;
    }
    
    /**
     * ��������� �� ������������� �����
     */
    public boolean is_file_exists(){
        return this.field_file.exists();
    }
    /** 
     * �������� ���� � ����� (������ ��������)
     */
    public String get_directory(){
        String return_value=this.field_file.getAbsolutePath();
        return return_value.substring(0,return_value.length()-this.field_file.getName().length());
    }
    /** 
     * �������� ��� ����� � �����������
     */
    public String get_filename(){
        return this.field_file.getName();
    }
    /**
     * �������� ��� ����� ��� ����������
     */
    public String get_filename_without_ext(){
        String return_value=this.field_file.getName();
        int dot_position=return_value.lastIndexOf(".");
        if(dot_position>=0){
            return return_value.substring(0,dot_position);
        }else{
            return return_value;
        }
    }
    /** 
     * �������� ���������� ������
     */
    public String get_extension(){
        String return_value=this.field_file.getName();
        int dot_position=return_value.lastIndexOf(".");
        if(dot_position>=0){
            return return_value.substring(dot_position+1);
        }else{
            return "";
        }
    }
    /** 
     * �������� ������ ���� � �����
     */
    public String get_full_path(){
        return this.field_file.getAbsolutePath();
    }
    /** 
     * �������� ��� �����, � ������� ����� �������� ������, ��� ��� ���� �� ������ ������������
     * @param path
     * @param sufix
     * ���� ������� �� (path)+(filename)+(sufix)+(number)+(extension)
     */
    public static String get_next_filename(String path,String sufix){
        int counter=1;
        file_utility file=new file_utility(path);
        String directory=file.get_directory();
        String file_name=file.get_filename_without_ext();
        String file_ext=file.get_extension();
        
        while (file.is_file_exists()==true){
            file=new file_utility(directory+file_name+sufix+counter+"."+file_ext);
        }
        return file.get_full_path();
    }
}











