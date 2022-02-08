package ltd.ihk.platform.rtms.json;

import lombok.Data;

/**
 * @author jingyk
 */
@Data
public class OpsMessage {
    private Action action;
    private Object message;

    public enum Action {
        /**
         * 加入房間
         */
        JOIN_ROOM,
        /**
         * 離開房間
         */
        LEAVE_ROOM,
    }
}
