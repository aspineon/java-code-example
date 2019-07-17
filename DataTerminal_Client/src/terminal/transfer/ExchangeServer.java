package terminal.transfer;

import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import terminal.transport.Transport;

/** ����� ������ ��� ��������������� ������ � �������� */
public class ExchangeServer {

	/** Logger DEBUG */
	private static void debug(String information){
		System.out.print("ExchangeServer ");
		System.out.print(" DEBUG: ");
	    System.out.println(information);
	}
	/** Logger ERROR */
	private static void error(String information){
		System.out.print("ExchangeServer ");
		System.out.print(" ERROR: ");
	    System.out.println(information);
	}
	
	/** ������ URL �� ������ */
	// TODO ��������� ����������� ����� ��� ������� 
	private String field_pathToServer="http://localhost:8080/DataTerminal_Server/TerminalServer";
	
	/** ��� ��������������� ������ � �������� 
	 * ������� - Transport, ����� - Transport
	 * @param path_to_server - ������ URL � �������, �� �������� ����� �������� ������ �� �������  
	 */
	public ExchangeServer(String path_to_server){
		if((path_to_server!=null)&&(!path_to_server.equals(""))){
			this.field_pathToServer=path_to_server;
		}
	}

	/** ��� ��������������� ������ � �������� 
	 * ������� - Transport, ����� - Transport
	 */
	public ExchangeServer(){
	}
	
	/** ���������� � �������� � ��������� �� ���� ������ �� ������������ ������
	 * @param request - ��������, ������� ����� ���������� 
	 */
	public Transport Connect(Transport request){
		return Connect(field_pathToServer,request);
	}

	/** ���������� � �������� � ��������� �� ���� ������ �� ������������ ������
	 * @param path_to_server ������ ����� �������� � ����
	 * @param request �����, ������� ����� ������������ ��� ������� �� ������ 
	 */
	public Transport Connect(String path_to_server, Transport request){
		debug("Connect:begin");
		Transport response=null;
		try{
			URL url=new URL(path_to_server);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//debug("Send:connection");
			OutputStream outputstream=connection.getOutputStream();
			//debug("Send:create object output");
			ObjectOutputStream object_output=new ObjectOutputStream(outputstream);
			//debug("Send:write object");
			object_output.writeObject(request);
			object_output.flush();
			//debug("Send:object_output close");
			object_output.close();
			//debug("Send:outputstream close");
			outputstream.close();
			//debug("Send:connection to URL");
			connection.connect();
			//debug("Send:get input stream");
			InputStream inputstream=connection.getInputStream();
			//debug("Send:get object input stream");
			ObjectInput object_input=new ObjectInputStream(inputstream);
			//debug("Send:read object");
			response=(Transport)object_input.readObject();
			inputstream.close();
			debug("Connect:Done");
		}catch(Exception ex){
			error("Send:Server Exchange exception:"+ex.getMessage());
		}
		debug("Connect:end");
		return response;
	}

}
