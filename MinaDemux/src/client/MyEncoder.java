package client;

import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.ByteBuffer;
import java.util.Set;
import java.util.HashSet;
import java.nio.charset.Charset;
import messages.CountMessage;
import messages.HelloMessage;

public class MyEncoder implements MessageEncoder {
    private static final Set<Class<?>> TYPES = new HashSet<Class<?>>();
    static {
        TYPES.add(CountMessage.class);
        TYPES.add(HelloMessage.class);
    }
    public Set<Class<?>> getMessageTypes() {
        return TYPES;
    }
    
    /**
     * @param session - ������
     * @param message - ������, ������� ����� ������������� � ����� 
     * @param out - �����, � ������� ����� �������� ������
     */
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        // �������� �������� : ��� �� java.nio.ByteBuffer, ��� org.apache.mina.common.ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        if (message instanceof CountMessage) {
            CountMessage countMessage = (CountMessage)message;
            // ������� ��� 1 �������� ���������
            buffer.putInt(CountMessage.TYPE);
            buffer.putInt(countMessage.a);
            buffer.putInt(countMessage.b);
        } else if (message instanceof HelloMessage) {
            // ������� ��� 2 ��������� �����������
            buffer.putInt(HelloMessage.TYPE);
            buffer.putString(((HelloMessage)message).hello, Charset.forName("UTF-8").newEncoder());
        }
        buffer.flip();
        out.write(buffer);
    }
}