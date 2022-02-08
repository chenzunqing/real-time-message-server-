package ltd.ihk.platform.rtms.restapi;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ltd.ihk.platform.rtms.adapter.AuthenticatedUser;
import ltd.ihk.platform.rtms.json.RoomMessage;
import ltd.ihk.platform.rtms.server.ConnectionContext;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jingyk
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageApi {
    
    private final SocketIOServer socketIOServer;
    
    private final ConnectionContext connectionContext;
    
    @PostMapping("/messages")
    public Receivers sendMessage(@RequestBody RoomMessage roomMessage) {
        Assert.hasText(roomMessage.getRoomId(), "roomId is required");
        socketIOServer.getRoomOperations(roomMessage.getRoomId()).sendEvent("message", roomMessage.getMessage());
        Receivers receivers = new Receivers();
        receivers.setUsers(socketIOServer.getRoomOperations(roomMessage.getRoomId()).getClients()
                .stream()
                .map(client -> Optional.ofNullable(connectionContext.getUserBySessionId(client.getSessionId())).orElse(new AuthenticatedUser()))
                .collect(Collectors.toList()));
        if (receivers.getUsers().isEmpty()) {
            throw new CommonHandler.RtmsException("60002", "room is empty");
        }
        return receivers;
    }
    
    @PostMapping("/person-messages")
    public void sendMessage(@RequestBody PersonMessage personMessage) {
        Assert.hasText(personMessage.getTargetId(), "targetId is required");
        UUID sessionId = connectionContext.sessionId(personMessage.getTargetId());
        if (sessionId == null) {
            throw new CommonHandler.RtmsException("60001", "targetId not found");
        }
        log.info("管理员信息={}，sessionId={}",personMessage,sessionId);
        socketIOServer.getNamespace("/rtms/entrypoint").getClient(sessionId).sendEvent("message", personMessage.getMessage());
        log.info("管理员信息1={}，sessionId={}",personMessage,sessionId);
    }
   
    
    
    @Data
    public static class Receivers {
        private List<AuthenticatedUser> users;
    }
    
    @Data
    public static class RoomMessage {
        private String roomId;
        private Object message;
    }
    
    @Data
    public static class PersonMessage {
        private String targetId;
        private Object message;
    }
}
