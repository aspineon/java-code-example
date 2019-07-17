import javax.comm.*;
//import com.sun.comm.Win32Driver;
import java.util.*;
public class com_port {
	public static void main(String args[]) throws Exception {
		// List portNames
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier)ports.nextElement();
			String s = getPortInfo(portId);
			System.out.println(s);
		}	

		// test open-close methods on each port
		ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier)ports.nextElement();
			CommPort port = null;
			try {
				System.out.print("open " + portId.getName());
				port = portId.open(portId.getName(), 2000);
				port.close();
				System.out.println("...closed");
			} catch (Exception ex) {
				ex.printStackTrace();
				if (port != null)
					try { port.close(); } catch (Exception e) { }
			}
	    }

	}

	private static String getPortInfo(CommPortIdentifier portid) {
		StringBuffer sb = new StringBuffer();
		sb.append(portid.getName());
		sb.append(", ");
		sb.append("portType: ");
		if (portid.getPortType() == CommPortIdentifier.PORT_SERIAL)
			sb.append("serial");
		else if (portid.getPortType() == CommPortIdentifier.PORT_PARALLEL)
			sb.append("parallel");
		else
			sb.append("unknown");
		return sb.toString();
	}
}
