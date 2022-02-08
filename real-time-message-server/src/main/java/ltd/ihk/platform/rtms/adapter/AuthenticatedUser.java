package ltd.ihk.platform.rtms.adapter;

import lombok.Data;

import java.util.Map;

/**
 * @author jingyk
 */
@Data
public class AuthenticatedUser {
    private String id;
    private Map<String, Object> properties;
}
