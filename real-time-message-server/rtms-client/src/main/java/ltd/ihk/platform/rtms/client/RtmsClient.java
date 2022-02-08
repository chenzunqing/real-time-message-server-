package ltd.ihk.platform.rtms.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Headers;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import ltd.ihk.platform.rtms.client.exception.ExceptionHandler;
import ltd.ihk.platform.rtms.client.exception.RoomEmptyException;
import ltd.ihk.platform.rtms.client.exception.ServerException;
import ltd.ihk.platform.rtms.client.exception.TargetNotFoundException;
import ltd.ihk.platform.rtms.client.json.PersonMessage;
import ltd.ihk.platform.rtms.client.json.Receivers;
import ltd.ihk.platform.rtms.client.json.RoomMessage;

/**
 * @author jingyk
 */
public interface RtmsClient {

    /**
     * 新的標準客戶端
     *
     * @param endpoint 服務端地址
     * @return 標準客戶端
     */
    static RtmsClient newStandardClient(String endpoint) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new ExceptionHandler(new ObjectMapper()))
                .target(RtmsClient.class, endpoint);
    }

    /**
     * 發送消息
     *
     * @param message 房間消息
     * @return 接受消息的人
     * @throws ServerException    服務端錯誤
     * @throws RoomEmptyException 空房間
     */
    @RequestLine("POST /messages")
    @Headers("Content-Type: application/json;charset=utf-8")
    Receivers sendMessage(RoomMessage message) throws RoomEmptyException, ServerException;


    /**
     * 發送消息
     *
     * @param personMessage 個人消息
     * @throws TargetNotFoundException 目標不在綫
     * @throws ServerException         服務端錯誤
     */
    @RequestLine("POST /person-messages")
    @Headers("Content-Type: application/json;charset=utf-8")
    void sendMessage(PersonMessage personMessage) throws TargetNotFoundException, ServerException;
}
