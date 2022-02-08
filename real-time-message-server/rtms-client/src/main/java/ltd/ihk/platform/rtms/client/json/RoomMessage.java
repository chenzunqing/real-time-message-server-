package ltd.ihk.platform.rtms.client.json;

import lombok.Data;

/**
 * @author jingyk
 */
@Data
public class RoomMessage {
    /**
     * 房間號
     */
    private String roomId;
    /**
     * 消息
     */
    private Object message;
}
