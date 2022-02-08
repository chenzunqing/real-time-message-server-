package ltd.ihk.platform.rtms.server;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import ltd.ihk.platform.rtms.json.RoomMessage;
import ltd.ihk.platform.rtms.json.OpsMessage;
import ltd.ihk.platform.rtms.json.P2pMessage;
import ltd.ihk.platform.rtms.listener.AuthenticationListener;
import ltd.ihk.platform.rtms.listener.RoomMessageListener;
import ltd.ihk.platform.rtms.listener.OpsMessageListener;
import ltd.ihk.platform.rtms.listener.P2pMessageListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jingyk
 */
@Configuration
public class ServerConfiguration {

    @Bean
    @SuppressWarnings("all")
    @ConfigurationProperties(value = "rtms")
    public com.corundumstudio.socketio.Configuration socketIOConfiguration(AuthenticationListener authenticationListener) {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setHostname("localhost");
        configuration.setPort(9092);
        configuration.setAuthorizationListener(authenticationListener);
        return configuration;
    }

    @Bean
    public SocketIONamespace defaultNamespace(
            SocketIOServer server,
            P2pMessageListener p2pMessageListener,
            RoomMessageListener roomMessageListener,
            OpsMessageListener opsMessageListener) {
        SocketIONamespace defaultNamespace = server.addNamespace("/rtms/entrypoint");
        defaultNamespace.addEventListener("p2p", P2pMessage.class, p2pMessageListener);
        defaultNamespace.addEventListener("room", RoomMessage.class, roomMessageListener);
        defaultNamespace.addEventListener("ops", OpsMessage.class, opsMessageListener);
        return defaultNamespace;
    }
}
