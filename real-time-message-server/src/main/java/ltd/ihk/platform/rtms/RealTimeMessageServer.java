package ltd.ihk.platform.rtms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author jingyk
 */
@EnableAsync
@SpringBootApplication
public class RealTimeMessageServer {

    public static void main(String[] args) {
        SpringApplication.run(RealTimeMessageServer.class, args);
    }
}
