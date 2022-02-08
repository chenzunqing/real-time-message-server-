package ltd.ihk.platform.rtms.client.exception;

/**
 * @author jingyk
 */
public class RtmsException extends RuntimeException {
    protected String code;
    protected String message;

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
