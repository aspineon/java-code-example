package bc.data_terminal.editor.database;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;

/** ������ ��� ��������� */
public class ExchangeFileHeadAttribute{
	private Logger field_logger=Logger.getLogger(this.getClass());
	{
		field_logger.setLevel(Level.DEBUG);
	}
	
	public static int TYPE_EDIT=0;
	public static int TYPE_COMBOBOX=1;
	
	/** ������������ ����*/
	private String field_name;
	/** �������� �� ������� ���� */
	private String[] field_values;
	/** ������� ��������, ������� ����� �������� */
	private String field_current_value;
	/** ��� ��������� */
	private int field_type;

	public ExchangeFileHeadAttribute(String name, String[] values){
		this.field_name=name;
		this.field_values=values;
		this.field_type=TYPE_COMBOBOX;
	}
	
	public ExchangeFileHeadAttribute(String name){
		this.field_name=name;
		this.field_values=null;
		this.field_type=TYPE_EDIT;
	}
	
	public boolean isEdit(){
		return (this.field_type==TYPE_EDIT);
	}
	
	public boolean isCombobox(){
		return (this.field_type==TYPE_COMBOBOX);
	}
	
	public String getName(){
		return this.field_name;
	}
	
	public String[] getValues(){
		return this.field_values;
	}
	
	/** �������� ������� �������� */
	public String getCurrent_value(){
		return this.field_current_value;
	}
	/** ���������� ������� �������� */
	public void setCurrent_value(String value){
		this.field_current_value=value;
	}
	
	public StringBuffer getHtml(){
		// TODO �������� HTML �� ���������
		StringBuffer return_value=new StringBuffer();
		if(this.isCombobox()){
			return_value.append("<b>");
			return_value.append(this.field_name);
			return_value.append("</b>");
			return_value.append("<select id=\""+this.field_name+"\"  >");
			try{
				for(int counter=0;counter<this.field_values.length;counter++){
					if((this.field_current_value!=null)&&(this.field_current_value.equals(this.field_values[counter]))){
						return_value.append("<option value=\""+this.field_values[counter]+"\" selected=\"selected\">");
						return_value.append(this.field_values[counter]);
						return_value.append("</option>");
					}else{
						return_value.append("<option value=\""+this.field_values[counter]+"\">");
						return_value.append(this.field_values[counter]);
						return_value.append("</option>");
					}
				}
			}catch(NullPointerException ex){
				field_logger.error("getHTML:"+ex.getMessage());
			}
			return_value.append("</select>");
		};
		if(this.isEdit()){
			return_value.append("<b>");
			return_value.append(this.field_name);
			return_value.append("</b>");
			return_value.append("<input type=\"text\" id=\""+this.field_name+"\" >");
		}
		return return_value;
	}
	
	/** ������� HTML ��� � ������������� ��������� � ���� HTML */
	public StringBuffer getHtml(Attr attribute){
		// TODO �������� HTML �� ���������
		StringBuffer return_value=new StringBuffer();
		String value=null;
		if(attribute!=null){
			value=attribute.getValue();
			if(value!=null){
				if(this.isCombobox()){
					return_value.append("<b>");
					return_value.append(this.field_name);
					return_value.append("</b>");
					return_value.append("<select id=\""+this.field_name+"\"  >");
					try{
						for(int counter=0;counter<this.field_values.length;counter++){
							if(this.field_values[counter].equals(value)){
								return_value.append("<option value=\""+this.field_values[counter]+"\" selected>");
							}else{
								return_value.append("<option value=\""+this.field_values[counter]+"\">");
							}
							return_value.append(this.field_values[counter]);
							return_value.append("</option>");
						}
					}catch(NullPointerException ex){
						field_logger.error("getHTML:"+ex.getMessage());
					}
					return_value.append("</select>");
				};
				if(this.isEdit()){
					return_value.append("<b>");
					return_value.append(this.field_name);
					return_value.append("</b>");
					return_value.append("<input type=\"text\" id=\""+this.field_name+"\" value=\""+value+"\">");
				}
			}else{
				return_value=getHtml();
			}
		}else{
			return_value=getHtml();
		}
		return return_value;
	}
	
}
