package bc.data_terminal.editor.database;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;


/** �����, ������� �������� �������� ��������� XML �����, ����������� ��� ����������� XML ��������� � �� �������� �������������� �� HTML � XML */
public class ExchangeFileHeadElement{
	private Logger field_logger=Logger.getLogger(this.getClass());
	{
		field_logger.setLevel(Level.DEBUG);
	}
	
	private String field_name;
	private ExchangeFileHeadAttribute[] field_attributes;
	
	
	public ExchangeFileHeadElement(String tagName, ExchangeFileHeadAttribute ... attributes){
		this.field_name=tagName;
		this.field_attributes=attributes;
	}
	
	public void setName(String value){
		this.field_name=value;
	}
	public void setAttributes(ExchangeFileHeadAttribute[] values){
		this.field_attributes=values;
	}
	
	/** �������� ��������� ��� ������� �������� */
	public String getName(){
		return this.field_name;
	}
	
	/** �������� HTML ����� ��� ������� ��������*/
	public StringBuffer getHtml(){
		StringBuffer return_value=new StringBuffer();
		return_value.append("<TABLE id=\""+field_name+"\" border=1 >");
		return_value.append("	<TR>");
		return_value.append("	<TH>");
		return_value.append("	<CENTER>");
		return_value.append(this.field_name);
		return_value.append("	</CENTER>");
		return_value.append("	</TH>");
		return_value.append("	</TR>");
		try{
			for(int counter=0;counter<this.field_attributes.length;counter++){
				return_value.append("<TR>");
				return_value.append(this.field_attributes[counter].getHtml());
				return_value.append("</TR>");
			}
		}catch(NullPointerException ex){
			field_logger.error("getHTML():"+ex.getMessage());
		}
		return_value.append("</TABLE>");
		return return_value;
	}
	
	/** �������� HTML ����� ��� ��������� ��������, �� ��������� ��� ��������� ������ 
	 * @param node - �������, ������� �������� ����������� ������ 
	 * */
	public StringBuffer getHtml(Node node){
		StringBuffer return_value=new StringBuffer();
		return_value.append("<TABLE id=\""+field_name+"\" border=1 >");
		return_value.append("	<TR>");
		return_value.append("	<TH>");
		return_value.append("	<CENTER>");
		return_value.append(this.field_name);
		return_value.append("	</CENTER>");
		return_value.append("	</TH>");
		return_value.append("	</TR>");
		Attr attribute_value=null;
		try{
			for(int counter=0;counter<this.field_attributes.length;counter++){
				return_value.append("<TR>");
				
				try{
					attribute_value=null;
					attribute_value=(Attr)node.getAttributes().getNamedItem(this.field_attributes[counter].getName());
				}catch(Exception ex){
					
				}
				return_value.append(this.field_attributes[counter].getHtml(attribute_value));
				return_value.append("</TR>");
			}
		}catch(NullPointerException ex){
			field_logger.error("getHTML():"+ex.getMessage());
		}
		return_value.append("</TABLE>");
		return return_value;
	}
	
	
	/** �������� HTML ����� ��� ������� �������� � ������������ �������� ������� �������� */
	public StringBuffer getHtmlWithManager(String html_id){
		StringBuffer return_value=new StringBuffer();
		return_value.append("<TABLE id=\""+html_id+"\" border=1 >");
		return_value.append("	<TR>");
		return_value.append("	<TH>");
		return_value.append("	<CENTER>");
		return_value.append(this.field_name);
		return_value.append("	</CENTER>");
		return_value.append("	</TH>");
		return_value.append("	</TR>");
		try{
			for(int counter=0;counter<this.field_attributes.length;counter++){
				return_value.append("<TR>");
				return_value.append(this.field_attributes[counter].getHtml());
				return_value.append("</TR>");
			}
		}catch(NullPointerException ex){
			field_logger.error("getHTML():"+ex.getMessage());
		}
		return_value.append("</TABLE>");
		return_value.append("<INPUT TYPE=\"button\" value=\"delete\" id=\"button_"+html_id+"\" onClick=\"delete_parent_element('"+html_id+"')\" >");
		return return_value;
	}

	/** �������� HTML ����� ��� ������� �������� � ������������ �������� ������� �������� */
	public StringBuffer getHtmlWithManager(String html_id,Node node){
		StringBuffer return_value=new StringBuffer();
		return_value.append("<TABLE id=\""+html_id+"\" border=1 >");
		return_value.append("	<TR>");
		return_value.append("	<TH>");
		return_value.append("	<CENTER>");
		return_value.append(this.field_name);
		return_value.append("	</CENTER>");
		return_value.append("	</TH>");
		return_value.append("	</TR>");
		Attr attribute_value=null;
		try{
			for(int counter=0;counter<this.field_attributes.length;counter++){
				return_value.append("<TR>");
				try{
					attribute_value=null;
					attribute_value=(Attr)node.getAttributes().getNamedItem(this.field_attributes[counter].getName());
				}catch(Exception ex){
					
				}
				return_value.append(this.field_attributes[counter].getHtml(attribute_value));
				return_value.append("</TR>");
			}
		}catch(NullPointerException ex){
			field_logger.error("getHTML():"+ex.getMessage());
		}
		return_value.append("</TABLE>");
		return_value.append("<INPUT TYPE=\"button\" value=\"delete\" id=\"button_"+html_id+"\" onClick=\"delete_parent_element('"+html_id+"')\" >");
		return return_value;
	}

	/** �������� ������������� �������� ������� ��������� 
	 * �������� �������� ������� ���������, ���� ���������� ��� - ���������� ������ ������
	 * */
	public String getValueOfFirstAttribute(){
		String return_value="";
		if(this.field_attributes.length>0){
			return_value=this.field_attributes[0].getCurrent_value();
			if(return_value==null){
				return_value="";
			}
		}
		return return_value;
	}
}
