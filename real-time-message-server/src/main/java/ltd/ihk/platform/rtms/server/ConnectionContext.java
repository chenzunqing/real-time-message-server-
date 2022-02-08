package ltd.ihk.platform.rtms.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ltd.ihk.platform.rtms.adapter.AuthenticatedUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author jingyk
 */
@Getter
@Slf4j
@Component
public class ConnectionContext {
    private final ConcurrentHashMap<String, UUID> userSocketMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, String> socketUserMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, AuthenticatedUser> socketUserObjMap = new ConcurrentHashMap<>();

    public void newConnection(UUID sessionId, AuthenticatedUser authenticatedUser) {
        userSocketMap.put(authenticatedUser.getId(), sessionId);
        socketUserMap.put(sessionId, authenticatedUser.getId());
        socketUserObjMap.put(sessionId, authenticatedUser);
        log.info("{} is connected.", authenticatedUser.getId());
        log.debug("userSocketMap: {}", userSocketMap);
        log.debug("socketUserMap: {}", socketUserMap);
    }

    public void removeConnection(UUID sessionId) {
        log.info("remove session {}", sessionId);
        String userId = socketUserMap.get(sessionId);
        if (userId == null) {
            log.warn("not fount sessionId: {}", sessionId);
            return;
        }
        if (sessionId.equals(sessionId(userId))) {
            userSocketMap.remove(userId);
        }
        socketUserMap.remove(sessionId);
        socketUserObjMap.remove(sessionId);
        log.info("{} is disconnected", userId);
        log.debug("userSocketMap: {}", userSocketMap);
        log.debug("socketUserMap: {}", socketUserMap);
    }

    public UUID sessionId(String targetId) {
        return userSocketMap.get(targetId);
    }

    public AuthenticatedUser getUserBySessionId(UUID sessionId) {
        return socketUserObjMap.get(sessionId);
    }

    public List<UUID> sessionsByUserId(String id) {
        return socketUserMap.entrySet().stream().filter(kv -> kv.getValue().equals(id)).map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
