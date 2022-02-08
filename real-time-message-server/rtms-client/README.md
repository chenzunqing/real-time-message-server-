# rtms-client

### Maven 
```xml
<dependency>
    <groupId>ltd.ihk.platform.rtms</groupId>
    <artifactId>rtms-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

```java
// 1. 單例客戶端對象
RtmsClient rtmsClient = RtmsClient.newStandardClient("http://127.0.0.1:8080");

// 2. 發送room消息
RoomMessage roomMessage = new RoomMessage();
roomMessage.setRoomId("room1");
roomMessage.setMessage("hello");
rtmsClient.sendMessage(roomMessage);

// 3. 發送person消息
PersonMessage personMessage = new PersonMessage();
personMessage.setTargetId("2556974441421");
personMessage.setMessage("hello");
rtmsClient.sendMessage(roomMessage);
```