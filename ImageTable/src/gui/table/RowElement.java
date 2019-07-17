package gui.table;

import java.awt.Component;

import java.awt.GridLayout;
import javax.swing.JPanel;

/** �������, ������� �������� ����������� � ���������� ������������� */
public class RowElement {
	/** ������, ���������� ���������� ������������� */
	private String field_unique_identifier;
	/** ������, ���������� ����������� */
	private ImageIdentifier[] field_image_identifier;
	/** ������, ������� �������� ������� ����������� �������� */
	//private JPanel field_panel;
	/** ������, ������� �������� ��������� ����������� �������� */
	private JPanel field_panel_thumb;
	/** ������� ����� ������ ����������� �� ������ */
	private int field_border_space_full=2;
	private int field_border_space_thumb=10;
	
	
	/** ������� �� ������ �������
	 * @param unique_identifier - ���������� �������������
	 * @param identifier �������������� ����������� 
	 * 
	 * */
	public RowElement(String unique_identifier, ImageIdentifier ... identifiers){
		this.field_unique_identifier=unique_identifier;
		this.field_image_identifier=identifiers;
		
		// create panel with big image
		//this.field_panel=createPanel();
		// create panel with thumb image
		this.field_panel_thumb=createPanelThumb();
	}
	
	/** return panel with empty border */
	private JPanel getWrapPanel(Component component,int top, int left, int bottom, int right){
		JPanel return_value=new JPanel();
		return_value.setBorder(javax.swing.BorderFactory.createEmptyBorder(top, left, bottom, right));
		return_value.setLayout(new GridLayout(1,1));
		return_value.add(component);
		return return_value;
	}
	
	/** create JPanel � �������� ���������� */
	private JPanel createPanel(){
		JPanel return_value=new JPanel();
		for(int counter=0;counter<this.field_image_identifier.length;counter++){
			return_value.add(this.getWrapPanel(this.field_image_identifier[counter].getLabel(), 
											   this.field_border_space_full, 
											   this.field_border_space_full, 
											   this.field_border_space_full, 
											   this.field_border_space_full)
							 );
		}
		return return_value;
	}
	
	
	/** create JPanel � ���������� ���������� */
	private JPanel createPanelThumb(){
		JPanel return_value=new JPanel();
		for(int counter=0;counter<this.field_image_identifier.length;counter++){
			return_value.add(this.getWrapPanel(this.field_image_identifier[counter].getLabelThumb(), 
											   this.field_border_space_thumb, 
											   this.field_border_space_thumb, 
											   this.field_border_space_thumb, 
											   this.field_border_space_thumb) 
							 );
		}
		return return_value;
	}
	
	/** �������� �� equals ����������� �������������� 
	 * @param value ������������� ��� �������� 
	 * @return true - ���� �������������� ��������� 
	 * */
	public boolean isUniqueIdentifierEquals(String value){
		return field_unique_identifier.equals(value);
	}
	
	/** ���������� ����������� ������, �� ������� ����������� ����������� � ���� ������ */
	public JPanel getPanelThumb(){
		return this.field_panel_thumb;
	}
	/** ���������� ����������� ������, �� ������� ����������� ������� ����������� */
	public JPanel getPanel(){
		return this.createPanel();
		//return this.field_panel;
	}
	
	/** ���������� ���������� ������������� */
	public String getUniqueIdentifier(){
		return this.field_unique_identifier;
	}
}
