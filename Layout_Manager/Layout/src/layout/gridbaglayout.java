/*
 * frame.java
 *
 * Created on 6 ������ 2008, 18:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package layout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.border.BevelBorder;
/**
 * ������ ������������� BagGridLayout
 *
 */
public class gridbaglayout extends JFrame{
    private JPanel panel_main;
    private JPanel panel_filter;
    private JPanel panel_manager;
    private JPanel panel_view;
    private JButton button_set_filter;
    private JButton button_remove_filter;
    private GridBagLayout grid_bag_layout;
    private GridBagConstraints grid_bag_constraints;
    
    public void make_button(String name,GridBagLayout gridbaglayout,GridBagConstraints gridbagconstraints,JPanel destination){
        JButton button=new JButton(name);
        gridbaglayout.setConstraints(button,gridbagconstraints);
        destination.add(button);
    }
    
    /** Creates a new instance of frame */
    public gridbaglayout() {
        super("frame");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel_main=new JPanel();
        panel_filter=new JPanel();
        panel_manager=new JPanel();
        panel_view=new JPanel();
        button_set_filter=new JButton("set");
        button_remove_filter=new JButton("remove");
        grid_bag_layout=new GridBagLayout();
        grid_bag_constraints=new GridBagConstraints();
        panel_main.setLayout(grid_bag_layout);        

        //���������� ������ ������ ������� ��� ������
        grid_bag_constraints.gridwidth=1;
        //���������� ������ ������ ������� ��� ������
        grid_bag_constraints.gridheight=1;
        //��������� ������������ ������ ��� ������������� � ������ ������
        grid_bag_constraints.gridx=GridBagConstraints.RELATIVE;
        // ���������� ������ ������ � ���� ��������� ���������� ������� 
        grid_bag_constraints.weightx=1;
        // ���������� ������ ������ � ���� ��������� ���������� �������
        grid_bag_constraints.weighty=1;
        // ���������� ���������� ������ � ������� - ������������ ������������
        grid_bag_constraints.fill=GridBagConstraints.VERTICAL;
        // ���������� ����� �� ������ (��������� ��������� � �������� �������)
        grid_bag_constraints.anchor=GridBagConstraints.WEST;
        make_button("set_button",grid_bag_layout,grid_bag_constraints,panel_main);
        
        // ���������� ��������� ��������� � ������
        grid_bag_constraints.gridx=GridBagConstraints.RELATIVE;
        // ��������� ��������� �� ����������� � �������
        grid_bag_constraints.fill=GridBagConstraints.HORIZONTAL;
        // ���������� ����� �� ������ (��������� ��������� � �������� �������)
        grid_bag_constraints.anchor=GridBagConstraints.WEST;
        make_button("remove_button",grid_bag_layout,grid_bag_constraints,panel_main);

        // ���������� ��������� ��������� � ������
        grid_bag_constraints.gridx=GridBagConstraints.REMAINDER;
        // ���������� ��� ���������� �������������� ������������
        grid_bag_constraints.fill=GridBagConstraints.BOTH;
        // ���������� ����� ��� ���������� �� �������
        grid_bag_constraints.anchor=GridBagConstraints.EAST;
        // ���������� ������ ���������� ����� ��� ���������� � ���� ���� �����
        grid_bag_constraints.gridwidth=2; 
        make_button("clear_button",grid_bag_layout,grid_bag_constraints,panel_main);
        
        panel_main.setBorder(javax.swing.BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        this.getContentPane().add(panel_main);
        //this.pack();
        setVisible(true);
    }
    
}
