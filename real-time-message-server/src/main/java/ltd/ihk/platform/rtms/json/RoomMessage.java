package ltd.ihk.platform.rtms.json;

import lombok.Data;

/**
 * @author jingyk
 */
@Data
public class RoomMessage {
    private String roomId;
    private Object message;
}
