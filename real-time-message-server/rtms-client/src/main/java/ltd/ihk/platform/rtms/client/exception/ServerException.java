package ltd.ihk.platform.rtms.client.exception;

/**
 * @author jingyk
 */
public class ServerException extends RtmsException {
    public ServerException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
