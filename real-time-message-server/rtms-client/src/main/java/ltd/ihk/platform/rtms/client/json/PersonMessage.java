package ltd.ihk.platform.rtms.client.json;

import lombok.Data;

/**
 * @author jingyk
 */
@Data
public class PersonMessage {
    /**
     * 目標用戶ID
     */
    private String targetId;
    /**
     * 消息
     */
    private Object message;
}
