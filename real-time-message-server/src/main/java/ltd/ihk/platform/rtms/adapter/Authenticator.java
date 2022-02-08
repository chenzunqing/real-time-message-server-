package ltd.ihk.platform.rtms.adapter;

/**
 * @author jingyk
 */
public interface Authenticator {
    /**
     * 認證
     *
     * @param token 憑證
     * @return 已認證的用戶
     * @throws AuthorizationRequiredException 認證失敗
     */
    AuthenticatedUser authenticate(String token) throws AuthorizationRequiredException;
    
    /**
     * 认证
     *
     * @param token 认证
     * @return 已登录用户
     * @throws AuthorizationRequiredException 认证失败
     */
    AuthenticatedUser adminAuthenticate(String token) throws AuthorizationRequiredException;
}
