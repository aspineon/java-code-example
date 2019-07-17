package aromat;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import html_parser.element.base.HtmlPage;
import html_parser.element.base.HtmlRecord;
import html_parser.engine.base.WalkRecord;

public class AromatWalkRecord extends WalkRecord{
	/** ����, ���������� ��� ������ �� ������ �������� */
	private NodeList blockNode;
	
	public AromatWalkRecord(HtmlPage page) throws Exception {
		super(page);
		// ���������� ������ �� ������� ������� � ��� ������
		Node node=getNodeWithDataBlock(page.getReader(),
						    "windows-1251",
							"//html/body/table/tbody/tr[1]/td[1]/table[3]/tbody/tr[1]/td[2]/table[1]/tbody/tr[1]/td[1]/table[1]/tbody"
							);
		if(node.hasChildNodes()){
			this.blockNode=node.getChildNodes();
		}else{
			throw new Exception("is not node List");
		}
	}
	
	private int childCounter=0;
	
	/** �������� ��������� ������ �� �����, ��� ������� null, ���� ������� ��� */
	@Override
	public HtmlRecord getNextRecord() {
		HtmlRecord returnValue=null;
		// ����� ���������� �������� �� ����� ������
			// ����� valign="top"
		while(childCounter<this.blockNode.getLength()){
			if(this.blockNode.item(childCounter).hasAttributes()){
				// ���� �������� - ��������� - ������
				try{
					returnValue=new AromatHtmlRecord(
													 this.blockNode.item(childCounter),
													 this.blockNode.item(childCounter+1),
													 this.blockNode.item(childCounter+2),
													 this.blockNode.item(childCounter+3),
													 this.blockNode.item(childCounter+4),
													 this.blockNode.item(childCounter+5),
													 this.blockNode.item(childCounter+6),
													 this.blockNode.item(childCounter+7)
												     );
					childCounter++;
					break;
				}catch(Exception ex){
					// ������� � ��������� ��������
					System.err.println("get element error:"+ex.getMessage());
				}
			}
			childCounter++;
		}
		return returnValue;
	}
	
}
