package ltd.ihk.platform.rtms.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.RequiredArgsConstructor;
import ltd.ihk.platform.rtms.json.Ack;
import ltd.ihk.platform.rtms.json.RoomMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author jingyk
 */
@Component
@RequiredArgsConstructor
public class RoomMessageListener implements DataListener<RoomMessage> {

    private final SocketIOServer socketIOServer;

    @Override
    public void onData(SocketIOClient socketIOClient, RoomMessage roomMessage, AckRequest ackRequest) {
        if (!StringUtils.hasText(roomMessage.getRoomId())) {
            socketIOClient.sendEvent("system", new IllegalArgumentException("roomId是必須的"));
            return;
        }
        socketIOServer.getRoomOperations(roomMessage.getRoomId()).sendEvent("message", roomMessage.getMessage());
        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(new Ack(true));
        }
    }

}
