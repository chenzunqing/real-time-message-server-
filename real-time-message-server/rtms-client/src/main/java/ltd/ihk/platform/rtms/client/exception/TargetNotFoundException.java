package ltd.ihk.platform.rtms.client.exception;

/**
 * @author jingyk
 */
public class TargetNotFoundException extends RtmsException {
    public static final String TARGET_NOT_FOUND_CODE = "60001";

    public TargetNotFoundException() {
        this.code = code = TARGET_NOT_FOUND_CODE;
        this.message = "targetId not found";
    }
}
