package ltd.ihk.platform.rtms.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.extern.slf4j.Slf4j;
import ltd.ihk.platform.rtms.json.Ack;
import ltd.ihk.platform.rtms.json.OpsMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author jingyk
 */
@Slf4j
@Component
public class OpsMessageListener implements DataListener<OpsMessage> {

    @Override
    public void onData(SocketIOClient socketIOClient, OpsMessage opsMessage, AckRequest ackRequest) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> message = (Map<String, Object>) opsMessage.getMessage();
            Assert.notNull(opsMessage.getAction(), "action是必須的");
            Assert.notNull(message, "message是必須的");

            if (opsMessage.getAction() == OpsMessage.Action.JOIN_ROOM) {
                String room = (String) message.get("room");
                Assert.hasText(room, "room是必須的");
                socketIOClient.joinRoom(room);
                log.debug("{} joined room {}", socketIOClient.getSessionId(), room);
                if (ackRequest.isAckRequested()) {
                    ackRequest.sendAckData(new Ack(true));
                }
            } else if (opsMessage.getAction() == OpsMessage.Action.LEAVE_ROOM) {
                String room = (String) message.get("room");
                Assert.hasText(room, "room是必須的");
                socketIOClient.leaveRoom(room);
                if (ackRequest.isAckRequested()) {
                    ackRequest.sendAckData(new Ack(true));
                }
                log.debug("{} leave room {}", socketIOClient.getSessionId(), room);
            } else {
                throw new UnsupportedOperationException("不支持的操作");
            }
        } catch (Exception e) {
            socketIOClient.sendEvent("system", e);
        }
    }

}
