package bc.data_terminal.editor.transfer;

import java.io.File;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

/** �����, ������� ���������� � ������� DWR (JavaScript) � �������� ������ */
public class XmlExchange {
	static Logger field_logger;
	/** �������� ��������(������ ���������) �� �������� HTML */
	static String field_delimeter="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	static {
		field_logger=Logger.getLogger("XmlExchange");
	}
	
	/** �������� �������� �� ������ ������, ���������� ������� ����������� attribute_name="attribute_value" */
	protected String getAttributeFromString(String source, String attribute){
		String return_value="";
		int index=source.indexOf(attribute);
		if(index>=0){
			int index_end=source.indexOf("\"",index+attribute.length()+2);
			return_value=source.substring(index+2+attribute.length(),index_end);
		}
		return return_value;
	}

	/** �������� ������ �������� �� NODE ���� �� ������� null
	 * @return empty string, is attribute is not exists
	 * */
	protected String getFirstAttributeValue(Node node){
		if(node.hasChildNodes()){
			return ((Attr)node.getAttributes().item(0)).getValue(); 
		}else{
			return "";
		}
	}

	/** �������� ������ �������� �� NODE ���� �� ������� null
	 * @return empty string, is attribute is not exists
	 * */
	protected String getFirstAttributeKey(Node node){
		if(node.hasChildNodes()){
			return ((Attr)node.getAttributes().item(0)).getName(); 
		}else{
			return "";
		}
	}
	
	/** �������� XML ���� �� ���������� ����������� ���� */
	protected Document getXmlByPath(String path_to_xml){
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // ���������� ������������� ���������� Parser-��
        document_builder_factory.setValidating(false);
        try {
            // ��������� �����������
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            //print_file(path_to_file);
            return_value=parser.parse(new File(path_to_xml));
        }catch(Exception ex){
        	field_logger.error("XmlExchange.java ERROR");
        }
		return return_value;
		
	}

	/** 
	 * ��������� ������ Document �� ������� ��������� 
	 * @param doc �������� XML
	 * @param path_to_file ���� � �����, � ������� ����� ��������� ������
	 * */
	protected boolean saveXmlToFile(Document doc, String path_to_file){
		boolean return_value=false;
        try{
            javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
            javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
            javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(doc); // Pass in your document object here  
            java.io.FileWriter out=new java.io.FileWriter(path_to_file);
            //string_writer = new Packages.java.io.StringWriter();  
            javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
            transformer.transform(dom_source, stream_result);  
            //xml_string = string_writer.toString(); // At this point you could use the Servoy XMLReader plugin to read your XML String.  
            //application.output(xml_string);                  
            out.flush();
            out.close();
            return_value=true;
        }catch(Exception ex){
        	return_value=false;
        	field_logger.error("saveXmlToFile Exception:"+ex.getMessage());
        }
		return return_value;
	}

	
	/** ���������� � ��������� Node �������� �� ����������������� XML 
	 * @param pattern - Node from pattern
	 * @param user_node - Node from user_node
	 * @param attribute_name - ��� ���������
	 * */
	protected void setPatternNodeFromUserXML(Node pattern,
										   Node user_node,
										   String attribute_name){
		String visible_attribute;
		if(user_node!=null){
			// �������� �������� �� ����������������� XML
			try{
				//field_logger.debug("NodeName:"+user_node.getNodeName());
				//field_logger.debug("NodeValue:"+user_node.getNodeValue());
				//field_logger.debug("Attribute Length:"+user_node.getAttributes().getLength());
				visible_attribute=((Attr)(user_node.getAttributes().getNamedItem(attribute_name))).getValue();
				pattern.getAttributes().getNamedItem(attribute_name).setTextContent(visible_attribute);
/*				field_logger.debug("Visble attribute:"+visible_attribute);
				if (visible_attribute.equals(attribute_value)){
					// ���������� �������� � �������
					
				}else{
					// ���������� �������� � ������� �� ���������
					if(attribute_default!=null){
						pattern.getAttributes().getNamedItem(attribute_name).setTextContent(attribute_default);
					}
				}*/
			}catch(NullPointerException ex){
				// ���� �������� �� ������ � �����-���� �� ������
				field_logger.debug(attribute_name+" attribute: is not found ");
			};
		}
	}
	
	/** �������� �������� ��������� �� ��� ����� �� Node
	 * @param node �������, �� �������� ���������� �������� ��������
	 * @param attribute_name ��� ���������, ������� ����� ��������
	 * @return null ���� ��������� �����-���� ������, ���� �� �������� �� ���������� 
	 * */
	protected String getAttrFromNode(Node node, String attribute_name){
		String return_value=null;
		try{
			return_value=((Attr)node.getAttributes().getNamedItem(attribute_name)).getValue();
		}catch(Exception ex){
			return_value=null;
		}
		return return_value;
	}

}

















