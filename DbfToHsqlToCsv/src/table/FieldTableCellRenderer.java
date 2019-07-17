package table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import common.Field;


public class FieldTableCellRenderer implements TableCellRenderer{

	private Color defaultColor=new Color(238,238,238);
	private Color selectedColor=Color.LIGHT_GRAY;
	private Color focusColor=Color.gray;
	
	/** renderer for JTable 
	 * <b>defaultColor</b> - ���� �� ��������� - new Color(238,238,238)  
	 * <b>selected</b> - ���� ��������� Color.LIGHT_GRAY
	 * <b>focus</b> - ���� ������ Color.GRAY
	 */
	public FieldTableCellRenderer(){
	}

	/** renderer for JTable 
	 * @param defaultColor - ���� �� ��������� 
	 * @param selected - ���� ��������� 
	 * @param focus - ���� ������ 
	 */
	public FieldTableCellRenderer(Color defaultColor, Color selected, Color focus){
		this.defaultColor=defaultColor;
		this.selectedColor=selected;
		this.focusColor=focus;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, 
												   Object value,
												   boolean isSelected, 
												   boolean hasFocus, 
												   int row, 
												   int column) {
		try{
			Field field=(Field)value;
			field.setComponentBackground(this.defaultColor);
			if(isSelected){
				field.setComponentBackground(this.selectedColor);
			};
			if(hasFocus){
				field.setComponentBackground(this.focusColor);
			}
			return field.getJComponent();
		}catch(Exception ex){
			System.err.println("Table cell Renderer Exception:"+ex.getMessage());
			return null;
		}
	}

}
