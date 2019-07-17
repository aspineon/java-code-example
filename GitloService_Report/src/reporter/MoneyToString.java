package reporter;

import java.text.MessageFormat;


public class MoneyToString {
	public static void main(String[] args){
		System.out.println("begin:");
		MoneyToString money=new MoneyToString();
		System.out.println("value:"+money.moneytostr(4.2));
		System.out.println("value:"+money.moneytostr(401.00));
		System.out.println("end:");
	}

	
	static String RubOneUnit, RubTwoUnit, RubFiveUnit, RubSex,
                      KopOneUnit, KopTwoUnit, KopFiveUnit, KopSex;
        StringBuffer money2str = new StringBuffer();
 
	public MoneyToString(Double theMoney, String theISOstr) {
		FillSuffix(theISOstr);
		moneytostr(theMoney);
	}
	public MoneyToString() {
		FillSuffix(null);
		//moneytostr(theMoney);
	}
 
	public void FillSuffix(String theISOstr) {
		/*org.w3c.dom.Document xmlDoc = null;
 
		javax.xml.parsers.DocumentBuilderFactory DocFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
 
		try {
			javax.xml.parsers.DocumentBuilder xmlDocBuilder = DocFactory.newDocumentBuilder();
			xmlDoc = xmlDocBuilder.parse( new java.io.File("currlist.xml") );
		} catch (org.xml.sax.SAXException sxe) {
			Exception  x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();
		} catch (javax.xml.parsers.ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}
		org.w3c.dom.Element theISOElement = (org.w3c.dom.Element) (xmlDoc.getElementsByTagName(theISOstr)).item(0);
 
		RubOneUnit  = theISOElement.getAttribute("RubOneUnit");
		RubTwoUnit  = theISOElement.getAttribute("RubTwoUnit");
		RubFiveUnit = theISOElement.getAttribute("RubFiveUnit");
		KopOneUnit  = theISOElement.getAttribute("KopOneUnit");
		KopTwoUnit  = theISOElement.getAttribute("KopTwoUnit");
		KopFiveUnit = theISOElement.getAttribute("KopFiveUnit");
		RubSex      = theISOElement.getAttribute("RubSex");
		KopSex      = theISOElement.getAttribute("KopSex");
		*/
		RubOneUnit  = "������";
		RubTwoUnit  = "�����i";
		RubFiveUnit = "�������";
		KopOneUnit  = "���i���";
		KopTwoUnit  = "���i���";
		KopFiveUnit = "���i���";
		RubSex      = "F";
		KopSex      = "F";
	}
 
	public String moneytostr(Double theMoney) {
		money2str=new StringBuffer();
		int triadNum = 0;
		int theTriad;
 
		int intPart = theMoney.intValue();
		int fractPart = (int) Math.round((theMoney.doubleValue() - intPart)*100);
		if (intPart == 0) money2str.append("���� ");
		do {
			theTriad = intPart % 1000;
			money2str.insert(0, triad2Word(theTriad, triadNum, RubSex));
			if (triadNum == 0) {
				int range10 = (theTriad % 100) / 10;
				int range = theTriad % 10;
				if (range10 == 1) {
					money2str = money2str.append(RubFiveUnit);
				} else {
					switch (range) {
						case 1: money2str = money2str.append(RubOneUnit); break;
						case 2:
						case 3:
						case 4: money2str = money2str.append(RubTwoUnit); break;
						default: money2str = money2str.append(RubFiveUnit); break;
					}
				}
			}
			intPart = intPart/1000;
			triadNum++;
		}while(intPart > 0); 
		//while(theTriad !=0);
 
		/*money2str = money2str.append(" ").append(triad2Word(fractPart, 0, KopSex));
		if ((fractPart % 10) == 1) {
			money2str = money2str.append(KopOneUnit);
		} else {
			switch (fractPart % 10) {
				case 0:{
					if(fractPart==0){
						money2str.append("���� ");
					};
					money2str = money2str.append(KopFiveUnit); break;
				}
				case 1: money2str = money2str.append(KopOneUnit); break;
				case 2:
				case 3:
				case 4: money2str = money2str.append(KopTwoUnit); break;
				default:  money2str = money2str.append(KopFiveUnit); break;
			}
		}
		money2str.setCharAt(0, Character.toUpperCase(money2str.charAt (0)));
		 */
		MessageFormat format=new MessageFormat(" {0,number,00} ���.");
		money2str.append(format.format(new Object[]{new Double(fractPart)}));
		money2str.setCharAt(0, Character.toUpperCase(money2str.charAt(0)));
		return money2str.toString();
	}
 
	static private String triad2Word(int triad, int triadNum, String Sex) {
		StringBuffer triadWord = new StringBuffer(28); // ��������� ����������� ������ - 28 ���������� ?
 
		if (triad == 0) {
			return triadWord.toString();
		}
		
		int range = triad / 100;
		switch (range) {
			default: break;
			/*case 1:  triadWord = triadWord.append("��� ");       break;
			case 2:  triadWord = triadWord.append("������ ");    break;
			case 3:  triadWord = triadWord.append("������ ");    break;
			case 4:  triadWord = triadWord.append("��������� "); break;
			case 5:  triadWord = triadWord.append("������� ");   break;
			case 6:  triadWord = triadWord.append("�������� ");  break;
			case 7:  triadWord = triadWord.append("������� ");   break;
			case 8:  triadWord = triadWord.append("��������� "); break;
			case 9:  triadWord = triadWord.append("��������� "); break;*/
			case 1:  triadWord = triadWord.append("��� ");       break;
			case 2:  triadWord = triadWord.append("��i��i ");    break;
			case 3:  triadWord = triadWord.append("������ ");    break;
			case 4:  triadWord = triadWord.append("��������� "); break;
			case 5:  triadWord = triadWord.append("�'����� ");   break;
			case 6:  triadWord = triadWord.append("�i����� ");  break;
			case 7:  triadWord = triadWord.append("�i���� ");   break;
			case 8:  triadWord = triadWord.append("�i�i���� "); break;
			case 9:  triadWord = triadWord.append("���'����� "); break;
		}
		
		range = (triad % 100) / 10;
		switch (range) {
			default: break;
			case 2:  triadWord = triadWord.append("�������� ");    break;
			case 3:  triadWord = triadWord.append("�������� ");    break;
			case 4:  triadWord = triadWord.append("����� ");       break;
			case 5:  triadWord = triadWord.append("�'������� ");   break;
			case 6:  triadWord = triadWord.append("�i������� ");  break;
			case 7:  triadWord = triadWord.append("�i������ ");   break;
			case 8:  triadWord = triadWord.append("�i�i������ "); break;
			case 9:  triadWord = triadWord.append("���'������ ");   break;
		}
		
		int range10 = range;
		range = triad % 10;
		if (range10 == 1) {
			switch (range) {
				case 0: triadWord = triadWord.append("������ ");       break;
				case 1: triadWord = triadWord.append("���������� ");  break;
				case 2: triadWord = triadWord.append("���������� ");   break;
				case 3: triadWord = triadWord.append("���������� ");   break;
				case 4: triadWord = triadWord.append("������������ "); break;
				case 5: triadWord = triadWord.append("�'��������� ");   break;
				case 6: triadWord = triadWord.append("�i��������� ");  break;
				case 7: triadWord = triadWord.append("�i�������� ");   break;
				case 8: triadWord = triadWord.append("�i�i�������� "); break;
				case 9: triadWord = triadWord.append("���'��������� "); break;
			}
		} else {
			switch (range) {
				default: break;
			        case 1:	if (triadNum == 1)
						triadWord = triadWord.append("���� ");
					else
						if (Sex.equals("M")) triadWord = triadWord.append("���� ");
						if (Sex.equals("F")) triadWord = triadWord.append("���� ");
					break;
				case 2: if (triadNum == 1)
						triadWord = triadWord.append("��� ");
					else
						if (Sex.equals("M")) triadWord = triadWord.append("��� ");
						if (Sex.equals("F")) triadWord = triadWord.append("��i ");
					break;
				case 3: triadWord = triadWord.append("��� ");    break;
				case 4: triadWord = triadWord.append("������ "); break;
				case 5: triadWord = triadWord.append("�'��� ");   break;
				case 6: triadWord = triadWord.append("�i��� ");  break;
				case 7: triadWord = triadWord.append("�i� ");   break;
				case 8: triadWord = triadWord.append("�i�i� "); break;
				case 9: triadWord = triadWord.append("���'��� "); break;
			}
		}
	
	switch (triadNum) {
		default: break;
		case 1:	if (range10 == 1)
				triadWord = triadWord.append("����� ");
		        else {
				switch (range) {
					default: triadWord = triadWord.append("����� ");  break;
					case 1:  triadWord = triadWord.append("������ "); break;
					case 2:
					case 3:
					case 4:  triadWord = triadWord.append("�����i "); break;
				}
			}
			break;
		case 2: if (range10 == 1)
				triadWord = triadWord.append("�i�����i� ");
			else {
				switch (range) {
					default: triadWord = triadWord.append("�i�����i� "); break;
					case 1:  triadWord = triadWord.append("�i����� ");   break;
					case 2:
					case 3:
					case 4:  triadWord = triadWord.append("�i������ ");  break;
				}
			}
			break;
		case 3: if (range10 == 1)
				triadWord = triadWord.append("�i�i���i� ");
			else {
				switch (range) {
					default: triadWord = triadWord.append("�i�i���i� "); break;
					case 1:  triadWord = triadWord.append("�i�i��� ");   break;
					case 2:
					case 3:
					case 4:  triadWord = triadWord.append("�i�i���� ");  break;
				}
			}
			break;
		case 4: if (range10 == 1)
				triadWord = triadWord.append("����i��i� ");
			else {
				switch (range) {
					default: triadWord = triadWord.append("����i��i� "); break;
					case 1:  triadWord = triadWord.append("����i�� ");   break;
					case 2:
					case 3:
					case 4:  triadWord = triadWord.append("����i��� ");  break;
				}
			}
			break;
		}
	return triadWord.toString();
	}
}

/*
<?xml version="1.0" encoding="windows-1251" standalone="yes"?>
<CurrencyList>
 
    <RUR CurrID="810" CurrName="���������� �����"
         RubOneUnit="�����" RubTwoUnit="�����" RubFiveUnit="������" RubSex="M"
         KopOneUnit="�������" KopTwoUnit="�������" KopFiveUnit="������" KopSex="F"
    />
 
    <DEM CurrID="276" CurrName="�������� �����"
         RubOneUnit="�����" RubTwoUnit="�����" RubFiveUnit="�����" RubSex="F"
         KopOneUnit="�������" KopTwoUnit="��������" KopFiveUnit="���������" KopSex="M"
    />
 
    <USD CurrID="840" CurrName="������� ���"
         RubOneUnit="������" RubTwoUnit="�������" RubFiveUnit="��������" RubSex="M"
         KopOneUnit="����" KopTwoUnit="�����" KopFiveUnit="������" KopSex="M"
    />
 
</CurrencyList>


*/