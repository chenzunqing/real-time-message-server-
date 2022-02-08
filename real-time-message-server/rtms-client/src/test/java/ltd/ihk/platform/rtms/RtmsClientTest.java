package ltd.ihk.platform.rtms;

import ltd.ihk.platform.rtms.client.RtmsClient;
import ltd.ihk.platform.rtms.client.json.PersonMessage;
import ltd.ihk.platform.rtms.client.json.Receivers;
import ltd.ihk.platform.rtms.client.json.RoomMessage;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class RtmsClientTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        //RtmsClient rtmsClient = RtmsClient.newStandardClient("http://192.168.0.8:9093");
        RtmsClient rtmsClient = RtmsClient.newStandardClient("http://192.168.1.176:8080");
        RoomMessage roomMessage = new RoomMessage();
        roomMessage.setRoomId("room2");
        roomMessage.setMessage("hello");
        Receivers receivers = rtmsClient.sendMessage(roomMessage);

        PersonMessage personMessage = new PersonMessage();
        personMessage.setTargetId("1566");
        personMessage.setMessage("Hello Siwei");

        rtmsClient.sendMessage(personMessage);
    }
}
