package ltd.ihk.platform.rtms.extra;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jingyk
 */
@Configuration
public class ExtraConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "extra")
    public ExtraConfig extraConfig() {
        return new ExtraConfig();
    }

    @Bean
    public MsFoodAccountClient foodAccountClient(ExtraConfig extraConfig) {
        return MsFoodAccountClient.newStandardClient(extraConfig.getAccountEndpoint());
    }
}
