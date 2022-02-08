package ltd.ihk.platform.rtms.json;

import lombok.Data;

/**
 * @author jingyk
 */
@Data
public class P2pMessage {
    private String targetId;
    private Object message;
}
