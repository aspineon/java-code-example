/*
 * Frame_main.java
 *
 * Created on 9 ����� 2008, 9:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 *
 * @author Technik
 */
public class Frame_main extends JFrame{
    
    public void create_and_location_components(){
        // �������� ��������
        JPanel panel_main=new JPanel();
        panel_main.setLayout(new BorderLayout());
        JButton button_discover=new JButton("Discover");
        JButton button_search=new JButton("SearchService");
        // ���������� ����������
        button_discover.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_discover_click();
            }
        });
        button_search.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_search_service_click();
            }
        });
        // ������������ ��������
        panel_main.add(button_discover,BorderLayout.NORTH);
        panel_main.add(button_search,BorderLayout.SOUTH);
        this.add(panel_main);
    }
    /** Creates a new instance of Frame_main */
    public Frame_main() {
        super("Main form");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.create_and_location_components();
        this.setBounds(100,100,200,70);
        this.setVisible(true);
    }
    
    /** ����� �� ��������� ������� ������� �� ������ Discover*/
    public void on_discover_click(){
        System.out.println("Discover click:");
        new Discover();
    }
    /** ����� �� ������ ��������*/
    public void on_search_service_click(){
        System.out.println("Search click:");
        new SearchService();
    }
    
}
