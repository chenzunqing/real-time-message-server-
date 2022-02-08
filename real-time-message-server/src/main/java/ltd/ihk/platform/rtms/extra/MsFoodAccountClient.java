package ltd.ihk.platform.rtms.extra;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import ltd.ihk.platform.rtms.vo.LoginUser;

/**
 * @author jingyk
 */
public interface MsFoodAccountClient {
    
    /**
     * 認證
     *
     * @param token
     * @return
     */
    @RequestLine("GET /account/api/acc/find/user/{token}")
    Result<AccUserDto> authenticate(@Param("token") String token);
    
    /**
     * 认证
     *
     * @param token
     * @return
     */
    @RequestLine("GET /account/api/acc/find/admin?token={token}")
    Result<LoginUser> adminAuthenticate(@Param("token") String token);
    
    /**
     * 創建客戶端
     *
     * @param endpoint
     * @return
     */
    static MsFoodAccountClient newStandardClient(String endpoint) {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .target(MsFoodAccountClient.class, endpoint);
    }
    
    
}
