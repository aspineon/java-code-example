package html_parser.element.base;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** �����-�������� ��� ������(Html ������) �� ����� ������ HtmlBlock*/
public class HtmlRecord {
	private String value;
	private XPath xpath=null;
	
	public HtmlRecord(String value){
		this.value=value;
		init();
	}
	
	public HtmlRecord(){
		init();
	}

	private void init(){
		XPathFactory factory=XPathFactory.newInstance();
		this.xpath=factory.newXPath();
	}
	
	@Override
	public String toString(){
		return this.value;
	}
	
	/** ���������� Node �� ��������� XPath ��������� <br>
	 *  <b>Important:  
	 *  <li>  example expression="//td[1]/div[2]" - ������������ ����� ����� ���������</li>
	 *  <li>  example expression="td[1]/div[2]" - ������������ ������� Node</li>
	 *  </b>
	 * @param node - ������������ ���� ������
	 * @param expression - ��������� ��� ������
	 * @return Node ��� null - ���� ��������� ������ ��������
	 * */
	protected Node getNodeByXPath(Node node, String expression){
		try{
			XPathExpression xpathExpression=this.xpath.compile(expression);
			Object object=xpathExpression.evaluate(node,XPathConstants.NODE);
			return (Node)object;
		}catch(Exception ex){
			System.err.println("getNodeByXPath error:"+ex.getMessage());
			return null;
		}
	}
	
	/** ���������� Node �� ��������� XPath ��������� <br>
	 *  <b>Important:  
	 *  <li>  example expression="//td[1]/div[2]" - ������������ ����� ����� ���������</li>
	 *  <li>  example expression="td[1]/div[2]" - ������������ ������� Node</li>
	 *  </b>
	 * @param node - ������������ ���� ������
	 * @param expression - ��������� ��� ������
	 * @return Node ��� null - ���� ��������� ������ ��������
	 * */
	protected NodeList getNodeListByXPath(Node node, String expression){
		try{
			XPathExpression xpathExpression=this.xpath.compile(expression);
			Object object=xpathExpression.evaluate(node,XPathConstants.NODESET);
			return (NodeList)object;
		}catch(Exception ex){
			System.err.println("getNodeByXPath error:"+ex.getMessage());
			return null;
		}
	}
	
}
