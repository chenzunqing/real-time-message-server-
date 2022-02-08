package ltd.ihk.platform.rtms.client.exception;

/**
 * @author jingyk
 */
public class RoomEmptyException extends RtmsException {
    public static final String CODE = "60002";

    public RoomEmptyException() {
        this.code = CODE;
        this.message = "room is empty";
    }
}
