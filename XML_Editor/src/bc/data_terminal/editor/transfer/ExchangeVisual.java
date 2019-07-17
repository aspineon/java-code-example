package bc.data_terminal.editor.transfer;

import java.util.StringTokenizer;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import bc.data_terminal.editor.database.DBFunction;



public class ExchangeVisual extends XmlExchange{

	/** ��������� XML ���� �� ������ � �������� ����� */
	public Data sendXmlToServer(Data data){
		field_logger.debug("get data Key:"+data.getKey());
		field_logger.debug("get data Value:"+data.getValue());

		Data return_value=new Data();
		if(data.getKey().equals("client_id")){
			// ��������� ������� - ������� ���� XML
			return_value.setKey("xml_body");
			String html_kod=this.getHtmlFromUserName(data.getValue()).toString();
			field_logger.debug(html_kod);
			return_value.setValue(html_kod);
		};
		if(data.getKey().equals("xml_body")){
			return_value.setKey("alert");
			//return_value.setValue(this.parseHtmlToXML(data.getValue()));
			return_value.setValue(this.parseHtmlToDataBase(data.getValue(),data.getValue2()));
		}
		return return_value;
	}

	/** 
	 * ��������� � ���� ������ ���������� (���� XML),���������� �� ������� � ���� HTML ������ 
	 * @param user_id - ��� ������������ ( ����� ��������� )
	 * @param data ������ � ���� HTML 
	 */
	private String parseHtmlToDataBase(String user_id, String data){
		String return_value="Error";
		Document doc=this.getXmlUser(user_id);
		if(doc!=null){
			StringTokenizer element=new StringTokenizer(data,"<");
			String current_value;
			String element_value;
			String element_task_id;
			while(element.hasMoreElements()){
				current_value=element.nextToken();
				if(current_value.startsWith("INPUT")){
					field_logger.debug("ELEMENT:"+current_value);
					// ����� �������� �������� � ������
					element_value=getAttributeFromString(current_value,"checked");
					// �������� �������� Task_id
					element_task_id=getAttributeFromString(current_value,"task_id");
					// ����� ���������� ������ � ����
					field_logger.warn("user_id:"+user_id+"     task_id:"+element_task_id+"    value:"+element_value);
					if(DBFunction.saveVisualXmlElement(user_id, element_task_id, element_value)){
						// save OK
					}else{
						field_logger.error("parseHtmlToDataBase save to DataBase Error ");
					}
				}else{
					// another element's
					field_logger.debug("ANOTHER_ELEMENT");
				}
			}
			
			return_value="Ok";
		}else{
			field_logger.error("parseHtmlToDataBase: user XML is not loaded:"+user_id);
		}
		return return_value;
	}
	
	
	/** ���������� ���������� ��� �� ������� ������������ HTML � XML (����� ������ � ������ �� ��� ������ ������) 
	 * ������� ��������� ��� ����������� ������������ � �������� ��������� � ������������� ��� ������������� ����������
	 * */
/*	private String parseHtmlToXML(String data){
		String return_value="OK";
		StringTokenizer element=new StringTokenizer(data,"<");

		// ����� ��� ��������� ���������-���������� �����
		//String path_to_xml="D:\\eclipse_workspace\\DataTerminal_Client\\XML_Files\\terminal_client.xml";
		//Document doc=this.getXmlByPath(path_to_xml);
		
		// ������������� HTML � XML 
		Document doc=this.getXmlPattern();
		NodeList node_list;
		Node node;
        
		String current_value;
		String element_name;
		String element_value;
		String element_task_id;
		while(element.hasMoreElements()){
			current_value=element.nextToken();
			if(current_value.startsWith("INPUT")){
				field_logger.debug("ELEMENT:"+current_value);
				// ����� ��� ������� � ������
				element_name=getAttributeFromString(current_value,"id");
				// ����� �������� �������� � ������
				element_value=getAttributeFromString(current_value,"checked");
				// �������� �������� Task_id
				element_task_id=getAttributeFromString(current_value,"task_id");
				field_logger.debug("ID:"+element_name+"    checked:"+element_value+"  task_id:"+element_task_id);
				try{
					// ����� ������� � XML �����
					node_list=doc.getElementsByTagName(element_name);
					if(node_list.getLength()>0){
						// ���������� ������� visible � XML �����
						field_logger.debug("Element finded:"+element_value);
						node=node_list.item(0);
						if(!element_value.equals("")){
							if(element_value.equalsIgnoreCase("false")){
								field_logger.debug("set value visible false");
								node.getAttributes().getNamedItem("visible").setTextContent("false");
							}else{
								field_logger.debug("set value visible true");
								node.getAttributes().getNamedItem("visible").setTextContent("true");
							}
						}else{
							field_logger.debug("set value visible false");
							node.getAttributes().getNamedItem("visible").setTextContent("false");
						}
						// ���������� ������� task_id � �����
						((Element)node).setAttribute("task_id", element_task_id);
					}else{
						// element not found
					}
				}catch(Exception ex){
					field_logger.debug("Xpath find error:"+ex.getMessage());
				}
			}else{
				// another element's
				field_logger.debug("ANOTHER_ELEMENT");
			}
		}
		// ��������� ����
        try{
            javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
            javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
            javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(doc); // Pass in your document object here  
            java.io.FileWriter out=new java.io.FileWriter("c:\\out_server.xml");
            //string_writer = new Packages.java.io.StringWriter();  
            javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
            transformer.transform(dom_source, stream_result);  
            //xml_string = string_writer.toString(); // At this point you could use the Servoy XMLReader plugin to read your XML String.  
            //application.output(xml_string);                  
            out.flush();
            out.close();
            System.out.println("XML saved");
        }catch(Exception ex){
            System.out.println("Export XML Error");
        }

		return return_value;
	}
*/	
	/** �������������� XML ���� � HTML ����������� */
	private StringBuffer convertXmlToHTML(Document document){
		StringBuffer return_value=new StringBuffer();
		return_value.append(convertNodeListToHtml("",document));
		return return_value;
	}
	

	/** �������� HTML ��� �� ��������� ����� ������������ */
	private StringBuffer getHtmlFromUserName(String user_name){
		// �������� XML ��������
		Document document=getXmlUser(user_name);
		if(document!=null){
			// ������������� XML �������� � HTML ���
			return convertXmlToHTML(document);
		}else{
			field_logger.error("Error in create XML");
			return new StringBuffer();
		}
	}
	/** �������������� NODE/NODELIST � HTML */
	private StringBuffer convertNodeListToHtml(String preambule, Node node){
		StringBuffer return_value=new StringBuffer();
		if(node.hasChildNodes()){
			NodeList node_list=node.getChildNodes();
			// preambule
			if(node.getNodeType()==Node.ELEMENT_NODE){
				return_value.append(convertNodeToHtml(preambule,node,true));
				return_value.append("<span id=\"body_"+node.getNodeName()+"\">");
			}
			// body
			for(int counter=0;counter<node_list.getLength();counter++){
				if(node_list.item(counter).getNodeType()==Node.ELEMENT_NODE){
					field_logger.debug("NODE ELEMENT_NODE");
					return_value.append(convertNodeListToHtml(preambule+field_delimeter,node_list.item(counter)));
				}else{
					field_logger.debug("NODE other type");
				}
			}
			// postambule
			if(node.getNodeType()==Node.ELEMENT_NODE){
				return_value.append("</span>");
			}
			
		}else{
			return_value.append(convertNodeToHtml(preambule,node,false)); 
		}
		return return_value;
	}

	/** �������������� NODE � HTML */
	private StringBuffer convertNodeToHtml(String preambule, Node node,boolean isRoot){
		//field_logger.debug("Preambule:["+preambule+"]");
		StringBuffer return_value=new StringBuffer();
		String node_name=node.getNodeName();
		String task_id=this.getAttrFromNode(node, "task_id");
		
		if(isRoot){
			// �������� ����������
			if (((Attr)(node.getAttributes().getNamedItem("visible"))).getValue().equals("true")){
				//field_logger.debug(preambule+"<input onclick=\"check_root(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" checked=\"checked\" value=\""+node_name+"\" >"+node_name+"<br>");
				return_value.append(preambule+"<input onclick=\"check_root(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" checked=\"checked\" value=\""+node_name+"\" task_id=\""+task_id+"\" >"+node_name+"<br>");
			}else{
				//field_logger.debug(preambule+"<input onclick=\"check_root(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" value=\""+node_name+"\" >"+node_name+"<br>");
				return_value.append(preambule+"<input onclick=\"check_root(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" value=\""+node_name+"\" task_id=\""+task_id+"\" >"+node_name+"<br>");
			}
		}else{
			if (((Attr)(node.getAttributes().getNamedItem("visible"))).getValue().equals("true")){
				//field_logger.debug(preambule+"<input type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" checked=\"checked\" value=\""+node_name+"\" >"+node_name+"<br>");
				return_value.append(preambule+"<input onclick=\"check_brother(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" checked=\"checked\" value=\""+node_name+"\" task_id=\""+task_id+"\" >"+node_name+"<br>");
			}else{
				//field_logger.debug(preambule+"<input type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" value=\""+node_name+"\" >"+node_name+"<br>");
				return_value.append(preambule+"<input onclick=\"check_brother(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" value=\""+node_name+"\" task_id=\""+task_id+"\" >"+node_name+"<br>");
			}
		}
		return return_value;
	}

	/** �������� XML �������� �� ��������� ����� ������������  �� ��������� 
	 * @param user_name ���������� ��� ������������, �� �������� ����� ��������� XML ���� �� ��������� 
	 * */
	private Document getXmlUser(String user_name){
		// TODO ����� ��� ��������� ����� �������
		// ���������� ������ �������-������� XML, ����� ��������� ��� �������� �������� ���������� �� ������������ �� ������� ������������
		// (���������� ��-�� �������� � ���������-����_������ "������" ������ XML )
		//String path_to_xml="D:\\eclipse_workspace\\DataTerminal_Client\\XML_Files\\terminal_client.xml";
		//Document user=getXmlByPath(path_to_xml);
		//Document pattern=getXmlPattern();
		// ������������� ����, ���������� �� ��������� � ������-���������� ( ��������� ������ ) � ������ ������������ ��������� ������ � ������� �� ���������
		//refreshUserXmlByPatternXml(pattern, user);
		return DBFunction.getVisualXmlByUser(user_name);
	}

	/** �������� ��������� ��������-��������� ������ XML, � ������� ��� ����� �������� ������ �� ������, ����������� �� ��������� */
/*	protected Document getXmlPattern(){
		String path_to_xml="D:\\eclipse_workspace\\DataTerminal_Client\\XML_Files\\terminal_visual_main.xml";
		return getXmlByPath(path_to_xml);
	}
*/
	/** ���������� ��� �������� XML �����, ���������� �� ��������� � ��������� ������ ������� 
	 * @param pattern_xml ������ ( ��������� ������ ) ������� �������� ������������� ��� ����������� ���������
	 * @param user_xml ����������� �������� �� ���������, � ������������ ����������� �������������
	 * @return ��������������� �� ��������� XML ���������������� ������ ( �� ��������� ) 
	 * */
/*	private Document refreshUserXmlByPatternXml(Document pattern_xml, Document user_xml){
		// ��������� ��� �������� �� "�������"
		getFromUserPutToPattern(pattern_xml,user_xml);
		return pattern_xml;
	}
*/	
	/** ����� Node(���������� �� Pattern) � ��������� ��� ��������� � User */
/*	private void getFromUserPutToPattern(Node pattern, Document user){
		if(pattern.hasChildNodes()){
			// is NodeList
			field_logger.debug("is NodeList:"+pattern.getNodeName());
			setPatternNodeFromUserXML(pattern, user.getElementsByTagName(pattern.getNodeName()).item(0),"visible");
			NodeList node_list=pattern.getChildNodes();
			for(int counter=0;counter<node_list.getLength();counter++){
				getFromUserPutToPattern(node_list.item(counter),user);
			}
		}else{
			// is Node
			// �������� ������� �� ����������������� XML
			String node_name=pattern.getNodeName();
			field_logger.debug("is Node:"+node_name);
			NodeList node_list=(NodeList)user.getElementsByTagName(node_name);
			Node node;
			for(int counter=0;counter<node_list.getLength();counter++){
				node=node_list.item(counter);
				setPatternNodeFromUserXML(pattern, node,"visible");
			}
		}
	}

*/	
}
