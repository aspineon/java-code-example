import java.io.FileInputStream;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;


public class TestReader {
	
	public static void main(String[] args){
		String pathToDbf="C:\\vidd2.dbf";
		readHeaderDbfFile(pathToDbf);
		readDateDbfFile(pathToDbf);
		//printAviableCharset();
	}
	
	/** ��������� � ������� �� ������� ���������� DBF ����� */
	private static void readHeaderDbfFile(String pathToFile){
		try{
			DBFReader reader = new DBFReader( new FileInputStream(pathToFile)); 
		    // get the field count if you want for some reasons like the following
		    int numberOfFields = reader.getFieldCount();
		    // use this count to fetch all field information
		    // if required
		    for( int i=0; i<numberOfFields; i++) {
		    	DBFField field = reader.getField( i);
		        // do something with it if you want
		        // refer the JavaDoc API reference for more details
		    	System.out.print("Name:"+field.getName());
		    	System.out.println("   Type:"+field.getDataType());
		    }
		}catch(Exception ex){
			System.out.println("readFromDbfFile: "+ex.getMessage());
		}
	}

	/** �������� � ������� �� ������� ���������� DBF ����� */
	private static void readDateDbfFile(String pathToFile){
		try{
			DBFReader reader = new DBFReader( new FileInputStream(pathToFile));
			Object[] record;
			while((record=reader.nextRecord())!=null){
				for(int counter=0;counter<record.length;counter++){
					if(record[counter] instanceof String){
						//System.out.print(record[counter]+" "+convertDosString((String)record[counter]));
						System.out.print(convertDosString((String)record[counter]));
					}else{
						System.out.print(record[counter]+"   ");
					}
				}
				System.out.println();
			}
		}catch(Exception ex){
			System.out.println("readFromDbfFile: "+ex.getMessage());
		}
	}
	
	/** �������������� ����������� ������ �� DBF � "����������" ������������� ������� ���� */
	private static String convertDosString(String value){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<value.length();counter++){
			returnValue.append(getCharFromInteger(Integer.valueOf(value.charAt(counter)),value.charAt(counter)));
		}
		return returnValue.toString();
	}
	
	/** �������������� ������� �������� � "���������� ����������� "*/
	private static char getCharFromInteger(int value,char defaultValue){
		if(value==(128))return '�';
		if(value==(129))return '�';
		if(value==(130))return '�';
		if(value==(131))return '�';
		if(value==(132))return '�';
		if(value==(133))return '�';
		if(value==(134))return '�';
		if(value==(135))return '�';
		if(value==(136))return '�';
		if(value==(137))return '�';
		if(value==(138))return '�';
		if(value==(139))return '�';
		if(value==(140))return '�';
		if(value==(141))return '�';
		if(value==(142))return '�';
		if(value==(143))return '�';
		if(value==(144))return '�';
		if(value==(145))return '�';
		if(value==(146))return '�';
		if(value==(147))return '�';
		if(value==(148))return '�';
		if(value==(149))return '�';
		if(value==(150))return '�';
		if(value==(151))return '�';
		if(value==(152))return '�';
		if(value==(153))return '�';
		if(value==(154))return '�';
		if(value==(155))return '�';
		if(value==(156))return '�';
		if(value==(157))return '�';
		if(value==(158))return '�';
		if(value==(159))return '�';
		if(value==(160))return '�';
		if(value==(161))return '�';
		if(value==(162))return '�';
		if(value==(163))return '�';
		if(value==(164))return '�';
		if(value==(165))return '�';
		if(value==(166))return '�';
		if(value==(167))return '�';
		if(value==(168))return '�';
		if(value==(169))return '�';
		if(value==(170))return '�';
		if(value==(171))return '�';
		if(value==(172))return '�';
		if(value==(173))return '�';
		if(value==(174))return '�';
		if(value==(175))return '�';
		if(value==(224))return '�';
		if(value==(225))return '�';
		if(value==(226))return '�';
		if(value==(227))return '�';
		if(value==(228))return '�';
		if(value==(229))return '�';
		if(value==(230))return '�';
		if(value==(231))return '�';
		if(value==(232))return '�';
		if(value==(233))return '�';
		if(value==(234))return '�';
		if(value==(235))return '�';
		if(value==(236))return '�';
		if(value==(237))return '�';
		if(value==(238))return '�';
		if(value==(239))return '�';
		//if(value==())return '�';
		if(value==(240))return '�';
		if(value==(242))return '�';
		if(value==(244))return '�';
		//if(value==())return '�';
		//if(value==())return '�';
		//if(value==())return '�';
		if(value==(241))return '�';
		if(value==(252))return '�';
		if(value==(243))return '�';
		//if(value==())return '�';
		//if(value==())return '�';
		//if(value==())return '�';
		if(value==(245))return '�';
		return defaultValue;
	}
}

/*
�	;	128	;
�	;	129	;
�	;	130	;
�	;	131	;
�	;	132	;
�	;	133	;
�	;	134	;
�	;	135	;
�	;	136	;
�	;	137	;
�	;	138	;
�	;	139	;
�	;	140	;
�	;	141	;
�	;	142	;
�	;	143	;
�	;	144	;
�	;	145	;
�	;	146	;
�	;	147	;
�	;	148	;
�	;	149	;
�	;	150	;
�	;	151	;
�	;	152	;
�	;	153	;
�	;	154	;
�	;	155	;
�	;	156	;
�	;	157	;
�	;	158	;
�	;	159	;
�	;	160	;
�	;	161	;
�	;	162	;
�	;	163	;
�	;	164	;
�	;	165	;
�	;	166	;
�	;	167	;
�	;	168	;
�	;	169	;
�	;	170	;
�	;	171	;
�	;	172	;
�	;	173	;
�	;	174	;
�	;	175	;
�	;	224	;
�	;	225	;
�	;	226	;
�	;	227	;
�	;	228	;
�	;	229	;
�	;	230	;
�	;	231	;
�	;	232	;
�	;	233	;
�	;	234	;
�	;	235	;
�	;	236	;
�	;	237	;
�	;	238	;
�	;	239	;
�	;		;
�	;	240	;
�	;	242	;
�	;	244	;
�	;		;
�	;		;
�	;		;
�	;	241	;
�	;	252	;
�	;	243	;
�	;		;
�	;		;
�	;		;
�	;	245	;

 
 */ 
