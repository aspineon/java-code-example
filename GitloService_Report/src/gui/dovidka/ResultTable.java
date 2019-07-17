package gui.dovidka;

import gui.table_column_render.ColumnConst;
import gui.table_column_render.ColumnDate;
import gui.table_column_render.ColumnMultiCellValue;
import gui.table_column_render.ColumnSimple;
import gui.table_column_render.ColumnSimpleTrim;
import gui.table_column_render.ColumnTruncDouble;
import gui.table_column_render.ICellValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import reporter.HtmlReplacer;
import reporter.MoneyToString;
import reporter.replacer.AddPrefixIsNotEmptyRaplacer;
import reporter.replacer.MultiReplacer;
import reporter.replacer.ReplaceValue;
import reporter.replacer.SingleReplacer;
import jxl.write.Number;

/** �������, ������� �������� ���������� ���������� */
public class ResultTable extends JTable{
	private final static long serialVersionUID=1L;
	private ResultTableModel model;
	private String pathToPattern;
	private String pathToPatternAkt;
	/** ��� �������, ������� ������ ������������ � ������ */
	private String tableTnName="";
	/** ��� �������, �� ������� ����� �������� ���������� � ������ */
	private String tableOsName="";
	/** ��� �������, �� ������� ����� �������� ���������� � ��������� */
	private String tablePlName="";
	/** ������� �� ���� */
	private String whereJK="";
	/** ������ ��� ���� ����� */
	private ArrayList<String> fieldName=new ArrayList<String>();
	/** ������ ��� ���� ��.���. */
	private ArrayList<String> fieldValue=new ArrayList<String>();
	/** ������ �� ���������*/
	private String viddil;
	/** ����� �� ���������� ������ �� �����, ��� �� "������������� � ���������"*/
	private boolean pochta=false;
	/** ��������� ����������� 0..6 naim(0), adr(1), okp(2), mfo1(3), RASCH1(4), NOM_DPA(5), SWID_DPA (6)*/
	private ArrayList<String> rekvisit=new ArrayList<String>();	
	
	/** ���������� � ����� ������ */
	private Connection connection;
	
	/** �������, ������� �������� ��� ������ ��� ������ 
	 * @param connection  - ������� ���������� � ����� ������
	 * @param pathToPattern - ���� � �����-������� 
	 * */
	public ResultTable(Connection connection,
					   String pathToPattern,
					   String pathToPatternAkt){
		super();
		//this.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
		this.connection=connection;
		this.pathToPattern=pathToPattern;
		this.pathToPatternAkt=pathToPatternAkt;
		this.model=new ResultTableModel(this.connection,
										new ICellValue[]{new ColumnMultiCellValue(new ColumnSimpleTrim(8),new ColumnConst(" "),new ColumnTruncDouble(11),new ColumnConst(" "),new ColumnSimpleTrim(13)),
														 new ColumnSimple(6),
														 new ColumnTruncDouble(11),
														 new ColumnDate(15),
														 new ColumnSimple(17),
														 new ColumnSimple(7)
				                                         });
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setModel(this.model);

		this.getColumn(this.getColumnName(0)).setPreferredWidth(150);
		this.getColumn(this.getColumnName(1)).setPreferredWidth(150);
		this.getColumn(this.getColumnName(2)).setPreferredWidth(30);
		this.getColumn(this.getColumnName(3)).setPreferredWidth(70);
		this.getColumn(this.getColumnName(4)).setPreferredWidth(50);
		this.getColumn(this.getColumnName(5)).setPreferredWidth(250);

		this.getColumn(this.getColumnName(0)).setHeaderValue("�����");
		this.getColumn(this.getColumnName(1)).setHeaderValue("�������������");
		this.getColumn(this.getColumnName(2)).setHeaderValue("����� ���.");
		this.getColumn(this.getColumnName(3)).setHeaderValue("���� ���.");
		this.getColumn(this.getColumnName(4)).setHeaderValue("�������");
		this.getColumn(this.getColumnName(5)).setHeaderValue("��.�����");
	}
	
	/** ��������� ������� �� ����� ura_wp.dbf */
	private void fillUraWp(){
		for(int counter=0;counter<20;counter++){
			fieldName.add("");fieldValue.add("");
		}
		ResultSet rs=null;
		try{
			rs=this.connection.createStatement().executeQuery("SELECT * FROM URA_WP ");
			int position=0;
			System.out.println("URA_WP begin");
			while(rs.next()){
				//position=(new Float(rs.getFloat(1))).intValue();
				position=(new Float(rs.getFloat("WP1"))).intValue();
				System.out.println("WP: "+position+"   WP2:"+rs.getString("WP2")+"     WP7:"+rs.getString("WP7"));
				fieldName.set(position, rs.getString("WP2"));
				fieldValue.set(position,rs.getString("WP7"));
			}
			System.out.println("URA_WP end");
		}catch(Exception ex){
			System.err.println("fillUraWp:"+ex.getMessage());
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception ex){};
			}
		}
		/*
		fieldName.add("����� �� ����.");fieldValue.add("���.");//1
		fieldName.add("��������. �������");fieldValue.add("�2");//2
		fieldName.add("������i ������.");fieldValue.add("���");//3
		fieldName.add("����i��� ������.");fieldValue.add("���");//4
		fieldName.add("�����������������");fieldValue.add("���");//5
		fieldName.add("�i���.������� �� ���");fieldValue.add("���");//6
		fieldName.add("������� ����");fieldValue.add("�3");//7
		fieldName.add("������ ����");fieldValue.add("����");//8
		//fieldName.add("��i���� ���.��.���.");fieldValue.add("");//9
		fieldName.add("������ �� ���.");fieldValue.add("");//9
		fieldName.add("��������");fieldValue.add("����");//10
		fieldName.add("4,0% �i�.���.����.");fieldValue.add("���.");//11
		fieldName.add("�� ����i�.����.���.");fieldValue.add("���.");//12
		fieldName.add("����.�����.����i��.");fieldValue.add("���");//13
		fieldName.add("I�����. ��. ��.");fieldValue.add("���");//14
		fieldName.add("���");fieldValue.add("���");//15
		*/
	}
	
	/** �������� ������ */
	public void clearData(){
		this.model.clearData();
	}
	
	/** �������� � �������� ������ ������, ���������� ����� �����, ���� �� ������ ������, ���� =0*/
	private String getLongEmptyString(ResultSet rs, String columnName){
		String returnValue=null;
		try{
			long arn_okp=new Double(rs.getString(columnName)).longValue();
			if(arn_okp==0){
				// returnValue=null;
			}else{
				returnValue=Long.toString(arn_okp);
			}
		}catch(Exception ex){
			// String is empty
		}
		return (returnValue==null)?"":returnValue;
	}
	
	/** �������� � �������� ������ ������, ���������� ����� �����, ���� �� ������ ������, ���� =0<br>
	 * � ��������� ������ ������ ���������, �� ��������� ������, ���� ������, ������� � ������ ������
	 * */
	private String getLongEmptyStringWithPrefix(ResultSet rs, 
											    String columnName,
											    int minLength,
											    char prefix){
		String returnValue=null;
		try{
			long arn_okp=new Double(rs.getString(columnName)).longValue();
			if(arn_okp==0){
				// returnValue=null;
			}else{
				returnValue=Long.toString(arn_okp);
			}
		}catch(Exception ex){
			// String is empty
		}
		if(returnValue==null){
			// ������ �����
		}else{
			// ������ �� �����, ��������� �� ���-�� ������
			while(returnValue.length()<minLength){
				returnValue=prefix+returnValue;
			}
		}
		return (returnValue==null)?"":returnValue;
	}
	
	private String getEmptyStringWithPrefix(ResultSet rs, 
		    							    String columnName,
		    							    String prefix){
		String returnValue = null;
		try{
			returnValue=rs.getString(columnName).trim();
			if(returnValue.length()>0){
				returnValue=prefix+returnValue;
			}else{
				returnValue="";
			}
		}catch(Exception ex){
			System.err.println("getEmptyString WithPrefix Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	private String getEmptyString(ResultSet rs, String columnName){
		String returnValue="";
		try{
			if(rs.getString(columnName)!=null){
				returnValue=rs.getString(columnName);
			}
		}catch(Exception ex){
			System.err.println("getEmptyString Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	private SimpleDateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
	
	/** �������� ���� � ������� "dd.MM.yyyy" �� ���� TimeStamp */
	private String getFormatDateFromTimeStamp(ResultSet rs, String column){
		String returnValue=null;
		try{
			returnValue=dateFormat.format(rs.getDate(column));
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"":returnValue;
	}
	
	/** �������� ����� Float � ������� #,##*/
	private String getFormatCurrencyByString(ResultSet rs, String column){
		String returnValue=null;
		try{
			returnValue=MessageFormat.format("{0,number,#0.00}",new Object[]{rs.getFloat(column)});
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"0.00":returnValue;
	}
	
	/** �������� ����� Float � ������� #,## */
	private String getFormatCurrencyByFloat(Float value){
		return MessageFormat.format("{0,number,#0.00}",new Object[]{value});
	};

	/** �������� ����� Float � ������� #,##, ������������� ��� ����� */
	@SuppressWarnings("unused")
	private String getFormatCurrencyByString(ResultSet rs, String column, String column2){
		String returnValue=null;
		try{
			returnValue=MessageFormat.format("{0,number,#0.00}",new Object[]{rs.getFloat(column)+rs.getFloat(column2)});
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"0.00":returnValue;
	}
	
	/** ���������� ������������� ��������, ���� Float �������� � ResultSet �������� �� 0*/
	private Float getFloatFromResultSet(ResultSet rs, String column){
		float returnValue=0;
		try{
			returnValue=rs.getFloat(column);
		}catch(Exception ex){
			// returnValue=0;
		}
		return returnValue;
	}

	/** �������� �� ������ ������ ��������� ������� �� ���������, � ��� �� ������� � ���� ����� � 4 ������� ����� ������� */
	private String getFloatFromResultSetFour(ResultSet rs, String column){
		String returnValue=null;
		try{
			returnValue=MessageFormat.format("{0,number,#0.0000}",new Object[]{rs.getFloat(column)});
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"0.0000":returnValue;
	}
	/** �������� �� ������ ������ ��������� ������� �� ���������, � ��� �� ������� � ���� ����� � 5 ������� ����� ������� */
	private String getFloatFromResultSetFive(ResultSet rs, String column){
		String returnValue=null;
		try{
			returnValue=MessageFormat.format("{0,number,#0.00000}",new Object[]{rs.getFloat(column)});
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"0.0000":returnValue;
	}
	
	/** ������� � Excel ������� �� "��������" 
	 * @param month - ����� ������ ������
	 * @param year - ��� ������ ������
	 * @param jk - ���
	 * @param viddil - ��������� 
	 * @param date - ���� ������ 
	 * @param pathToExcelFile - ���� � ����� Excel
	 * @throws Exception - ����������, ������� ��������� 
	 */
	public void printSquareToExcel(String month, 
								   String year, 
								   String jk,
								   String viddil,
								   Calendar date,
								   String pathToExcelFile) throws Exception{
		// ������� ���� Excel
		// settings for workbook
		WorkbookSettings settings=new WorkbookSettings();
		settings.setEncoding("WINDOWS-1251");
		WritableWorkbook workbook=Workbook.createWorkbook(new File(pathToExcelFile),settings);
		// create sheet into workbook
		WritableSheet sheet=workbook.createSheet(month+" "+year, 0);
		// ������� �����
		CellView cellView=new CellView();
		WritableCellFormat center=new WritableCellFormat();
		WritableCellFormat right=new WritableCellFormat();
		WritableCellFormat left=new WritableCellFormat();
		WritableCellFormat rightFloat=new WritableCellFormat(new NumberFormat("#.00"));
		try{
			left.setAlignment(Alignment.LEFT);
			center.setAlignment(Alignment.CENTRE);
			right.setAlignment(Alignment.RIGHT);
			rightFloat.setAlignment(Alignment.RIGHT);
		}catch(Exception ex){
		}
		//�����+����� ����+����� ��������� (��������) - 
		cellView.setSize(7000);
		sheet.setColumnView(0, cellView);
		cellView.setFormat(left);
		//����� ����������� �������� �� arn
		cellView.setSize(10000);
		cellView.setFormat(left);
		sheet.setColumnView(1, cellView);
		//npom->nd ����� �������
		cellView.setSize(2500);
		cellView.setFormat(center);
		sheet.setColumnView(2, cellView);
		//npom->dd ���� ��������
		cellView.setSize(3000);
		cellView.setFormat(center);
		sheet.setColumnView(3, cellView);
		//npom->oplp ���������� �����
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(4, cellView);
		//��.�����
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(4, cellView);
		// ������� ���������
		sheet.addCell(new Label(2,0,"�i����:"+month+"  �i�:"+year+"  ���:"+jk+"  �i��i�����:"+viddil));
		float amount=0;
		float amountHome=0;
		String homeName="";
		String homeNumber="";
		String tempNumber;
		// ������� ������
		int row=2;
		for(int counter=0;counter<this.model.getRowCount();counter++){
			ResultSet rs = this.model.getResultSetByIndex(counter);
			rs.next();
			try {
				//counter<(this.model.getRowCount()-1)
				tempNumber=this.getLongEmptyString(rs,"NPOM_DM1")+" "+rs.getString("NPOM_LT1").trim();
				if((!homeName.equals(rs.getString("UL_NAIU")))||(!homeNumber.equals(tempNumber))){
					if(counter!=0){
						addAmountHome(++row, homeName, homeNumber, amountHome, sheet);
						row++;
					}
					homeName=rs.getString("UL_NAIU");
					homeNumber=tempNumber;
					amountHome=rs.getFloat("NPOM_OPLP");
				}else{
					amountHome+=rs.getFloat("NPOM_OPLP");
				}
				amount+=rs.getFloat("NPOM_OPLP");
				addRowToExcel(++row,rs,sheet);
			} catch (Exception ex) {
				System.out.println("printSelection ERROR:" + ex.getMessage());
			}
			rs.close();
		}
		addAmountHome(++row, homeName, homeNumber, amountHome, sheet);
		row+=2;
		addAmoutToEnd(row,amount,sheet);
		// ������� ���� Excel
		workbook.write();
		workbook.close();
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pathToExcelFile);		
	}
	
	private String getDoubleSymbolFromInteger(int value){
		if(value<10){
			return "0"+value;
		}else{
			return Integer.toString(value);
		}
	}
	
	/** �������� ����� ������ � ���, ������ �� �������� ������� PL */
	private String getMonthAndYear(){
		this.tableTnName.substring(4, 6);
		this.tableTnName.substring(6, 8);
		return this.tableTnName.substring(6,8)+"."+this.tableTnName.substring(4, 6);
	}
	/** �������� ��������� ����� ������ � ���, ������ �� �������� ������� PL*/
	private String getNextMonthAndYear(){
		this.tableTnName.substring(4, 6);
		this.tableTnName.substring(6, 8);
		if(Integer.parseInt(this.tableTnName.substring(6, 8))!=12){
			int value=Integer.parseInt(this.tableTnName.substring(6,8));
			return getDoubleSymbolFromInteger(value+1)
				   +"."
			       +this.tableTnName.substring(4, 6);
		}else{
			return "01."+this.getDoubleSymbolFromInteger(Integer.parseInt(this.tableTnName.substring(6,8)));
		}
	}
	
	/** ������� � Excel ��������� ��������� ��� ��� �1 
	 * @param month - ����� ������ ������
	 * @param year - ��� ������ ������
	 * @param jk - ��� ( �� ��������� ������ ������ ���� ������ - ������������ ������� ����)
	 * @param viddil - ��������� 
	 * @param date - ���� ������ 
	 * @param pathToExcelFile - ���� � ����� Excel
	 * @throws Exception - ����������, ������� ��������� 
	 */
	public void printOborotkaToExcel(String month, 
								   String year, 
								   String jk,
								   String viddil,
								   Calendar date,
								   String pathToExcelFile) throws Exception{
		// ������� ���� Excel
		// settings for workbook
		WorkbookSettings settings=new WorkbookSettings();
		settings.setEncoding("WINDOWS-1251");
		WritableWorkbook workbook=Workbook.createWorkbook(new File(pathToExcelFile),settings);
		// create sheet into workbook
		WritableSheet sheet=workbook.createSheet(month+" "+year, 0);
		// ������� �����
		CellView cellView=new CellView();
		WritableCellFormat center=new WritableCellFormat();
		WritableCellFormat right=new WritableCellFormat();
		WritableCellFormat left=new WritableCellFormat();
		WritableCellFormat rightFloat=new WritableCellFormat(new NumberFormat("#0.00"));
		try{
			left.setAlignment(Alignment.LEFT);
			center.setAlignment(Alignment.CENTRE);
			right.setAlignment(Alignment.RIGHT);
			rightFloat.setAlignment(Alignment.RIGHT);
		}catch(Exception ex){
			System.err.println("ResultTable create Alignment Error:"+ex.getMessage());
		}
			// Font
		WritableFont times14ptBoldUnderline = new WritableFont(WritableFont.TIMES,
				   14,
				   WritableFont.BOLD,
				   false,
				   UnderlineStyle.SINGLE);
		WritableFont times10ptBold = new WritableFont(WritableFont.TIMES,
				   10,
				   WritableFont.BOLD,
				   false,
				   UnderlineStyle.NO_UNDERLINE);
		WritableFont times10ptItalic = new WritableFont(WritableFont.ARIAL,
				   10,
				   WritableFont.NO_BOLD,
				   true,
				   UnderlineStyle.NO_UNDERLINE);
		
			// Format
		WritableCellFormat timesBoldUnderline = new WritableCellFormat(times14ptBoldUnderline);
		WritableCellFormat timesBold = new WritableCellFormat(times10ptBold);
		WritableCellFormat timesAmount = new WritableCellFormat(times10ptItalic,new NumberFormat("#0.00"));
		
		timesBold.setVerticalAlignment(VerticalAlignment.CENTRE);
		timesBold.setAlignment(Alignment.CENTRE);
		timesBold.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		timesBold.setWrap(true);
		timesBoldUnderline.setAlignment(Alignment.CENTRE);
		// ������� ��������� ��� �������
	    sheet.mergeCells(0, 0, 13, 0);
		sheet.addCell(new Label(0,0,
								"�������� �i���i���(��� 1) �� :"+month+"  �i�:"+year+"  �i��i�����:"+viddil,
								timesBoldUnderline
								)
					 );
		// (A) ���������� ����� �������:
		cellView.setSize(1000);
		cellView.setFormat(center);
		sheet.setColumnView(0, cellView);
		sheet.mergeCells(0, 2, 0, 4);
		sheet.addCell(new Label(0,2,"�",timesBold));
		// (B) ������������ �������� �� ��������
		cellView.setSize(7000);
		cellView.setFormat(left);
		sheet.setColumnView(1, cellView);
		sheet.mergeCells(1, 2, 1, 4);
		sheet.addCell(new Label(1,2,"������������ �������� �� ��������",timesBold));
		// (C) ������
		cellView.setSize(10000);
		cellView.setFormat(left);
		sheet.setColumnView(2, cellView);
		sheet.mergeCells(2, 2, 2, 4);
		sheet.addCell(new Label(2,2,"������",timesBold));
		// (D) �-�� �� 01.02.09:�-�	
			sheet.mergeCells(3, 2, 4, 3);	
			sheet.addCell(new Label(3,2,"�-�� �� 01."+this.getMonthAndYear(),timesBold));
		cellView.setSize(2500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(3, cellView);
		sheet.addCell(new Label(3,4,"�-�",timesBold));
		// (E) �-�� �� 01.02.09:�-�
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(4, cellView);
		sheet.addCell(new Label(4,4,"�-�",timesBold));
		// (F) ����� �� ����i�����
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(5, cellView);
		sheet.mergeCells(5, 2, 5, 4);
		sheet.addCell(new Label(5,2,"����� �� ����i�����",timesBold));
		// (G) ����������i��i �������
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(6, cellView);
		sheet.mergeCells(6, 2, 6, 4);
		sheet.addCell(new Label(6,2,"����������i��i �������",timesBold));
		// (H) I��i �����������
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(7, cellView);
		sheet.mergeCells(7, 2, 7, 4);
		sheet.addCell(new Label(7,2,"I��i �����������",timesBold));
		// (I) �����
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(8, cellView);
		sheet.mergeCells(8, 2, 8, 4);
		sheet.addCell(new Label(8,2,"�����",timesBold));
		// (J) ���      643
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(9, cellView);
		sheet.mergeCells(9, 2, 9, 4);
		sheet.addCell(new Label(9,2,"��� 643",timesBold));
		// (K) ������ ����������
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(10, cellView);
		sheet.mergeCells(10, 2, 10, 4);
		sheet.addCell(new Label(10,2,"������ ����������",timesBold));
		// (Q) ������ ����������
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(11, cellView);
		sheet.mergeCells(11, 2, 11, 4);
		sheet.addCell(new Label(11,2,"������ ����������",timesBold));
		// (S) �-�� �� 01.03.09: �-�
			sheet.mergeCells(12, 2, 13, 3);	
			sheet.addCell(new Label(12,2,"�-�� �� 01."+this.getNextMonthAndYear(),timesBold));
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(12, cellView);
		sheet.addCell(new Label(12,4,"�-�",timesBold));
		// (T) �-�� �� 01.03.09: �-�
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(13, cellView);
		sheet.addCell(new Label(13,4,"�-�",timesBold));
		
		sheet.addCell(new Label(15,2,"TN1"));
		sheet.addCell(new Label(16,2,"TNA"));
		// ������� ������
		int row=4;
		PreparedStatement columnQ=connection.prepareStatement("select sum(wpl) from "+this.tablePlName+" where "+this.tablePlName+".tna=? and "+this.tablePlName+".tn1=?");
		float g=0;
		float d_e=0;
		float h=0;
		float q=0;
		float s=0;
		float k=0;
		float f=0;
		String tempNumber="";
		String homeNumber="";
		String homeName="";
		int homeCounter=1;
		float[] amount=new float[11];
		
		for(int counter=0;counter<this.model.getRowCount();counter++){
			ResultSet rs = this.model.getResultSetByIndex(counter);
			rs.next();
			row++;
			try {
				// (C) ������
				tempNumber=this.getLongEmptyString(rs,"NPOM_DM1")+" "+rs.getString("NPOM_LT1").trim();
				if((!homeName.equals(rs.getString("UL_NAIU")))||(!homeNumber.equals(tempNumber))){
					homeCounter=0;
					homeName=rs.getString("UL_NAIU");
					homeNumber=tempNumber;
					//amountHome=rs.getFloat("NPOM_OPLP");
					if(counter>0){
						// output amount by building
						for(int index=0;index<amount.length;index++){
							sheet.addCell(new Number(3+index,row,amount[index],timesAmount));
						}
						row++;row++;
						for(int index=0;index<amount.length;index++)amount[index]=0;
					}
				}else{
					homeCounter++;
					}
				sheet.addCell(new Label(2,
										row,
										rs.getString("UL_NAIU").trim()+" "+this.getLongEmptyString(rs, "npom_dm1")+" "+rs.getString("NPOM_LT1").trim()+" "+rs.getString("NPOM_LT1_KW").trim()
										)
							  );
				
				// ����������� ��������
				sheet.addCell(new Number(15,row,rs.getInt("tn1")));
				sheet.addCell(new Number(16,row,rs.getInt("tna")));
				// (A) ���������� ����� �������:
				sheet.addCell(new Label(0,row,Integer.toString(homeCounter+1)));
				// (B) ������������ �������� �� ��������
				if((rs.getString("NPOM_ND")!=null)&&(!rs.getString("NPOM_ND").equals(""))){
					sheet.addCell(new Label(1,row,this.getEmptyString(rs, "arn_naim").trim()+"  ( "+this.getEmptyStringWithPrefix(rs, "NPOM_ND", "�")+" �i� "+this.getFormatDateFromTimeStamp(rs, "NPOM_DD")+" )"));
				}else{
					sheet.addCell(new Label(1,row,this.getEmptyString(rs, "arn_naim")));
				}

				// �-�� �� 01.02.09
				if(rs.getFloat("os_pl")>0){
					// (D) :�-�
					sheet.addCell(new Number(3,row,rs.getFloat("os_pl")));
					// (E) :�-�
					sheet.addCell(new Number(4,row,0));
					d_e=rs.getFloat("os_pl");
					amount[0]+=d_e;
				}else{
					// (D) :�-�
					sheet.addCell(new Number(3,row,0));
					// (E) :�-�
					sheet.addCell(new Number(4,row,Math.abs(rs.getFloat("os_pl"))));
					d_e=Math.abs(rs.getFloat("os_pl"));
					amount[1]+=d_e;
				}
				// (F) ����� �� ����i�����
				f=rs.getFloat("pl1")+rs.getFloat("pl14");
				sheet.addCell(new Number(5,row,f));
				amount[2]+=f;
				// (G) ����������i��i �������
				g=rs.getFloat("pl2");
				sheet.addCell(new Number(6,row,g));
				amount[3]+=g;
				// (H) I��i �����������
				h=rs.getFloat("pl3")+rs.getFloat("pl4")+rs.getFloat("pl5")+rs.getFloat("pl6")+
				  rs.getFloat("pl7")+rs.getFloat("pl8")+rs.getFloat("pl9")+rs.getFloat("pl10")+
				  rs.getFloat("pl11")+rs.getFloat("pl12")+rs.getFloat("pl13");
				sheet.addCell(new Number(7,row,h));
				amount[4]+=h;
				// (I) �����
				sheet.addCell(new Number(8,row,f+g+h));
				amount[5]+=g+h;
				// (J) ���      643
				sheet.addCell(new Number(9,row,rs.getFloat("pl15")));
				amount[6]+=rs.getFloat("pl15");
				// (K) ������ ����������
				k=g+h+rs.getFloat("pl15");
				sheet.addCell(new Number(10,row,k));
				amount[7]+=g+h+rs.getFloat("pl15");
				// (Q) ������ ����������
				try{
					q=0;
					columnQ.setFloat(1, rs.getFloat("tna"));
					columnQ.setFloat(2, rs.getFloat("tn1"));
					ResultSet rsQ=columnQ.executeQuery();
					if(rsQ.next()){
						q=rsQ.getFloat(1);
					}
					rsQ.close();
				}catch(Exception ex){
					System.err.println("Q column Exception:"+ex.getMessage());
				};
				sheet.addCell(new Number(11,row,q));
				amount[8]+=q;
				// �-�� �� 01.03.09:
				s=d_e // D-E
				 +k // +K
				 -q;// -Q
				if(s>0){
					// (S)  �-�
					sheet.addCell(new Number(12,row,s));
					// (T)  �-�
					sheet.addCell(new Number(13,row,(float)0));
					amount[9]+=s;
				}else{
					// (S)  �-�					
					sheet.addCell(new Number(12,row,(float)0));
					// (T)  �-�
					sheet.addCell(new Number(13,row,Math.abs(s)));
					amount[10]+=Math.abs(s);
				}

			} catch (Exception ex) {
				System.out.println("printSelection ERROR:" + ex.getMessage());
			}
			rs.close();
		}
		row++;
		// output amount by building
		for(int index=0;index<amount.length;index++){
			sheet.addCell(new Number(3+index,row,amount[index]));
		}
		
		row+=2;
		//addAmoutToEnd(row,amount,sheet);
		// ������� ���� Excel
		workbook.write();
		workbook.close();
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pathToExcelFile);		
	}
	
	
	/** ������� � Excel ��������� ��������� ��� ��� �2 
	 * @param month - ����� ������ ������
	 * @param year - ��� ������ ������
	 * @param jk - ��� ( �� ��������� ������ ������ ���� ������ - ������������ ������� ����)
	 * @param viddil - ��������� 
	 * @param date - ���� ������ 
	 * @param pathToExcelFile - ���� � ����� Excel
	 * @throws Exception - ����������, ������� ��������� 
	 */
	public void printOborotkaToExcel2(String month, 
								   String year, 
								   String jk,
								   String viddil,
								   Calendar date,
								   String pathToExcelFile) throws Exception{
		// ������� ���� Excel
		// settings for workbook
		WorkbookSettings settings=new WorkbookSettings();
		settings.setEncoding("WINDOWS-1251");
		WritableWorkbook workbook=Workbook.createWorkbook(new File(pathToExcelFile),settings);
		// create sheet into workbook
		WritableSheet sheet=workbook.createSheet(month+" "+year, 0);
		// ������� �����
		CellView cellView=new CellView();
		WritableCellFormat center=new WritableCellFormat();
		WritableCellFormat right=new WritableCellFormat();
		WritableCellFormat left=new WritableCellFormat();
		WritableCellFormat rightFloat=new WritableCellFormat(new NumberFormat("#0.00"));
		try{
			left.setAlignment(Alignment.LEFT);
			center.setAlignment(Alignment.CENTRE);
			right.setAlignment(Alignment.RIGHT);
			rightFloat.setAlignment(Alignment.RIGHT);
		}catch(Exception ex){
			System.err.println("ResultTable create Alignment Error:"+ex.getMessage());
		}
			// Font
		WritableFont times14ptBoldUnderline = new WritableFont(WritableFont.TIMES,
				   14,
				   WritableFont.BOLD,
				   false,
				   UnderlineStyle.SINGLE);
		WritableFont times10ptBold = new WritableFont(WritableFont.TIMES,
				   10,
				   WritableFont.BOLD,
				   false,
				   UnderlineStyle.NO_UNDERLINE);
		/*
		WritableFont times10ptItalic = new WritableFont(WritableFont.ARIAL,
				   10,
				   WritableFont.NO_BOLD,
				   true,
				   UnderlineStyle.NO_UNDERLINE);
		*/
			// Format
		WritableCellFormat timesBoldUnderline = new WritableCellFormat(times14ptBoldUnderline);
		WritableCellFormat timesBold = new WritableCellFormat(times10ptBold);
		//WritableCellFormat timesAmount = new WritableCellFormat(times10ptItalic,new NumberFormat("#0.00"));
		
		timesBold.setVerticalAlignment(VerticalAlignment.CENTRE);
		timesBold.setAlignment(Alignment.CENTRE);
		timesBold.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		timesBold.setWrap(true);
		timesBoldUnderline.setAlignment(Alignment.CENTRE);
		// ������� ��������� ��� �������
	    sheet.mergeCells(0, 0, 13, 0);
		sheet.addCell(new Label(0,0,
								"�������� �i���i���(��� 2) �� :"+month+"  �i�:"+year+"  �i��i�����:"+viddil,
								timesBoldUnderline
								)
					 );
		// (A) ���������� ����� �������:
		cellView.setSize(1000);
		cellView.setFormat(center);
		sheet.setColumnView(0, cellView);
		sheet.mergeCells(0, 2, 0, 4);
		sheet.addCell(new Label(0,2,"�",timesBold));
		// (B) ������������ �������� �� ��������
		cellView.setSize(7000);
		cellView.setFormat(left);
		sheet.setColumnView(1, cellView);
		sheet.mergeCells(1, 2, 1, 4);

		sheet.addCell(new Label(1,2,"������������ �������� �� ��������",timesBold));
		// (C) ������
		cellView.setSize(10000);
		cellView.setFormat(left);
		sheet.setColumnView(2, cellView);
		sheet.mergeCells(2, 2, 2, 4);
		sheet.addCell(new Label(2,2,"������",timesBold));
		// (D) �-�� �� 01.02.09:�-�	
		sheet.mergeCells(3, 2, 4, 3);	
		sheet.addCell(new Label(3,2,"�-�� �� 01."+this.getMonthAndYear(),timesBold));
			cellView.setSize(2500);
			cellView.setFormat(rightFloat);
			sheet.setColumnView(3, cellView);
			sheet.addCell(new Label(3,4,"�-�",timesBold));
			// (E) �-�� �� 01.02.09:�-�
			cellView.setSize(3000);
			cellView.setFormat(rightFloat);
			sheet.setColumnView(4, cellView);
			sheet.addCell(new Label(4,4,"�-�",timesBold));
		// (F) ������� ����������.
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(5, cellView);
		sheet.mergeCells(5, 2, 5, 4);
		sheet.addCell(new Label(5,2,"������� ����������.",timesBold));
		// (G) ������ ����������.
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(6, cellView);
		sheet.mergeCells(6, 2, 6, 4);
		sheet.addCell(new Label(6,2,"������ ����������.",timesBold));
		// (H) ��������
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(7, cellView);
		sheet.mergeCells(7, 2, 7, 4);
		sheet.addCell(new Label(7,2,"��������",timesBold));
		// (I) ��. ����������
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(8, cellView);
		sheet.mergeCells(8, 2, 8, 4);
		sheet.addCell(new Label(8,2,"��. ����������",timesBold));
		// (J) 4 % �i��
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(9, cellView);
		sheet.mergeCells(9, 2, 9, 4);
		sheet.addCell(new Label(9,2,"4 % �i��",timesBold));
		// (K) �����
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(10, cellView);
		sheet.mergeCells(10, 2, 10, 4);
		sheet.addCell(new Label(10,2,"�����",timesBold));

		// (L) ��� 643
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(11, cellView);
		sheet.mergeCells(11, 2, 11, 4);
		sheet.addCell(new Label(11,2,"��� 643",timesBold));

		// (M) ������ ����������
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(12, cellView);
		sheet.mergeCells(12, 2, 12, 4);
		sheet.addCell(new Label(12,2,"������ ����������",timesBold));

		// (S) ������ ����������
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(13, cellView);
		sheet.mergeCells(13, 2, 13, 4);
		sheet.addCell(new Label(13,2,"������ ����������",timesBold));
		
		// (U) �-�� �� 01.03.09: �-�
		sheet.mergeCells(14, 2, 15, 3);	
		sheet.addCell(new Label(14,2,"�-�� �� 01."+this.getNextMonthAndYear(),timesBold));
			cellView.setSize(3000);
			cellView.setFormat(rightFloat);
			sheet.setColumnView(14, cellView);
			sheet.addCell(new Label(14,4,"�-�",timesBold));
			// (V) �-�� �� 01.03.09: �-�
			cellView.setSize(3000);
			cellView.setFormat(rightFloat);
			sheet.setColumnView(15, cellView);
			sheet.addCell(new Label(15,4,"�-�",timesBold));
		
		sheet.addCell(new Label(16,2,"TN1"));
		sheet.addCell(new Label(17,2,"TNA"));
		// ������� ������
		int row=4;
		PreparedStatement columnK=connection.prepareStatement("select sum(wpl) from "+this.tablePlName+" where "+this.tablePlName+".tna=? and "+this.tablePlName+".tn1=?");
		float f=0;
		float g=0;
		float h=0;
		float i=0;
		float j=0;
		float k=0;
		float l=0;
		float d=0;
		float e=0;
		float m=0;
		float s=0;
		float u=0;
		String groupAddress=null;
		int groupCounter=0;
		float[] amount=new float[13];
		for(int counter=0;counter<this.model.getRowCount();counter++){
			ResultSet rs = this.model.getResultSetByIndex(counter);
			rs.next();
			row++;
			try {
				String currentAddress=rs.getString("UL_NAIU").trim()+this.getLongEmptyString(rs,"NPOM_DM1")+" "+rs.getString("NPOM_LT1").trim();
				if(currentAddress!=groupAddress){
					// new Address
					groupCounter=0;
					groupAddress=currentAddress;
					if(groupAddress!=null){
						// output Amount by Address
						for(int index=0;counter<amount.length;index++){
							sheet.addCell(new Number(index+3,row,amount[index]));
						}
						row++;
						row++;
					}else{
						// it is first line into output 
					}
				}
				groupCounter++;
				
				// (C) ������
				sheet.addCell(new Label(2,
						row,
						rs.getString("UL_NAIU").trim()+" "+this.getLongEmptyString(rs, "npom_dm1")+" "+rs.getString("NPOM_LT1").trim()+" "+rs.getString("NPOM_LT1_KW").trim()
						)
				);
				// ����������� ��������
				sheet.addCell(new Number(16,row,rs.getInt("tn1")));
				sheet.addCell(new Number(17,row,rs.getInt("tna")));
				// (A) ���������� ����� �������:
				sheet.addCell(new Label(0,row,Integer.toString(groupCounter)));
				// (B) ������������ �������� �� ��������
				if((rs.getString("NPOM_ND")!=null)&&(!rs.getString("NPOM_ND").equals(""))){
					sheet.addCell(new Label(1,row,this.getEmptyString(rs, "arn_naim").trim()+"  ( "+this.getEmptyStringWithPrefix(rs, "NPOM_ND", "�")+" �i� "+this.getFormatDateFromTimeStamp(rs, "NPOM_DD")+" )"));
				}else{
					sheet.addCell(new Label(1,row,this.getEmptyString(rs, "arn_naim")));
				}

				// �-�� �� 01.02.09
				if(rs.getFloat("os_pl")>0){
					// (D) :�-�
					d=rs.getFloat("os_pl");
					sheet.addCell(new Number(3,row,d));
					amount[0]+=d;
					// (E) :�-�
					e=0;
					sheet.addCell(new Number(4,row,e));
				}else{
					// (D) :�-�
					d=0;
					sheet.addCell(new Number(3,row,d));
					// (E) :�-�
					e=Math.abs(rs.getFloat("os_pl"));
					sheet.addCell(new Number(4,row,e));
					amount[1]+=e;
				}
				// (F) ������� ����������.
				f=rs.getFloat("pl7");
				sheet.addCell(new Number(5,row,f));
				amount[2]+=f;

				// (G) ������ ����������.
				g=rs.getFloat("pl8");
				sheet.addCell(new Number(6,row,g));
				amount[3]+=g;

				// (H) ��������
				h=rs.getFloat("pl10");
				sheet.addCell(new Number(7,row,h));
				amount[4]+=h;

				// (I) ��. ����������
				i=rs.getFloat("pl9");
				sheet.addCell(new Number(8,row,i));
				amount[5]+=i;

				// (J) 4 % �i��
				j=rs.getFloat("pl11");
				sheet.addCell(new Number(9,row,j));
				amount[6]+=j;

				// (K) �����
				k=f+g+h+i+j;
				sheet.addCell(new Number(10,row,k));
				amount[7]+=k;

				// (L) ��� 643
				l=rs.getFloat("pl15");
				sheet.addCell(new Number(11,row,l));
				amount[8]+=l;

				// (M) ������ ����������
				m=k+l;
				sheet.addCell(new Number(12,row,m));
				amount[9]+=m;

				// (S) ������ ����������
				//k=g+h+rs.getFloat("pl15");
				try{
					s=0;
					columnK.setFloat(1, rs.getFloat("tna"));
					columnK.setFloat(2, rs.getFloat("tn1"));
					ResultSet rsK=columnK.executeQuery();
					if(rsK.next()){
						s=rsK.getFloat(1);
					}
					rsK.close();
				}catch(Exception ex){
					System.err.println("K column Exception:"+ex.getMessage());
				};
				sheet.addCell(new Number(13,row,s));
				amount[10]+=s;
				// �-�� �� 01.03.09:
				u=d-e+m-s;
				if(u>0){
					// (U)  �-�
					sheet.addCell(new Number(14,row,u));
					amount[11]+=u;
					// (V)  �-�
					sheet.addCell(new Number(15,row,(float)0));
				}else{
					// (U)  �-�					
					sheet.addCell(new Number(14,row,(float)0));
					// (V)  �-�
					sheet.addCell(new Number(15,row,Math.abs(u)));
					amount[12]+=Math.abs(u);
				}

			} catch (Exception ex) {
				System.out.println("printSelection ERROR:" + ex.getMessage());
			}
			rs.close();
		}
		//addAmoutToEnd(row,amount,sheet);
		// ������� ���� Excel
		workbook.write();
		workbook.close();
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pathToExcelFile);		
	}

	
	/** �������� ������ � ���� 
	 * @param row - ������, � ������� ������� �������� ������ �� ResultSet
	 * @param rs - ����� ������ 
	 * @param sheet - ����, � ������� ������� �������� ������ 
	 */
	private void addRowToExcel(int row, ResultSet rs, WritableSheet sheet){
		try{
			sheet.addCell(new Label(0,row,rs.getString("UL_NAIU").trim()
										 +" "
										 +this.getLongEmptyString(rs,"NPOM_DM1")
										 +getEmptyStringWithPrefix(rs,"NPOM_LT1",", ")
										 +getEmptyStringWithPrefix(rs,"NPOM_LT1_KW",", ")
										 ));// �����
			sheet.addCell(new Label(1,row,rs.getString("ARN_NAIM")));// �������� �����������
			sheet.addCell(new Label(2,row,rs.getString("NPOM_ND")));// ����� ��������
			sheet.addCell(new Label(3,row,this.getFormatDateFromTimeStamp(rs, "NPOM_DD")));// ���� ��������
			sheet.addCell(new Number(4,row,rs.getFloat("NPOM_OPLP")));// ������������ �������
			sheet.addCell(new Label(5,row,rs.getString("ARN_ADR")));// ��.�����
		}catch(Exception ex){
			System.err.println("addRowToExcel Exception: "+ex.getMessage());
		}
	}
	/** �������� ����� � ����� ����� */
	private void addAmoutToEnd(int row, float amount, WritableSheet sheet){
		try{
			sheet.addCell(new Label(3,row,"������:"));
			sheet.addCell(new Number(4,row,amount));
		}catch(Exception ex){
			System.err.println("addAmount ToExcel:"+ex.getMessage());
		}
	}
	
	/** �������� � ���� ������ � ����� �� ����������� ���� 
	 * @param row - ������
	 * @param homeName - ��� ����
	 * @param numberName - ����� ����
	 * @param amount - �����
	 * @param sheet - ����
	 */
	private void addAmountHome(int row, String homeName, String numberName, float amount, WritableSheet sheet){
		try{
			sheet.addCell(new Label(0,row,homeName.trim()+" "+numberName));
			sheet.addCell(new Label(3,row,"����:"));
			sheet.addCell(new Number(4,row,amount));
		}catch(Exception ex){
			System.err.println("addAmount ToExcel:"+ex.getMessage());
		}
	}
	
	
	/** ���������� ���������� ������ � ���� ����-������� 
	 * @param monthName - ��� ������, �� �������� ��������� �����
	 * @param year - ���, �� �������� ��������� ����� 
	 * @param now - ����, �� ������� ��������� �����
	 * @param isJk1 - ���� ����� �������� �� ������ ���1
	 * @param showBord - ���������� �� ������������� �� ��������� ���������
	 * @param showProplata - �������� � ����������� ��������
	 * @param pathToOutFile - ���� � ����� ��� ������ ������
	 * */
	public void printSelection(String monthName, 
			   				   String year, 
			   				   Calendar now,
			   				   boolean isJk1,
			   				   boolean showBorg,
			   				   boolean showProplata,
			   				   String pathToOutFile){
		ResultSet rs=this.model.getResultSetByIndex(this.convertRowIndexToModel(this.getSelectedRow()));
		try{
			FileOutputStream fos=new FileOutputStream(pathToOutFile);
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fos));
			this.printResultSet(monthName, year, now, pathToOutFile, rs,isJk1,showBorg,showProplata, writer,true,true);
			writer.close();
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+pathToOutFile);
			rs.close();
		}catch(Exception ex){
			System.out.println("printSelection ERROR:"+ex.getMessage());
		}
	}
	/** ���������� ���������� ������ � ���� ���� 
	 * @param monthName - ��� ������, �� �������� ��������� �����
	 * @param year - ���, �� �������� ��������� ����� 
	 * @param now - ����, �� ������� ��������� �����
	 * */
	public void printSelectionAkt(String monthName, 
			   				   String year, 
			   				   Calendar now,
			   				   String pathToOutFile){
		try{
			FileOutputStream fos=new FileOutputStream(pathToOutFile);
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fos));
			ResultSet rs=this.model.getResultSetByIndex(this.convertRowIndexToModel(this.getSelectedRow()));
			this.printResultSetAsAkt(monthName, year, now, rs,writer,true,false,false);
			rs.previous();
			this.printResultSetAsAkt(monthName, year, now, rs,writer,false,true,true);
			writer.close();
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+pathToOutFile);
			rs.close();
		}catch(Exception ex){
			System.out.println("printSelectionAkt ERROR:"+ex.getMessage());
		}
	}

	public void printAll(String monthName, 
						 String year, 
						 Calendar now,
						 boolean isJk1,
						 boolean showBorg,
						 boolean showProplata,
						 String pathToOutFile) {
		try{
			FileOutputStream fos=new FileOutputStream(pathToOutFile);
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fos));
			for(int counter=0;counter<this.model.getRowCount();counter++){
				ResultSet rs = this.model.getResultSetByIndex(counter);
				try {
					if(counter==0){
						this.printResultSet(monthName, year, now, pathToOutFile, rs,isJk1,showBorg, showProplata, writer,true,false);
					}else{
						if(counter<(this.model.getRowCount()-1)){
							this.printResultSet(monthName, year, now, pathToOutFile, rs,isJk1,showBorg, showProplata, writer,false,false);
						}else{
							this.printResultSet(monthName, year, now, pathToOutFile, rs,isJk1,showBorg,  showProplata,writer,false,true);
						}
					}
				rs.close();	
				} catch (Exception ex) {
					System.out.println("printSelection ERROR:" + ex.getMessage());
				}
			}
			writer.close();
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pathToOutFile);
		}catch(Exception ex){
			System.out.println("printAll ERROR:" + ex.getMessage());
		}
	}
	
	public void printAllAkt(String monthName, 
			 String year, 
			 Calendar now,
			 String pathToOutFile) {
		try {
			FileOutputStream fos = new FileOutputStream(pathToOutFile);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fos));
			for (int counter = 0; counter < this.model.getRowCount(); counter++) {
				ResultSet rs = this.model.getResultSetByIndex(counter);
				try {
					if (counter == 0) {
						this.printResultSetAsAkt(monthName, year, now,rs, writer, true, false,false);
						rs.previous();
						this.printResultSetAsAkt(monthName, year, now,rs, writer, false, false,true);
					} else {
						if (counter < (this.model.getRowCount() - 1)) {
							this.printResultSetAsAkt(monthName, year, now,rs, writer, false, false,false);
							rs.previous();
							this.printResultSetAsAkt(monthName, year, now,rs, writer, false, false,true);
						} else {
							this.printResultSetAsAkt(monthName, year, now,rs, writer, false, false,false);
							rs.previous();
							this.printResultSetAsAkt(monthName, year, now,rs, writer, false, true,(counter&1)==1);
						}
					}
					rs.close();
				} catch (Exception ex) {
					System.out.println("printSelection ERROR:"
							+ ex.getMessage());
				}
			}
			writer.close();
			Runtime.getRuntime().exec(
					"rundll32 url.dll,FileProtocolHandler " + pathToOutFile);
		} catch (Exception ex) {
			System.out.println("printAllAkt ERROR:" + ex.getMessage());
		}
	}
	

	/** ������ ResultSet ��� ���� 
	 * @param monthName - �����
	 * @param year - ���
	 * @param now - ����� ������
	 * @param pathToOutFile - ���� � �����, � ������� ����� �������� ������
	 * @param rs - ����� ������ ��� ������ ����� 
	 * @param printWriter - 
	 * @param printHeader
	 * @param printFooter
	 * @param setPageDelimeter
	 */
	public void printResultSetAsAkt(String monthName, 
							   		String year, 
							   		Calendar now,
							   		ResultSet rs,
							   		BufferedWriter printWriter,
							   		boolean printHeader,
							   		boolean printFooter,
							   		boolean setPageDelimeter){
		MultiReplacer multi=new MultiReplacer("<tr><td width=100></td><td align=\"left\" width=300 >{0}</td><td align=\"right\" width=150> {1} ���.</td><td width=50></td></tr>",
											  "<tr><td width=100></td><td align=\"left\" width=300 ></td><td align=\"right\" width=150> </td><td width=50></td></tr>");
		try{
			if(rs.next()){
				// data for output
				ArrayList<ReplaceValue> values=new ArrayList<ReplaceValue>();
				multi.clearObjects();
				try{
					values.add(new SingleReplacer(rs.getString("NPOM_ND")));//1 - ����� �������� �� ����
					values.add(new SingleReplacer(dateFormat.format(now.getTime())));//2 - ���� ���������� �������� 
					values.add(new SingleReplacer(rs.getString("ARN_NAIM")));// 3 - ������ ��� 
					values.add(new SingleReplacer(rs.getString("UL_NAIU")+" &nbsp "
		  				      					  +this.getLongEmptyString(rs, "npom_dm1")+"&nbsp "
		  				      					  +rs.getString("NPOM_LT1").trim()+"&nbsp "
		  				      					  +rs.getString("NPOM_LT1_KW").trim()
		  				      					  +", "+MessageFormat.format("{0,number,#0.00}", new Object[]{getFloatFromResultSet(rs,"NPOM_OPLP")})+" �2."
		  				      					  )
							  );// 4 - ������ ����� ( + ������� )
					values.add(new SingleReplacer(monthName+" "+year));//5 - ����� � ���
					Float currentFloat;
					float amount=0;
					currentFloat=getFloatFromResultSet(rs,"PL1");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(1),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL2");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(2),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL3");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(3),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL4");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(4),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL5");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(5),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL6");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(6),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL7");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(7),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL8");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(8),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL9");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(9),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL10");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(10),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL11");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(11),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL12");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(12),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL13");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(13),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL14");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(14),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					values.add(multi);
					values.add(new SingleReplacer(getFormatCurrencyByString(rs,"PL15")));// 7 -���
					values.add(new SingleReplacer(getFormatCurrencyByString(rs,"PL")));// 8 - �����

					values.add(new SingleReplacer(this.rekvisit.get(0)));
					values.add(new SingleReplacer(this.rekvisit.get(4)));
					values.add(new SingleReplacer(this.rekvisit.get(3)));
					values.add(new SingleReplacer(this.rekvisit.get(2)));
					
					values.add(new SingleReplacer(rs.getString("ARN_NAIM")));// 9 - ������ ��� 
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_RASCH1")));// 10 - ��������� ����
					values.add(new SingleReplacer(rs.getString("MFO_NAME"))); // 11 - ����
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_MFO1")));//12 - ���
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_OKP")));//13 - ��� �����
					if(setPageDelimeter==true){
						values.add(new SingleReplacer("<p style=\"page-break-before: always\">"));// page delimeter
					}else{
						values.add(new SingleReplacer(""));
					}
				}catch(Exception ex){
					System.out.println("Exception: "+ex.getMessage());
				}
				
				HtmlReplacer replacer=new HtmlReplacer(this.pathToPatternAkt,
													   "WINDOWS-1251",
													   "value",
													   values);
				// INFO ����� ���������� ���������� �������
				replacer.printResultToOutputStream(printWriter,printHeader,"���",printFooter);
				System.out.println("printSelection: OK");
			}else{
				JOptionPane.showMessageDialog(this, "Data not found");
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Exception:"+ex.getMessage());
		}
		
	}
	
	/** ������ ResultSet ��� ����-������� 
	 * @param monthName - ��� ������, �� �������� ��������� �����
	 * @param year - ���, �� �������� ��������� �����
	 * @param pathToOutFile - ���� � ����� ��� ������ ������
	 * @param rs - ����� ������ 
	 * @param showBord - ���������� �� ������������� �� ��������� ���������
	 * @param showProplata - �������� � ������ ����������� �������� 
	 * @param printWriter - ������ ��� ������ ������
	 * @param printHeader - ������ ���������
	 * @param printFooter - print footer
	 * */
	private void printResultSet(String monthName, 
							   String year, 
							   Calendar now,
							   String pathToOutFile,
							   ResultSet rs,
							   boolean isJk1,
							   boolean showBorg,
							   boolean showProplata,
							   BufferedWriter printWriter,
							   boolean printHeader,
							   boolean printFooter){
		MultiReplacer multi=new MultiReplacer("<tr><td width=200 align=\"center\">{0}</td><td width=80 align=\"center\">{1}</td><td width=80 align=\"right\">{2}</td><td width=110 align=\"right\">{3}</td><td width=80 align=\"right\">{4}</td><td ></td></tr>",
											  "<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
		try{
			if(rs.next()){
				ResultSet rsProplata=null;
				if(showProplata==true){
					rsProplata=this.model.getResultSetForProplata(rs.getFloat("TNA"), rs.getFloat("TN1"));
				}
				// data for output
				ArrayList<ReplaceValue> values=new ArrayList<ReplaceValue>();
				multi.clearObjects();
				try{
					values.add(new SingleReplacer(getLongEmptyString(rs,"NPOM_PLAT")));
					values.add(new SingleReplacer(MessageFormat.format("{0,number,#0}", new Object[]{new Float(rs.getFloat("npom_jk1"))})));
					values.add(new SingleReplacer(dateFormat.format(now.getTime())));
					for(int counter=0;counter<7;counter++){
						values.add(new SingleReplacer(this.rekvisit.get(counter)));
					}
					values.add(new SingleReplacer(rs.getString("ARN_NAIM")));
					values.add(new SingleReplacer(rs.getString("ARN_ADR")));
					values.add(new SingleReplacer(getLongEmptyStringWithPrefix(rs,"ARN_OKP",8,'0')));// �������.���
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_MFO1")));
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_RASCH1")));
					values.add(new AddPrefixIsNotEmptyRaplacer(",&nbsp",rs.getString("MFO_NAME")));
					if(isJk1==true){
						// isJk1
						values.add(new SingleReplacer(""));
					}else{
						// isJk2
						values.add(new SingleReplacer("����������� "));
					}
					values.add(new SingleReplacer(monthName+" "+year));
					values.add(new SingleReplacer(rs.getString("UL_NAIU")+" &nbsp "
							  				      //+rs.getString("NPOM_LT1_KW")+"&nbsp "
							  				      +this.getLongEmptyString(rs, "npom_dm1")+"&nbsp "
							  				      +rs.getString("NPOM_LT1")+"&nbsp "
							  				      +rs.getString("NPOM_LT1_KW")+",&nbsp "
												  +" &nbsp&nbsp ( ��� � &nbsp "
												  +rs.getString("NPOM_ND")+" &nbsp �i� &nbsp "
												  +getFormatDateFromTimeStamp(rs,"NPOM_DD")
												  +")")
												  );
					
					Float currentFloat;
					float amount=0;
					String prefixBorg="���� ";
					// ----------------------- 1 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL1");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL1");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(1),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL1");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(1),
								  this.fieldValue.get(1),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}",
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 2 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL2");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL2");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(2),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL2");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(2),
								  this.fieldValue.get(2),
								  getFloatFromResultSetFour(rs,"NPOM_OPLP"),
								  getFloatFromResultSetFive(rs,"ura_pp_stpl2"),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 3 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL3");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL3");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(3),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL3");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(3),
								  this.fieldValue.get(3),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 4 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL4");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL4");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(4),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL4");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(4),
								  this.fieldValue.get(4),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 5 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL5");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL5");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(5),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL5");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(5),
								  this.fieldValue.get(5),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 6 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL6");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL6");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(6),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL6");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(6),
								  this.fieldValue.get(6),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 7 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL7");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL7");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(7),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL7");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(7),
								  this.fieldValue.get(7),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 8 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL8");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL8");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(8),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL8");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(8),
								  this.fieldValue.get(8),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 9 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL9");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL9");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(9),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL9");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(9),
								  this.fieldValue.get(9),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 10 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL10");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL10");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(10),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL10");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(10),
								  this.fieldValue.get(10),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 11 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL11");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL11");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(11),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL11");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(11),
								  this.fieldValue.get(11),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 12 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL12");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL12");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(12),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL12");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(12),
								  this.fieldValue.get(12),
								  "1,000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 13 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL13");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL13");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(13),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL13");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(13),
								  this.fieldValue.get(13),
								  "1,0000",
								  MessageFormat.format("{0,number,#.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 14 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL14");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL14");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(14),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// ���� is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL14");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(14),
								  this.fieldValue.get(14),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					values.add(multi);
					values.add(new SingleReplacer(MessageFormat.format("{0,number,#0.00}", new Object[]{new Float(amount)})));// ������
					float amountPl15=0; // ���
					float amountPl=0; // �����

					if(showBorg){
						amountPl15+=rs.getFloat("OS_PL15");
						amountPl+=rs.getFloat("OS_PL");
					}
					if(showProplata){
						rsProplata.beforeFirst();
						while(rsProplata.next()){
							amountPl15-=rsProplata.getFloat("PL15");
							amountPl-=rsProplata.getFloat("PL");
						}
					}
					amountPl15+=rs.getFloat("PL15");
					amountPl+=rs.getFloat("PL");
					values.add(new SingleReplacer(getFormatCurrencyByFloat(amountPl15)));
					values.add(new SingleReplacer(getFormatCurrencyByFloat(amountPl)));
					values.add(new SingleReplacer(moneyToString.moneytostr(new Double(amountPl))));
					values.add(new SingleReplacer("<p style=\"page-break-before: always\">"));// page delimeter
				}catch(Exception ex){
					System.out.println("Exception: "+ex.getMessage());
				}
				if(rsProplata!=null){
					try{
						rsProplata.close();
					}catch(Exception ex){}
				}
				HtmlReplacer replacer=new HtmlReplacer(this.pathToPattern,
													   "WINDOWS-1251",
													   "value",
													   values);
				// INFO ����� ���������� ���������� �������
				replacer.printResultToOutputStream(printWriter,printHeader,"����-�������",printFooter);
				System.out.println("printSelection: OK");
			}else{
				JOptionPane.showMessageDialog(this, "Data not found");
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Exception:"+ex.getMessage());
		}
	}
	private MoneyToString moneyToString=new MoneyToString();
	
	/** "��������" ������ 
	 * @param currentDate - ������� ����, �� ������� ���������� �������
	 * @param month - �����(0..11), �� �������� ���������� ��������� ���� 
	 * @param year - ���
	 * @param jk1 - ��� 1
	 * @param jk2 - ��� 2
	 * @param borg - ����� �� �������� �������������
	 * @param proplata - ����� �� �������� ��������
	 * @param viddil - ����� ������
	 * @param plataAll - ��� �1 - ��� ������  
	 * @param plataYes - ��� �1 - ������ ���� ���� ����� �� ���������  
	 * @param plataNo - ��� �1 - ������ ���� ��� ����� �� ���������  
	 * @param pochta - ����� �������� �� ������ �� ����� Pochta.dbf, � �� �� �������
	 */
	public void refreshData(Date currentDate,
							int month, 
							int year, 
							boolean jk1, 
							boolean jk2,
							String viddil,
							boolean plataAll,
							boolean plataYes,
							boolean plataNo,
							boolean pochta){
		fillUraWp();
		this.tableTnName=MessageFormat.format("tn{0,number,0000}{1,number,00}", new Object[]{new Integer(year),new Integer(month+1)});
		this.tableOsName=MessageFormat.format("os{0,number,0000}{1,number,00}", new Object[]{new Integer(year),new Integer(month+1)});
		this.tablePlName=MessageFormat.format("pl{0,number,0000}{1,number,00}", new Object[]{new Integer(year),new Integer(month+1)});
		if(jk1==true){
			this.whereJK=" npom.JK1=1 ";
			if(plataAll==false){
				if(plataYes==true){
					this.whereJK+="AND "+this.tableTnName+".pl1<>0 ";
				}
				if(plataNo==true){
					this.whereJK+="AND "+this.tableTnName+".pl1=0 ";
				}
			}
		}
		if(jk2==true){
			this.whereJK=" npom.JK1=2 ";
		}
		this.viddil=viddil;
		this.pochta=pochta;
		this.model.refresh(this.getSqlText(currentDate),
						   this.getSqlWhereId(), 
						   this.getSqlOrderBy(),
						   this.getSqlProplata()
						   );
		// �������� � ���������� ���������� �����������
		ResultSet rs=null;
		try{
			rs=this.connection.createStatement().executeQuery("SELECT NAIM, ADR, OKP, MFO1, RASCH1, NOM_DPA, SWID_DPA FROM ARN WHERE ARN.AKOD=22");
			rekvisit.clear();
			rs.next();
			// naim
			rekvisit.add(rs.getString(1));
			// adr
			rekvisit.add(rs.getString(2));
			// okp
			rekvisit.add((new Long((new Float(rs.getFloat(3))).longValue())).toString());
			// mfo1
			rekvisit.add((new Long((new Float(rs.getFloat(4))).longValue())).toString());
			// RASCH1
			rekvisit.add(this.getBeforeComma(rs.getString(5)));
			// NOM_DPA
			rekvisit.add(this.getBeforeComma(rs.getString(6)));
			// SWID_DPA
			rekvisit.add(this.getBeforeComma(rs.getString(7)));
		}catch(Exception ex){
			
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
	}

	/** ���� � ������ ���� ������� - �������� ������� �� ����� "�������" */
	private String getBeforeComma(String value){
		int indexOfComma=value.indexOf(',');
		if(indexOfComma>0){
			return getBeforeDot(value.substring(0,indexOfComma).trim());
		}else{
			return getBeforeDot(value);
		}
	}
	/** ���� � ������ ���� ����� - �������� ������� �� ����� "�����" */
	private String getBeforeDot(String value){
		int indexOfComma=value.indexOf('.');
		if(indexOfComma>0){
			return value.substring(0,indexOfComma).trim();
		}else{
			return value;
		}
	}
	
	/** �������� SQL query for PreparedStatement query */
	private String getSqlProplata(){
		StringBuffer returnValue=new StringBuffer();
		returnValue.append("select * from "+this.tablePlName+" where tna=? and tn1=?");
		return returnValue.toString();
	}
	
	/** �������� ������ SQL 
	 * @param currentDate - ������� ����, �� ������� �������� ������ ������
	 * @param borg - ����� �� �������� �������������
	 * */
	private String getSqlText(Date currentDate){
		boolean borg=true;
		StringBuffer result=new StringBuffer();
		result.append("	select distinct "+this.tableTnName+".tn1,\n");
		result.append("	        "+this.tableTnName+".tna,\n");
		result.append("	        npom.kod_pom,	\n");
		result.append("	        npom.aren,	\n");
		result.append("	        '-',	\n");
		result.append("	        arn.naim arn_naim,	\n");// 6
		result.append("	        arn.adr arn_adr,	\n");
		result.append("	        ul.naiu ul_naiu,	\n");//8
		result.append("	        mfo.mfo1 mfo_number, \n");
		result.append("	        mfo.mfo2 mfo_name, \n"); //10
		result.append("	        npom.dm1 npom_dm1,	\n");//11
		result.append("	        npom.nd npom_nd,	\n");//12
		result.append("	        npom.lt1 npom_lt1,	\n");//13
		result.append("	        npom.lt1_kw npom_lt1_kw,	\n");
		result.append("	        npom.dd npom_dd,	\n");//15
		result.append("	        npom.plat npom_plat,	\n");
		result.append("	        npom.oplp npom_oplp,	\n");
		result.append("	        arn.okp arn_okp,	\n");
		result.append("	        arn.mfo1 arn_mfo1,	\n");
		result.append("	        npom.JK1 npom_jk1,	\n"); //20
		result.append("	        arn.rasch1 arn_rasch1,	\n");
		result.append("         ura_pp.stpl2 ura_pp_stpl2, \n");
		result.append("	        "+this.tableTnName+".pl1 pl1,	\n");
		result.append("	        "+this.tableTnName+".pl2 pl2,	\n");
		result.append("	        "+this.tableTnName+".pl3 pl3,	\n");
		result.append("	        "+this.tableTnName+".pl4 pl4,	\n");
		result.append("	        "+this.tableTnName+".pl5 pl5,	\n");
		result.append("	        "+this.tableTnName+".pl6 pl6,	\n");
		result.append("	        "+this.tableTnName+".pl7 pl7,	\n");
		result.append("	        "+this.tableTnName+".pl8 pl8,	\n");
		result.append("	        "+this.tableTnName+".pl9 pl9,	\n");
		result.append("	        "+this.tableTnName+".pl10 pl10,	\n");
		result.append("	        "+this.tableTnName+".pl11 pl11,	\n");
		result.append("	        "+this.tableTnName+".pl12 pl12,	\n");
		result.append("	        "+this.tableTnName+".pl13 pl13,	\n");
		result.append("	        "+this.tableTnName+".pl14 pl14,	\n");
		result.append("	        "+this.tableTnName+".pl15 pl15,	\n");
		/*os200902.tna=tn200902.tna and 
		os200902.tn1=tn200902.tn1
		����� �������� os200902.pl2..pl14
		*/
		if(borg){
			result.append("	        "+this.tableOsName+".pl os_pl,	\n");
			result.append("	        "+this.tableOsName+".pl1 os_pl1,	\n");
			result.append("	        "+this.tableOsName+".pl2 os_pl2,	\n");
			result.append("	        "+this.tableOsName+".pl3 os_pl3,	\n");
			result.append("	        "+this.tableOsName+".pl4 os_pl4,	\n");
			result.append("	        "+this.tableOsName+".pl5 os_pl5,	\n");
			result.append("	        "+this.tableOsName+".pl6 os_pl6,	\n");
			result.append("	        "+this.tableOsName+".pl7 os_pl7,	\n");
			result.append("	        "+this.tableOsName+".pl8 os_pl8,	\n");
			result.append("	        "+this.tableOsName+".pl9 os_pl9,	\n");
			result.append("	        "+this.tableOsName+".pl10 os_pl10,	\n");
			result.append("	        "+this.tableOsName+".pl11 os_pl11,	\n");
			result.append("	        "+this.tableOsName+".pl12 os_pl12,	\n");
			result.append("	        "+this.tableOsName+".pl13 os_pl13,	\n");
			result.append("	        "+this.tableOsName+".pl14 os_pl14,	\n");
			result.append("	        "+this.tableOsName+".pl15 os_pl15,	\n");
		}
		result.append("	        "+this.tableTnName+".pl pl	\n");
		result.append("	from "+this.tableTnName+"	\n");
		result.append("	inner join npom on npom.aren="+this.tableTnName+".tna and npom.kod_pom="+this.tableTnName+".tn1	\n");
		result.append("	inner join arn on arn.akod="+this.tableTnName+".tna	\n");
		result.append("	left join ul on ul.ul=npom.ul1	\n");
		result.append("	left join mfo on mfo.mfo1=arn.mfo1 \n");
		result.append("	left join ura_pp on ura_pp.tpa="+this.tableTnName+".tna and ura_pp.tpn="+this.tableTnName+".tn1	\n");
		if(borg){
			result.append("inner join "+this.tableOsName+" on "+this.tableOsName+".tna="+this.tableTnName+".tna and "+this.tableOsName+".tn1="+this.tableTnName+".tn1 \n");
		}
		if(this.pochta){
			result.append("inner join pochta on pochta.akod=arn.akod \n");
		}else{
			result.append("left join viddil_js on (viddil_js.ul1=npom.ul1 and viddil_js.dm1=npom.dm1 \n");
			result.append("and trim(both from viddil_js.lt1)=trim(both from npom.lt1)) \n");
		}
		result.append("where "+this.whereJK+" AND "+this.tableTnName+".pl<>0 \n");
		
		if(this.pochta){
			// pochta
		}else{
			if(this.viddil.length()>1){
				// viddil is not number
				result.append("and viddil_js.viddil is null\n");
			}else{
				// viddil is number 
				result.append("and viddil_js.viddil="+this.viddil+"\n");
			}
		}
		//result.append("AND convert(npom.do_ref,DATE)> convert('"+this.dateSql.format(currentDate)+"',DATE) \n");
		//result.append("AND npom.do_ref> '"+this.dateSql.format(currentDate)+"' \n");
		return result.toString();
	}
	
	/** INFO ����������� HSQLDb TIMESTAMP */
	@SuppressWarnings("unused")
	private SimpleDateFormat dateSql=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** ������� ����� �������, ������� �������� �� OrderBy*/
	private String getSqlOrderBy(){
		return "	order by ul.naiu,npom.dm1 ";
	}
	
	/** ������� ����� �������, ������� �������� �� ��������� ���������� ������, ��� ���������� � ��� ������*/
	private String getSqlWhereId(){
		return "AND "+this.tableTnName+".tn1=";
	}
}

