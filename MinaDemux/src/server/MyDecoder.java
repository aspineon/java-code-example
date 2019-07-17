package server;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.ByteBuffer;
import messages.CountMessage;
import messages.HelloMessage;
import java.nio.charset.Charset;

/** �������, ������� ������� �� �������� ������ */
public class MyDecoder implements MessageDecoder {
	
	/** �������, ������� �� ������ ��� ������ */
	public MessageDecoderResult decodable(IoSession session, ByteBuffer in) {
        if (in.remaining() < 4)
            return MessageDecoderResult.NEED_DATA;
        int type = in.getInt();
        if (type != CountMessage.TYPE && type != HelloMessage.TYPE)
            return MessageDecoderResult.NOT_OK;
        return MessageDecoderResult.OK;
    }
    
	/** ������������� - ������� ������� ���� ������ ������ ���������� - ������� OK
	 * @param session
	 * @param in - �����, � ������� ��������� ���������� ������ ( ����� �� �������)
	 * @param out - �����, � ������� ����� �������� �������, ������� ����� ������ ���������� 
	 *  
	 */
	public MessageDecoderResult decode(IoSession session, 
									   ByteBuffer in, 
									   ProtocolDecoderOutput out) throws Exception {
        if (in.remaining() < 4)
            return MessageDecoderResult.NEED_DATA;
        int type = in.getInt();
        switch(type) {
            case CountMessage.TYPE : {
                if (in.remaining() < 8)
                    return MessageDecoderResult.NEED_DATA;
                CountMessage message = new CountMessage();
                message.a = in.getInt();
                message.b = in.getInt();
                // �������� ������ � �����
                out.write(message);
                break;
            }
            case HelloMessage.TYPE : {
                // ����� ������ "hello" � ������� UTF-8
                if (in.remaining() < 5)
                    return MessageDecoderResult.NEED_DATA;
                HelloMessage message = new HelloMessage();
                message.hello = in.getString(Charset.forName("UTF-8").newDecoder());
                // �������� ������ � �����
                out.write(message);
                break;
            }
            default: return MessageDecoderResult.NOT_OK;
        }
        return MessageDecoderResult.OK;
    }
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        // ���� �� ���� ����� ��� ����
    	session.write(new HelloMessage());
    	System.out.println("send object to reader ");
    	session.close();
    }
}