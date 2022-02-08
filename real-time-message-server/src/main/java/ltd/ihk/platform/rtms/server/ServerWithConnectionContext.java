package ltd.ihk.platform.rtms.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import ltd.ihk.platform.rtms.adapter.AuthenticatedUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jingyk
 */
@Slf4j
@Component
public class ServerWithConnectionContext extends SocketIOServer implements ConnectListener, DisconnectListener {

    private final ConnectionContext connectionContext;
    public static final ConcurrentHashMap<String, AuthenticatedUser> AUTHENTICATED_USER_MAP = new ConcurrentHashMap<>();

    public ServerWithConnectionContext(Configuration configuration, ConnectionContext connectionContext) {
        super(configuration);
        this.addConnectListener(this);
        this.addDisconnectListener(this);
        this.connectionContext = connectionContext;
    }

    @Override
    public void onDisconnect(SocketIOClient socketIOClient) {
        connectionContext.removeConnection(socketIOClient.getSessionId());
        AUTHENTICATED_USER_MAP.remove(tokenFromHandshakeData(socketIOClient.getHandshakeData()));
    }

    @SuppressWarnings("all")
    @Override
    public void onConnect(SocketIOClient socketIOClient) {
        String token = tokenFromHandshakeData(socketIOClient.getHandshakeData());
        if (token == null) {
            log.info("anonymous {} connected", socketIOClient.getSessionId());
            return;
        }
        AuthenticatedUser authenticatedUser = AUTHENTICATED_USER_MAP.get(token);
        if (authenticatedUser == null) {
            log.error("token: {} not found", token);
            return;
        }
        synchronized (this) {
            connectionContext.sessionsByUserId(authenticatedUser.getId()).forEach(sessionId -> {
                SocketIOClient client = ServerWithConnectionContext.this.getNamespace("/rtms/entrypoint").getClient(sessionId);
                if (client != null) {
                    client.disconnect();
                }
            });
            connectionContext.newConnection(socketIOClient.getSessionId(), authenticatedUser);
        }
    }

    private String tokenFromHandshakeData(HandshakeData handshakeData) {
        return handshakeData.getSingleUrlParam("token");
    }
}
