package ltd.ihk.platform.rtms.adapter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author jingyk
 */
@ConditionalOnProperty(value = "authenticator", havingValue = "anonymous", matchIfMissing = true)
@Component
public class AnonymousAuthenticator implements Authenticator {
    @Override
    public AuthenticatedUser authenticate(String token) {
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setId(UUID.randomUUID().toString());
        return authenticatedUser;
    }
    
    @Override
    public AuthenticatedUser adminAuthenticate(String token) {
        return null;
    }
}
