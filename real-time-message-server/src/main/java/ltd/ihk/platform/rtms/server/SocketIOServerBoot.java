package ltd.ihk.platform.rtms.server;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jingyk
 */
@Slf4j
@Component
@SuppressWarnings("all")
@RequiredArgsConstructor
public class SocketIOServerBoot {

    private final SocketIOServer socketIOServer;

    @PostConstruct
    public void boot() {
        @SuppressWarnings("all")
        Thread socketIOMainThread = new Thread(() -> socketIOServer.start());
        socketIOMainThread.setName("socket.io-main");
        socketIOMainThread.setDaemon(false);
        socketIOMainThread.start();
    }

}
