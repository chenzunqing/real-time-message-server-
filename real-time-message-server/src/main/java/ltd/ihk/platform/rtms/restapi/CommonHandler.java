package ltd.ihk.platform.rtms.restapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author jingyk
 */
@ControllerAdvice
public class CommonHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result onIllegalArgumentException(IllegalArgumentException e) {
        Result result = new Result();
        result.setCode("400");
        result.setMessage(e.getMessage());
        return result;
    }

    @ExceptionHandler(value = RtmsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result onRtmsException(RtmsException e) {
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        return result;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result onOtherException(Exception e) {
        Result result = new Result();
        result.setCode("500");
        result.setMessage(e.getMessage());
        return result;
    }

    @Data
    public static class Result {
        private String code;
        private String message;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    public static class RtmsException extends RuntimeException {
        private String code;
        private String message;
    }
}
