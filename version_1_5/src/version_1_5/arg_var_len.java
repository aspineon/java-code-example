/*
 * arg_var_len.java
 *
 * Created on 26 ����� 2008, 0:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package version_1_5;

/**
 * ������������ �������� � ������ ���������� ���������� ������
 */
public class arg_var_len {
    /**
     * @param values - �������� � ���������� ������� 
     */
    private void example(int ... values){
        // ������� ���� ����������
        for(int counter:values){
            System.out.println(counter);
        }
    }
    
    private void example_object (Object ... values){
        for(Object object:values){
            System.out.println(object);
        }
    }
    
    /** Creates a new instance of arg_var_len */
    public arg_var_len() {
        this.example(10,20,30);
        this.example_object(new Integer(10),new Double(1.5),new String("hello"));
    }
    
}
