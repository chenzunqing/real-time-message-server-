package ltd.ihk.platform.rtms.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.RequiredArgsConstructor;
import ltd.ihk.platform.rtms.json.Ack;
import ltd.ihk.platform.rtms.json.P2pMessage;
import ltd.ihk.platform.rtms.server.ConnectionContext;
import org.springframework.stereotype.Component;

/**
 * @author jingyk
 */
@Component
@RequiredArgsConstructor
public class P2pMessageListener implements DataListener<P2pMessage> {

    private final ConnectionContext connectionContext;

    @Override
    public void onData(SocketIOClient socketIOClient, P2pMessage p2pMessage, AckRequest ackRequest) throws Exception {
        socketIOClient.getNamespace().getClient(connectionContext.sessionId(p2pMessage.getTargetId())).sendEvent("message", p2pMessage.getMessage());
        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(new Ack(true));
        }
    }

}
