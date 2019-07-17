package example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/*
 * sax.java
 *
 * Created on 29 ����� 2008, 14:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * ����� �� ���������� XML-�������� ��� ������ ������� SAX, ������ ���������� ����� ������� �� ���� ����, ��� �� ������ ��������. �� ���� ���������� - ��� ������ �� ���� ��������. ��� ��������� �������� �������, ������� �� ������ �������� ��� ������� XML-���������: 
 * ������� startDocument. 
 * ��� ������� ��������, ������� startElement ��� ������ �������� � ������� endElement ��� ��������� ��������. 
 * ���� � �������� ���� ����������, ����� ����� �������, ��� characters ��� ��������������� ������, startElement � endElement ��� �������� ��������� � �.�. 
 * ������� endDocument. 
 */
public class sax extends org.xml.sax.helpers.DefaultHandler {
    
    /**  */
    public sax(String path_to_file) {
        // ������� ������� ������������
        javax.xml.parsers.SAXParserFactory sax_parser_factory=javax.xml.parsers.SAXParserFactory.newInstance();
        // ������ ���������� ��������� DTD
        sax_parser_factory.setValidating(false);
        javax.xml.parsers.SAXParser sax_parser;
        try {
            // �������� ������, ������� ����� ����������� ������(Parsing) �����
            sax_parser=sax_parser_factory.newSAXParser();
            // ������ ��������
            // ����� �������� �� StringReader, ���� �������� �� ����, � ������
            org.xml.sax.InputSource input_source=new org.xml.sax.InputSource(new FileReader(path_to_file));
            // ������ (Parsing)
            sax_parser.parse(input_source,this);
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException:"+ex.getMessage());
        } catch(IOException iox){
            System.out.println("IOException:"+iox.getMessage());
        }catch (ParserConfigurationException ex) {
            System.out.println("ParserConfigurationException:"+ex.getMessage());
        } catch (SAXException ex) {
            System.out.println("SAXException:"+ex.getMessage());
        }
        
    }
    
    /** ������ ������ - �������� �� ����������� � ����� ��������*/
    public void characters(char[] ch, int start, int length){
        if((new String(ch,start,length)).trim().equals("")){
            // �������� ������ ������� �������� - ������� Tab, \n
        }else{
            // �������� �� ������ ������� - ������
            System.out.println(new String(ch,start,length));
        }
    }
    /** �������� ��������� ������� */
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException{
        System.out.println("<NameSpace:"+uri+"    localName:"+localName+"    qualified Name:"+qName+"  Attributes:"+attributes.getLength());
        if(attributes.getLength()>0){
            for(int index=0;index<attributes.getLength();index++){
                System.out.println("   attributes       type:"+attributes.getType(index)+"    Value:"+attributes.getValue(index)+"    localName:"+attributes.getLocalName(index)+"    qName:"+attributes.getQName(index)+"   URI:"+attributes.getURI(index));
            }
        }
    }
    /** �������� ���������� ������*/
    public void endElement(String uri,String localName,String qName){
        System.out.println(">NameSpace:"+uri+"    localName:"+localName+"    qualified Name:"+qName);
    }
    /** start parsing*/
    public void startDocument(){
        System.out.println("Start parsing");
    }
    /** end parsing*/
    public void endDocument(){
        System.out.println("End document");
    }
    /** warning in parsing process*/
    public void warning(SAXParseException e){
        System.out.println("!!! Exception:"+e.toString());
    }
    /** error*/
    /** fatalError */
}
