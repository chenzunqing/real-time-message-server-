package ltd.ihk.platform.rtms.adapter;

import lombok.Data;

/**
 * @author jingyk
 */
@Data
public class AuthorizationRequiredException extends Exception {

    private String message;

    public AuthorizationRequiredException() {
        this.message = "AuthorizationRequiredException";
    }

    public AuthorizationRequiredException(String message) {
        this.message = message;
    }
}
