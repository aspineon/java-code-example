package Utility;
import java.io.*;
import java.text.SimpleDateFormat;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import com.csvreader.*;

/** ������� ������ �������� �� �������� ������������ ������ � XML ������ � � CSV */
public class CsvChecker {
	
	private void debug(String information){
		//System.out.print(this.getClass().getName());
		//System.out.print(" DEBUG ");
		//System.out.println(information);
	}
	private void error(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	
	/** ����� ���������� ��� ������� ������������ � ���*/
	private void to_log(String information){
		System.out.print(" LOG: ");
		System.out.println(information);
	}
	
	/** ���� � ����� CSV */
	private String field_path_to_csv;
	/** ���� � ����� XML*/
	private String field_path_to_xml;
	/** ������ ������������ ���� ������� � XML */
	private Field[] field_fields;
	/** ����, ������� ������� � ������� ��������� */
	private boolean field_header_exists;
	/** �����������, ������� ������������ � CSV ����� */
	private String field_delimeter=",";
	
	/** ����, ������� ������� �� ������������� ������������*/
	private boolean field_comment_exists=true;
	
	/** ��� attribute ��� NODE ��������� DELIMETER */
	private static String field_delimeter_xml_attribute="value";
	
	/** ��� attribute ��� NODE ��������� CHECKER */
	private static String field_checker_xml_attribute="full_log";

	/** �������� ������ ��� = true, ��� �� �������� ������ ��� ������ �� �� ����������=false*/
	private boolean field_log_full=false;
	
	/**
	 * �� ������ ������� XML ����� ��������� ���������� ������ � CSV ����� 
	 * @param path_to_xml
	 * @param path_to_csv
	 * @throws Exception ���� ���� �� �������, ��������� ������ ������ XML...
	 */
	public CsvChecker(String path_to_xml, String path_to_csv) throws Exception {
		this.field_path_to_csv=path_to_csv;
		this.field_path_to_xml=path_to_xml;
		this.field_header_exists=false;
		debug("check for exists");
		try{
			if((new File(this.field_path_to_csv)).exists()&&(new File(this.field_path_to_xml).exists())==false){
				
			}
		}catch(Exception ex){
			error("file is not exists ");
			throw new Exception("����� �� ������");
		}
		debug("parsing XML");
		try{
			parseXML();
		}catch(Exception ex){
			error("xml parsing error");
			throw new Exception("������ ������� ����� XML");
		}
		
	}
	
	/** �����, ������� ������ ������ �� XML � ��������� �� � ���� ������� �������:
	 * <li>������ ������� ��������� ([HEADER] exists='true')</li>
	 * <li>������ �����, ������������� ������� (class)</li>
	 * <li>������ �����, ������������� �������������� ���� (canEmpty)</li>
	 * */
	private void parseXML() throws Exception{
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(this.field_path_to_xml);

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expression;
        
        debug("get DELIMETER");
        expression= xpath.compile("//PATTERN/DELIMETER");
        field_delimeter=",";
        try{
        	Node node=(Node)expression.evaluate(doc,XPathConstants.NODE);
        	if(node!=null){
        		field_delimeter=((Attr)node.getAttributes().getNamedItem(field_delimeter_xml_attribute)).getValue();
        		debug("DELIMETER is found:"+field_delimeter);
        	}
        }catch(Exception ex){
        	debug("DELIMETER not found:");
        }

        debug("get COMMENT");
        try{
        	// check for exists COMMENT
        	Node comment_node=(Node)xpath.compile("//PATTERN/COMMENT").evaluate(doc,XPathConstants.NODE);
        	if(comment_node!=null){
        		// check for exists COMMENT [@exists]
        		if(comment_node.getAttributes().getNamedItem("exists")!=null){
                    // find comment [@exists='true']
        			expression= xpath.compile("//PATTERN/COMMENT[@exists='true']");
                    Boolean comment_value=(Boolean)expression.evaluate(doc, XPathConstants.BOOLEAN);
                    if(comment_value!=null){
                    	this.field_comment_exists=comment_value.booleanValue();
                    }else{
                    	this.field_comment_exists=true;
                    }
        		}
        	}
        }catch(Exception ex){
        	//this.field_comment_exists=true;
        }
 		
        debug("get HEADER"); 
 		expression= xpath.compile("//PATTERN/HEADER[@exists='true']");
        Boolean header_value=(Boolean)expression.evaluate(doc, XPathConstants.BOOLEAN);
        if(header_value!=null){
        	this.field_header_exists=header_value.booleanValue();
        }else{
        	this.field_header_exists=false;
        }
        
        debug("get ELEMENTS");
        expression=xpath.compile("//PATTERN/FIELDS/FIELD");
        NodeList nodes=(NodeList)expression.evaluate(doc,XPathConstants.NODESET);
        debug("NODESET:"+nodes.getLength());
        this.field_fields=new Field[nodes.getLength()];
        for(int counter=0;counter<nodes.getLength();counter++){
        	this.field_fields[counter]=new Field(nodes.item(counter));
        }
        
        debug("get CHECKER");
        try{
        	// check for exists COMMENT
        	Node comment_node=(Node)xpath.compile("//PATTERN/CHECKER").evaluate(doc,XPathConstants.NODE);
        	if(comment_node!=null){
        		// check for exists COMMENT [@exists]
        		if(comment_node.getAttributes().getNamedItem(field_checker_xml_attribute)!=null){
                    // find comment [@exists='true']
        			expression= xpath.compile("//PATTERN/CHECKER[@"+field_checker_xml_attribute+"='true']");
                    Boolean comment_value=(Boolean)expression.evaluate(doc, XPathConstants.BOOLEAN);
                    if(comment_value!=null){
                    	this.field_log_full=comment_value.booleanValue();
                    }else{
                    	this.field_log_full=false;
                    }
        		}
        	}
        }catch(Exception ex){
        	//this.field_log_full=false;
        }
        
	}
	
	/** ��������� ���-�� ��������� � ������ 
	 * @param values - ��������, ����������� �� ����� 
	 * @param fields - ����, ������� �������� �������� �������� 
	 * @return <b>true</b> - ����������� ���������  <br>
	 * <b>false</b> - �������� �����������
	 * */
	private boolean checkValuesCount(String[] values, Field[] fields){
		return values.length==fields.length;
	}
	/** ��������� ��������� ������ �� ������ �������� � ������������ ������� �������� 
	 * @param values - ����������� �� ����� ������ � ���� ������� ����� 
	 * @param  fields - ����, ������� �������� �������� ��������
	 * @param index - ������ ����, ������� ������ ���� ���������������
	 * @return <b>true</b> - ���� ���� ���������, ���� ������, �� ��� ��������� XML ������ <br>
	 *  <b>false</b> - ���� ���� ������ � ��� �� ��������� 
	 * */
	private boolean checkEmpty(String[] values,Field[] fields, int index ){
		boolean return_value=false;
		String current_value=values[index].trim();

		if(current_value.length()>0){
			return_value=true;
		}else{
			if(fields[index].isCanEmpty()){
				return_value=true;
			}else{
				return_value=false;
			}
		}
		return return_value;
	}
	
	
	/** �������� ����� CSV �� ������������ ��������, ������� ��������� � XML ����� 
	 * @return "" ���� ���� �������������
	 * ���� �� ���������� ����� ������ 
	 * */
	public String checkCSV(){
		String return_value="";
		/** ����, ������� ������� � ������� ������ � ����� */
		boolean flag_error=false;
		try{
			// settings
			CsvReader reader=new CsvReader(this.field_path_to_csv);
				// set delimeter
			try{
				debug("SETTINGS: set delimeter");
				reader.setDelimiter(this.field_delimeter.charAt(0));
			}catch(Exception ex){
				error("setDelimeter is error:"+this.field_delimeter);
			}
				// set use comments
			debug("SETTINGS: set use comment"+this.field_comment_exists);
			reader.setUseComments(this.field_comment_exists);
				// set headers
			if(this.field_header_exists==true){
				debug("SETTINGS: set header exists");
				reader.readHeaders();
				if(reader.getHeaderCount()!=this.field_fields.length){
					throw new Exception("���-�� �������� � ��������� �� ��������� � �����������");
				}
			}
			
			// read body
			int row_counter=0;
			int counter=0;
			String[] values;
			// check for all rows into file
			while(reader.readRecord()){
				row_counter++;
				values=reader.getValues();
				for(counter=0;counter<values.length;counter++){
					System.out.print(values[counter]+"; ");
				}
				// check for all values into file
				for(counter=0;counter<values.length;counter++){
					// check data count
					if(this.checkValuesCount(values, field_fields)){
						
					}else{
						if(this.field_log_full){
							flag_error=true;
							to_log("Row:"+row_counter+" element count is not valid ");
						}else{
							reader.close();
							throw new Exception("Row:"+row_counter+" element count is not valid ");
						}
					}
					
					// check for empty
					if(this.checkEmpty(values, field_fields, counter)){
						// OK
					}else{
						// ERROR
						if(this.field_log_full){
							flag_error=true;
							to_log("Row:"+row_counter+"   Column:"+this.field_fields[counter].getColumnName()+"("+(counter+1)+") is Empty");
						}else{
							reader.close();
							throw new Exception("Row:"+row_counter+"   Column:"+this.field_fields[counter].getColumnName()+"("+(counter+1)+") is Empty");
						}
					}
					
					// check for Class
					if(!this.field_fields[counter].isParseToClass(values[counter])){
						if(this.field_log_full){
							flag_error=true;
							to_log("Row:"+row_counter+"   Column:"+this.field_fields[counter].getColumnName()+"("+(counter+1)+") not instanceof "+this.field_fields[counter].getClassName());
						}else{
							reader.close();
							throw new Exception("Row:"+row_counter+"   Column:"+this.field_fields[counter].getColumnName()+"("+(counter+1)+") not instanceof "+this.field_fields[counter].getClassName());
						}
					}
				}
			}
			reader.close();
		}catch(Exception ex){
			return_value=ex.getMessage();
			flag_error=true;
			error("checkCSV Exception:"+ex.getMessage());
		}
		
		if(field_log_full){
			if(flag_error){
				return "Error";
			}else{
				return "";
			}
		}else{
			return return_value;
		}
	}
	
}


/** �������, ������� �������� ���������� � ������� CSV ����� */
class Field{
	private static SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
	/** ����, ������� �������� ��������� ��� ������*/
	private String field_class_name;
	/** ����, ������� �������� ��������, ����� �� ������������� - ����� ������ ��������, ������ �� CSV*/
	private boolean field_can_empty;
	/** ����, ������� �������� ��������� ��� ������ ������� */
	private String field_column_name;
	
	/** ��� ��������� FIELD ��� ����������� ����������� ������� �������� */
	private static String field_xml_canEmpty="can_empty";

	/** ��� ��������� FIELD ��� ����������� ����� ���� */
	private static String field_xml_fieldName="field_name";

	/** ��� ��������� FIELD ��� ����������� ������ ����������, ����������� ��� ����� - �� ��� �������������� */
	private static String field_xml_class="class";
	
	/** 
	 * ������, ������� �������� �������� ������� �� XML
	 * @param class_name
	 * @param is_can_empty
	 */
/*	public Field(String class_name, String is_can_empty){
		this.field_class_name=class_name;
		field_can_empty=Boolean.valueOf(is_can_empty);
	}
*/	
	/**
	 * ������, ������� �������� �������� ������� �� XML
	 */
	public Field(Node node) throws Exception{
		NamedNodeMap attributes=node.getAttributes();
		Attr attribute;
		// canEmpty
		attribute=((Attr)attributes.getNamedItem(field_xml_canEmpty));
		this.field_can_empty=Boolean.parseBoolean(attribute.getValue());

		// class
		attribute=((Attr)attributes.getNamedItem(field_xml_class));
		this.setClass(attribute.getValue());
		
		// fieldName
		attribute=((Attr)attributes.getNamedItem(field_xml_fieldName));
		this.setFieldName(attribute.getValue());
		
	}
	
	private void setClass(String class_name){
		this.field_class_name="java.lang.Object";
		this.field_class_name=class_name;
	}
	
	private void setFieldName(String fieldName){
		this.field_column_name=fieldName;
	}
	
	
	/** ����� �� ������ ������ �� ����� �������� - ���� ������ */
	public boolean isCanEmpty(){
		return this.field_can_empty;
	}
	
	/** ���������� true, ���� ���������� �������� String ����� ���� ������������� � ������ ��� */
	public boolean isParseToClass(String value){
		boolean return_value=false;
		if(this.getClassName().equals("java.lang.Integer")){
			try{
				Integer.parseInt(value);
				return_value=true;
			}catch(Exception ex){
				if((value.trim().length()==0)&&(this.isCanEmpty())){
					return_value=true;
				}
			}
		};
		if(this.getClassName().equals("java.lang.String")){
			return_value=true;
		};
		if(this.getClassName().equals("java.lang.Float")){
			try{
				Float.parseFloat(value);
				return_value=true;
			}catch(Exception ex){
				value=value.replaceAll(",", ".");
				try{
					Float.parseFloat(value);
					return_value=true;
				}catch(Exception ex2){
					if((value.trim().length()==0)&&(this.isCanEmpty())){
						return_value=true;
					}
				};
			}
		};
		if(this.getClassName().equals("java.util.Date")){
			try{
				sdf.parse(value);
				return_value=true;
			}catch(Exception ex){}
		};
		if(this.getClassName().equals("java.lang.Double")){
			try{
				Double.parseDouble(value);
				return_value=true;
			}catch(Exception ex){
				value=value.replaceAll(",", ".");
				try{
					Double.parseDouble(value);
					return_value=true;
				}catch(Exception ex2){
					if((value.trim().length()==0)&&(this.isCanEmpty())){
						return_value=true;
					}
				};
			};
		};
		return return_value;
	}
	
	/** ���������� ��� ������ */
	public String getClassName(){
		return this.field_class_name;
	}
	
	/** ���������� ��� ������� */
	public String getColumnName(){
		return this.field_column_name;
	}
}




