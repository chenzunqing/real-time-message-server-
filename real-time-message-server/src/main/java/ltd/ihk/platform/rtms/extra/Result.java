package ltd.ihk.platform.rtms.extra;

import lombok.Data;

/**
 * @author jingyk
 */
@Data
public class Result<T> {
    private boolean success;
    private String message;
    private Integer code;
    private String i18n;
    private T result;
    private long timestamp;
}
