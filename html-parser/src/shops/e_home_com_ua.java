package shops;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import shop_list.html.parser.engine.record.Record;
import shop_list.html.parser.engine.single_page.TableSinglePage;

public class e_home_com_ua extends TableSinglePage{

	@Override
	protected Record getRecordFromRow(Node node) {
		Record returnValue=null;
		try{
			if(((Element)this.parser.getNodeFromNodeAlternative(node, "/td[2]/nobr")).getTextContent().indexOf("��� ")<0){
				String name=null;
				String url=null;
				String description=null;
				Float price=null;
				Float priceUSD=null;
				Float priceEURO=null;
				
				Element nodeName=(Element)this.parser.getNodeFromNodeAlternative(node, "/td[1]/a");
				url=nodeName.getAttribute("href");
				name=nodeName.getTextContent().trim();
				
				Element priceElement=(Element)this.parser.getNodeFromNodeAlternative(node, "/td[3]/nobr/b");
				// System.out.println("Source   price String: "+priceElement.getTextContent().trim());
				// System.out.println("Replaced price String: "+priceElement.getTextContent().trim().replaceAll("[\\$ ,]", ""));
				String priceElementText=priceElement.getTextContent().trim().replaceAll("[,]", "").replaceAll("���.", "");
				price=Float.parseFloat(priceElementText);
				
				returnValue=new Record(name,description,url, price, priceUSD, priceEURO);
			}else{
				// ��� �� ������
			}
		}catch(Exception ex){
			returnValue=null;
			logger.error(this, "#getRecordFromRow Exception:"+ex.getMessage());
		}
		return returnValue;
	}

	@Override
	protected String getSectionNameFromRow(Node node) {
		return this.parser.getTextContentFromNodeAlternative(node, "/td/a", "");
	}

	@Override
	protected boolean isRecord(Node node) {
		if(this.parser.getChildElementCount(node, "td")==3){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected boolean isSection(Node node) {
		if(this.parser.getChildElementCount(node, "td")==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected String getCharset() {
		return "windows-1251";
	}

	@Override
	protected String getFullHttpUrlToPrice() {
		return "http://e-home.com.ua/index.php?show_price=yes";
	}

	@Override
	public String getShopUrlStartPage() {
		return "http://e-home.com.ua";
	}

	@Override
	protected String getXmlPathToMainBlock() {
		return "/html/body/table/tbody/tr[2]/td[2]/table/tbody/tr/td/center/table[2]/tbody/tr[4]/td[2]/table/tbody/tr/td/table/tbody/tr/td/center/p[2]/table/tbody";
	}

}
