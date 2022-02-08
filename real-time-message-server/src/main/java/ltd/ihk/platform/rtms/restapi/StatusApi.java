package ltd.ihk.platform.rtms.restapi;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ltd.ihk.platform.rtms.adapter.AuthenticatedUser;
import ltd.ihk.platform.rtms.server.ConnectionContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jingyk
 */
@RestController
@RequiredArgsConstructor
public class StatusApi {

    private final SocketIOServer socketIOServer;

    private final ConnectionContext connectionContext;

    @GetMapping("/status")
    public Status status() {
        Status status = new Status();
        status.setConnections(socketIOServer.getAllNamespaces()
                .stream()
                .filter(ns -> StringUtils.hasText(ns.getName()))
                .flatMap(c -> c.getAllClients().stream())
                .map(client -> {
                    Status.Connection connection = new Status.Connection();
                    AuthenticatedUser user = Optional.ofNullable(connectionContext.getUserBySessionId(client.getSessionId())).orElse(null);
                    connection.setUser(user);
                    connection.setConnectionId(client.getRemoteAddress().toString());
                    connection.setRooms(new LinkedList<>(client.getAllRooms()));
                    return connection;
                })
                .collect(Collectors.toList()));
        return status;
    }


    @Data
    public static class Status {
        private List<Connection> connections;

        @Data
        public static class Connection {
            private String connectionId;
            private AuthenticatedUser user;
            private List<String> rooms;
        }
    }
}
