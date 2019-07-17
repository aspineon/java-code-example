/*
 * Main.java
 *
 * Created on 1 ������ 2007, 15:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package javaapplication11;

/**
 *
 * @author ������
 */
class temp_box{
    private String s;
    temp_box(){
        this.s="temp_box";
    }
    temp_box(String s){
        this.s=s;
    }
    public String toString(){
        return this.s;
    }
    static void clear_array(char[] array_of_chars){
        for(int i=0;i<array_of_chars.length;i++){
            array_of_chars[i]=' ';
        }
    }
    static void print_bytes(byte[] array_of_bytes){
        for(int i=0;i<array_of_bytes.length;i++){
            System.out.print(i+" - "+array_of_bytes[i]+"; ");
        }
        System.out.println();
    }
}

public class Main {

    public Main() {
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("<<<Begin");
        // �����������
        char chars[]={'a','b','c','d','e','f','g','h'};
        String s=new String(chars);// toCharArray()
        System.out.println("�����������:"+s);
        
        String s2=new String("temp string");
        System.out.println("�����������:"+s2);
        
        String s3=new String(chars,3,chars.length-3);
        System.out.println("�����������:"+s3);

        // Concat - ���������� ������
        s3=s+s2;
        System.out.println(s3);
        // ����������� ����� ������ toString()
        temp_box box=new temp_box("method toString of <object> of <class temp_box>");
        System.out.println("toString():"+box);
        // ����� charAt()
        char ch="abc".charAt(1);
        System.out.println("charAt():"+ch);
        // ������� ������� char[]
        temp_box.clear_array(chars);
        String s4=new String(chars);
        System.out.println("Clear_chars:"+s4);
        
        // ����� getChars();
        char[] new_char={'1','2','3','4','5','6','7','8'};
        s2.getChars(1,3,new_char,0);
        String s5=new String(new_char);
        System.out.println("getChars():"+s5);
        char[] new_char_2=s5.toCharArray();
        System.out.println("toCharArray():"+(new String(new_char_2)));
        // ����� getBytes();
        byte[] array_of_byte;
        array_of_byte=s5.getBytes();
        System.out.print("getBytes():");temp_box.print_bytes(array_of_byte);
        // ����� equals();
        String s10=new String("hello");
        String s11=new String("hello");
        String s12=new String("HELLO");
        if(s10.equals(s11)){
            System.out.println(s10+" equals "+s11);
        }
        else {
            System.out.println(s10+" NOT equals "+s11);
        };
        if(s10.equals(s12)){
            System.out.println(s10+" equals "+s12);
        }
        else {
            System.out.println(s10+" NOT equals "+s12);
        };
        // ����� equalsIgnoreCase();
        if(s10.equalsIgnoreCase(s12)){
            System.out.println(s10+" equalsIgnoreCase "+s12);
        }
        else {
            System.out.println(s10+" NOT equalsIgnoreCase "+s12);
        }
        // ������ regionMatches - 
        if(s10.regionMatches(true,1,s12,1,2)){// <IgnoreCase>,<begin> find in source string, <source> find string, substr find string <begin> <end>
            System.out.println("Matches");
        }
        else {
            System.out.println(" Not matches ");
        }
        // ����� startWith()
        if(s10.startsWith("he")){
            System.out.println(s10+" startsWith "+"he");
        }
        else {
            System.out.println(s10+" NOT startsWith "+"he");
        };
        // ������ endWith()
        if(s10.endsWith("llo")){
            System.out.println(s10+" endsWith "+"llo");
        }
        else {
            System.out.println(s10+" NOT endsWith "+"llo");
        }
        // ����� compareTo()
        System.out.println(s10+" compareTo() "+s11+"   "+(s10.compareTo(s11)));
        System.out.println(s10+" compareTo() "+s12+"   "+(s10.compareTo(s12)));
        // ����� indexOf()
        System.out.println("indexOf():"+s10.indexOf("l"));
        System.out.println("indexOf():"+s10.indexOf("z"));
        // ����� lastIndexOf()
        System.out.println("lastIndexOf():"+s10.lastIndexOf("l"));
        System.out.println("lastIndexOf():"+s10.lastIndexOf("z"));
        // ����� substring()
        System.out.println("substring():"+s10.substring(2));
        System.out.println("substring():"+s10.substring(1,5));
        System.out.println("<<<End");
        // ����� concat()
        System.out.println("concat():"+s10.concat(s12));
        // ����� raplace()
        System.out.println("replace():"+s10.replace("l","L"));
        // ����� trim()
        System.out.println("trim():"+ (new String("          trim string      ")).trim() );
        // static ����� valueOf() - ������ ������ ������� toString() ��� ������� �������, ��� �������� ����
        System.out.println("valueOf():"+String.valueOf(box));
        System.out.println("valueOf():"+String.valueOf(s10));
        // ����� toLowerCase()
        System.out.println("toLowerCase():"+s12+"=>"+s12.toLowerCase());
        // ����� toUpperCase()
        System.out.println("toUpperCase():"+s10+"=>"+s10.toUpperCase());
    }
    
}
