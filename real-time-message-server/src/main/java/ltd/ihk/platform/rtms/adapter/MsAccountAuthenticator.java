package ltd.ihk.platform.rtms.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ltd.ihk.platform.rtms.extra.AccUserDto;
import ltd.ihk.platform.rtms.extra.MsFoodAccountClient;
import ltd.ihk.platform.rtms.extra.Result;
import ltd.ihk.platform.rtms.vo.LoginUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author jingyk
 */
@ConditionalOnProperty(value = "authenticator", havingValue = "ms-account")
@Component
@RequiredArgsConstructor
@Slf4j
public class MsAccountAuthenticator implements Authenticator {

    private final MsFoodAccountClient foodAccountClient;

    private final ObjectMapper objectMapper;

    @Override
    @SuppressWarnings("unchecked")
    public AuthenticatedUser authenticate(String token) throws AuthorizationRequiredException {
        log.info("app登录token={}",token);
        if (!StringUtils.hasText(token)) {
            throw new AuthorizationRequiredException("401");
        }
        Result<AccUserDto> accUserDtoResult = foodAccountClient.authenticate(token);
        if (!accUserDtoResult.isSuccess()) {
            throw new AuthorizationRequiredException(accUserDtoResult.getMessage());
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setId(String.valueOf(accUserDtoResult.getResult().getId()));
        try {
            authenticatedUser.setProperties(objectMapper.readValue(objectMapper.writeValueAsBytes(accUserDtoResult.getResult()), Map.class));
        } catch (IOException e) {
            throw new AuthorizationRequiredException(e.getMessage());
        }
        return authenticatedUser;
    }
    
    @Override
    public AuthenticatedUser adminAuthenticate(String token) throws AuthorizationRequiredException {
        log.info("admin登录token={}",token);
        if (!StringUtils.hasText(token)) {
            throw new AuthorizationRequiredException("401");
        }
        Result<LoginUser> LoginUserResult = foodAccountClient.adminAuthenticate(token);
        if (!LoginUserResult.isSuccess()) {
            throw new AuthorizationRequiredException(LoginUserResult.getMessage());
        }
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setId(String.valueOf(LoginUserResult.getResult().getId()));
        try {
            authenticatedUser.setProperties(objectMapper.readValue(objectMapper.writeValueAsBytes(LoginUserResult.getResult()), Map.class));
        } catch (IOException e) {
            throw new AuthorizationRequiredException(e.getMessage());
        }
        return authenticatedUser;
    }
    
    
    public static void main(String[] args) {
        MsFoodAccountClient foodAccountClient = MsFoodAccountClient.newStandardClient("http://192.168.1.176:9004");

        //Result<AccUserDto> accUserDtoResult = foodAccountClient.authenticate("3f1cfeb2-971e-4ddd-924d-b814943df0fa");
        Result<LoginUser> userResult = foodAccountClient.adminAuthenticate("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDEyODg5NDIsInVzZXJuYW1lIjoiODYxNzg1MDEzNTc1MiJ9.S4VtLqvLjD8zAuTgvln6e7_is-O6vHlE4UeK8mXHl7c");
        System.out.println(userResult);
        
    }
}
