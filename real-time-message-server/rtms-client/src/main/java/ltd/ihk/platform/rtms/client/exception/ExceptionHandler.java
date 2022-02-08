package ltd.ihk.platform.rtms.client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author jingyk
 */
@RequiredArgsConstructor
public class ExceptionHandler implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);
            @SuppressWarnings("unchecked")
            Map<String, Object> responseObj = objectMapper.readValue(body, Map.class);
            String message = (String) (responseObj.containsKey("message") ? responseObj.get("message") : responseObj.get("error"));
            String code = responseObj.containsKey("code") ? (String) responseObj.get("code") : "";
            if (StringUtils.hasText(code)) {
                if (code.equals(TargetNotFoundException.TARGET_NOT_FOUND_CODE)) {
                    return new TargetNotFoundException();
                }
                if (code.equals(RoomEmptyException.CODE)) {
                    return new RoomEmptyException();
                }
            }
            return new ServerException(code, message);
        } catch (IOException e) {
            return new IllegalStateException(e);
        }
    }
}
