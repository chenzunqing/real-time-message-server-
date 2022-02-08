package ltd.ihk.platform.rtms.listener;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ltd.ihk.platform.rtms.adapter.AuthenticatedUser;
import ltd.ihk.platform.rtms.adapter.Authenticator;
import ltd.ihk.platform.rtms.adapter.AuthorizationRequiredException;
import ltd.ihk.platform.rtms.server.ServerWithConnectionContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author jingyk
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationListener implements AuthorizationListener {

    private final Authenticator authenticator;

    @Override
    public boolean isAuthorized(HandshakeData handshakeData) {
        String token = tokenFromHandshakeData(handshakeData);
        String platformType = handshakeData.getSingleUrlParam("platform_type");
        AuthenticatedUser authenticatedUser = null;
        try {
            // 后台token
            if(StringUtils.hasText(platformType) && platformType.equals("admin")){
                 authenticatedUser = authenticator.adminAuthenticate(token);
            }else {
                authenticatedUser = authenticator.authenticate(token);
            }
            
            if (null != token) {
                ServerWithConnectionContext.AUTHENTICATED_USER_MAP.put(token, authenticatedUser);
            }
        } catch (AuthorizationRequiredException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String tokenFromHandshakeData(HandshakeData handshakeData) {
        return handshakeData.getSingleUrlParam("token");
    }

}
