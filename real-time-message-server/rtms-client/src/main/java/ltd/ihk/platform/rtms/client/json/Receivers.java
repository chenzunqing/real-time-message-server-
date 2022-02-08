package ltd.ihk.platform.rtms.client.json;

import lombok.Data;

import java.util.List;

/**
 * @author jingyk
 */
@Data
public class Receivers {
    private List<User> users;

    @Data
    public static class User {
        private String id;
        private Object properties;
    }
}
