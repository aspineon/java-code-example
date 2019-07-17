import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * ���� ������� ������� - ������������ ������ ������ ����������
 * ������ � XML �������

 <root>
	this is text of root element
	<sub_element>
		this is text of sub element
	</sub_element>
</root>

 �����:
 �������� ����� �� �������� root �������� �� ������ sub_element
 �� �������������� ��������� ( ��� ������ ����� ����� ���� �����������
 */
public class ReadSubElementText {
	public static void main(String[] args) throws Exception{
		System.out.println("begin");
		Document file=getXmlByPath("temp.xml");
		Element element=(Element)file.getFirstChild();
		System.out.println("NodeName:"+element.getNodeName()
		+"\n  NodeType:"+element.getNodeType()
		+"\n  NodeValue:"+element.getNodeValue()
		+"\n  TagName:"+element.getTagName()
		+"\n  TextContent:"+element.getTextContent()
		+"\n  BaseUri:"+element.getBaseURI()
		);
		System.out.println("-end-");
	}
	
	/** �������� XML ���� �� ���������� ����������� ���� */
	private static Document getXmlByPath(String path_to_xml) throws Exception{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // ���������� ������������� ���������� Parser-��
        document_builder_factory.setValidating(false);
        try {
            // ��������� �����������
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            return_value=parser.parse(new File(path_to_xml));
        }catch(Exception ex){
        	System.err.println("XmlExchange.java ERROR:"+ex.getMessage());
        	throw ex;
        }
		return return_value;
	}
}
