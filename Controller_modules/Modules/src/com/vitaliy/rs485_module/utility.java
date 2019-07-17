package com.vitaliy.rs485_module;

import com.siemens.icm.io.file.FileConnection;
import java.io.OutputStream;
import javax.microedition.io.Connector;
/**
 * �����, ������� �������� ����������� ������ ��� �������� ������
 * @author root
 *
 */
public class utility {
	/**
	 * �������� �������� �� 0..255 � 16-��� ������� 0xXX
	 * @param hex_string
	 */
	public synchronized static boolean isHEX_byte(String hex_string) {
		boolean result=false;
		int value=0;
                try{
                    value=(Integer.parseInt(hex_string, 16));
                    if((value>=0)&&(value<=255)){
                        result=true;
                    }
                    else {
                        result=false;
                    }
                }
                catch(Exception e){
                    result=false;
                }
		return result;
	}
        /**
         * �������� �������� �� 0..65535 � 16-��� ������� 0xXXXX
         */
        public synchronized static boolean isHEX_word(String hex_string){
		boolean result=false;
		int value=0;
                try{
                    value=(Integer.parseInt(hex_string, 16));
                    if((value>=0)&&(value<=65535)){
                        result=true;
                    }
                    else {
                        result=false;
                    }
                }
                catch(Exception e){
                    result=false;
                }
		return result;
        }
        /**
         * ������ ������� �������� �������
         */
        public synchronized static int pow(int base,int grade){
            int result=1;
            for(int i=0;i<grade;i++){
                result=result*base;
            }
            return result;
        }
        // ������ ������ � ���� �� Flash
        public synchronized static int println(String s){
            int result=0;
            try {
                 FileConnection fconn = (FileConnection)Connector.open(s,Connector.READ_WRITE);
                 if (!fconn.exists()){
                    fconn.create(); // �������� ������ ����� ,���� ������� �� ������
                 }
                fconn.setReadable(true);
                fconn.setWritable(true);
                OutputStream os = fconn.openOutputStream(fconn.totalSize());
                os.write(s.getBytes());
                os.flush();
                if (os!=null){
                    os.close();
                }
                if (fconn!=null){
                   fconn.close();
                }
                result=s.length();
            }
                catch (IOException ioe) {
                result=-1;
            }
            return result;
        }
}
