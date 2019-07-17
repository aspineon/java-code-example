package bc.data_terminal.editor.transfer;

import java.util.ArrayList;

import org.apache.log4j.*;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

import bc.data_terminal.editor.database.DBFunction;
import bc.data_terminal.editor.database.ExchangeFieldsPatternField;
import bc.data_terminal.editor.database.ExchangeFieldsPatternHeadElement;
import bc.data_terminal.editor.database.ExchangeFile;



/**
 * �����, ������� �������� �� ������� �� ������� ���� HTML, ��� ����������
 * ������� ���� � ���� ������
 * */
public class ExchangeFieldsPatternParser {
	private Logger field_logger = Logger.getLogger(this.getClass());
	{
		field_logger.setLevel(Level.DEBUG);
		try {
			BasicConfigurator.configure();
			// Layout layout=new PatternLayout();// format output
			// Layout layout=new SimpleLayout();// format output
			// field_logger.addAppender(new FileAppender(layout,"c:\\log.txt"));
		} catch (Exception ex) {

		}
		if (!field_logger.getAllAppenders().hasMoreElements()) {
			BasicConfigurator.configure();
		}
	}

	private String field_is_create;
	private String field_pattern_name;
	private String field_html_data;
	private String field_task_id;

	/**
	 * ��������������� ���������� ������ �� ����� HTML � ���� ������
	 * 
	 * @param iscreate
	 *            - ���� ����� <b>iscreate</b> ���� ����� <b>edit</b> �������
	 *            ������� ���� � �������������� �������, ���� � ��� ��������
	 * @pram pattern_name - ��� �������, ������� ���� ����� ������� ���� �����
	 *       �������������
	 * @param html_data
	 *            ���������� ������ �� �������� (span id="header") � (span
	 *            id="fields")
	 * @return ���������� ������ ������ � ������ ������� ������ ����������
	 *         ������, ���� ����� ��� �������� ������������
	 */
	public ExchangeFieldsPatternParser(String is_create, String pattern_name,
			String html_data,String task_id) {
		this.field_is_create = is_create;
		this.field_pattern_name = pattern_name;
		this.field_html_data = html_data;
		this.field_task_id=task_id;
	}

	/** ������� ��������� ���������� ������ � ���� */
	public String getResult() {
		return getResult(field_is_create, field_pattern_name, field_html_data);
	}

	/**
	 * �������� ��������� ���������� ������ � ���� ������
	 * 
	 * @param iscreate
	 *            (="iscreate" - �������� �������)
	 * @param pattern_name
	 *            (���� ��� ������������ �������, ���� ��� �����, �� ��������
	 *            ���������� ��������������)
	 * @param html_data
	 *            (������ � ���� HTML �����, ������� ����� ���������� �
	 *            ���������� ���������)
	 * @return ������� �� ������ ��������, � ������, ����� ��������� ������
	 * */
	public String getResult(String iscreate, String pattern_name,
			String html_data) {
		String return_value = "";
		field_logger.debug("getResult:begin");
		Parser parser = new Parser();
		parser.setLexer(new Lexer(html_data));
		TagNameFilter filter_span = new TagNameFilter("span");
		OrFilter filter = new OrFilter(new NodeFilter[] { filter_span });
		field_logger.debug("getResult:parse begin");

		ArrayList<ExchangeFieldsPatternHeadElement> head_element = null;
		ArrayList<ExchangeFieldsPatternField> fields_element = null;
		try {
			NodeList list = parser.parse(filter);
			SimpleNodeIterator iterator = list.elements();
			Node node;
			while (iterator.hasMoreNodes()) {
				node = iterator.nextNode();
				// field_logger.debug("getResult:Node:"+node.getText());
				if (node.getText().indexOf("header") > 0) {
					// �������� �������� Head
					head_element = getHeadFromNode(node);
				}
				if (node.getText().indexOf("fields") > 0) {
					// �������� �������� Fields
					fields_element = getFieldFromNode(node);
				}
			}
		} catch (Exception ex) {
			field_logger.error("getResult: Parse Error:" + ex.getMessage());
			return_value = "Error parse HTML";
		}
		field_logger.debug("getResult: parse end");
		field_logger.debug("head_element:" + head_element);
		for (int counter = 0; counter < head_element.size(); counter++) {
			field_logger.debug("getResult: HeadElement:"
					+ head_element.get(counter));
		}
		field_logger.debug("fields_element:" + fields_element);
		for (int counter = 0; counter < fields_element.size(); counter++) {
			field_logger.debug("getResult: FieldElement:"
					+ fields_element.get(counter));
		}

		// save to DataBase
		if (iscreate.equals("iscreate")) {
			field_logger.debug("getResult:   CREATE :begin");
			// head_element
			String new_file_id = DBFunction
					.createExchangeFile("1", 
										pattern_name, 
										pattern_name, 
										"Y",
										this.getAttributeFromHead(head_element,"DELIMETER", "value"), 
										this.getAttributeFromHead(head_element,
											"HAS_COMMENTS", "exists").equals(
											"true") ? "Y" : "N", 
										this.getAttributeFromHead(head_element,
											"HAS_HEADER", "exists").equals(
											"true") ? "Y" : "N", 
										this.getAttributeFromHead(head_element,
											"NEED_LOG", "full_log").equals(
											"true") ? "Y" : "N", 
										this.field_task_id);
			if (new_file_id != null) {
				// ���� ������� ������, ��������� ������
				// fields_element
				if (DBFunction.fillExchangeFile("1", new_file_id,
						fields_element) == true) {
					field_logger.debug("������ ������� ��������� ");
					return_value = "";
				} else {
					field_logger.error("������ ���������� ���� - ����� ����� ");
					return_value = "fields saving error ";
				}
			} else {
				// ���� �� ������ - ���������� ������ ��������
				field_logger.error("getResult: ������ �������� ����� ");
				return_value = "Error in create file ";
			}
			field_logger.debug("getResult:   CREATE :end");
		} else {
			field_logger.debug("getResult:   EDIT:begin");
			// save head_element
			ExchangeFile file_for_save = new ExchangeFile(pattern_name);
			if (file_for_save.saveHeadersIntoFile(head_element,field_task_id) == true) {
				// TODO Pattern.Edit save - for saving fields
				if (file_for_save.saveFieldsIntoFile(fields_element) == true) {
					return_value = "";
				} else {
					field_logger
							.debug("Data not save: elements of field is not save ");
				}
			} else {
				field_logger
						.debug("Data not save: element of head is not save ");
			}

			// save fields_element
			field_logger.debug("getResult:   EDIT:end");
		}
		field_logger.debug("getResult:   saveDataHtmlToDataBase:begin");
		return return_value;
	}

	/**
	 * �������� �� ����� ��������� � ����� ��������� �������� ������� ���������:
	 * 
	 * @param headers
	 *            ��������� � ���� ArrayList
	 * @param header_name
	 *            ��� ���������, ������� ������� �����
	 * @param attribute_name
	 *            ��� ���������, �������� �������� ����� ��������
	 * */
	private String getAttributeFromHead(
			ArrayList<ExchangeFieldsPatternHeadElement> headers,
			String header_name, String attribute_name) {
		String return_value = "";
		for (int counter = 0; counter < headers.size(); counter++) {
			field_logger.debug("headers:" + headers.get(counter)
					+ "   header_name:" + header_name);
			if (headers.get(counter).isNameEquals(header_name) == true) {
				// name finded
				field_logger.debug("return :"
						+ headers.get(counter)
								.getAttributeValue(attribute_name));
				return_value = headers.get(counter).getAttributeValue(
						attribute_name);
				break;
			}
		}
		return return_value;
	}

	private ArrayList<ExchangeFieldsPatternHeadElement> getHeadFromNode(
			Node head_node) {
		ArrayList<ExchangeFieldsPatternHeadElement> return_value = new ArrayList<ExchangeFieldsPatternHeadElement>();
		NodeList list = head_node.getChildren();
		if (list == null) {
			field_logger
					.error("getHeadFromNode head not consists child element's ");
		} else {
			field_logger.debug("getheadFromNode:begin");
			SimpleNodeIterator iterator = list.elements();
			/** next node, wich consists table with Head Element data */
			while (iterator.hasMoreNodes()) {
				return_value.add(getHeadElementFromNode(iterator.nextNode()));
			}
			field_logger.debug("getheadFromNode:end");
		}
		return return_value;
	}

	/** �������� ������� HeadElement �� �������� [TABLE] */
	private ExchangeFieldsPatternHeadElement getHeadElementFromNode(
			Node table_node) {
		ExchangeFieldsPatternHeadElement return_value = new ExchangeFieldsPatternHeadElement(
				table_node.toHtml());
		return return_value;
	}

	/** �������� ������� Field �� �������� [TABLE] */
	private ArrayList<ExchangeFieldsPatternField> getFieldFromNode(
			Node fields_node) {
		ArrayList<ExchangeFieldsPatternField> return_value = new ArrayList<ExchangeFieldsPatternField>();
		NodeList list = fields_node.getChildren();
		if (list == null) {
			field_logger
					.error("getFieldsFromNode head not consists child element's ");
		} else {
			field_logger.debug("getFieldsFromNode:begin");
			SimpleNodeIterator iterator = list.elements();
			Node current_node;
			/** next node, wich consists table with Fields Element data */
			while (iterator.hasMoreNodes()) {
				current_node = iterator.nextNode();
				return_value.add(getOneFieldFromNode(current_node));
			}
			field_logger.debug("getFieldsFromNode:end");
		}
		return return_value;
	}

	private ExchangeFieldsPatternField getOneFieldFromNode(Node node) {
		field_logger.debug(node.toHtml());
		return new ExchangeFieldsPatternField(node.toHtml());
	}
}
