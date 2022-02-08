# 实时消息服务器

> socket.io协议服务端  
> 基于 https://github.com/mrniko/netty-socketio 实现

### 一、建立连接与认证
服务端利用`socket.io`的命名空间提供一个暴露断点实现`房间消息`和`个人到个人消息`实时推送。
客户端可使用如下代码与服务器建立连接：
```javascript
let serverSocket = io.connect('https://api.v1.ihk.ltd/rtms/entrypoint?token=073da97a-47a0-4cae-a7b1-692e7fa8f707')
```
服务端提供 request parameter `token` 进行认证。
服务端实现2种认证方式，`anonymous`和`ms-account`  
anonymous: 匿名认证，不用认证即可连接到服务器，一般用于测试服务连通性  
ms-account: 利用`ms-public-account`服务的认证服务提供认证

### 二、房间消息
> 多个人提前加入同一个房间，那么发往改房间的消息，房间内的所有人都能收到。
1. 客户端操纵加入房间(ACK模式)
```javascript
serverSocket.emit('ops', {
    action: "JOIN_ROOM",
    message: {
        room: "room1"
    }
}, ack => {
    console.log('join room success')
})
```
2. 客户端操作退出房间(ACK模式)
```javascript
serverSocket.emit('ops', {
    action: "LEAVE_ROOM",
    message: {
        room: "room1"
    }
}, ack => {
    console.log('leave room success')
})
```
3. 接收消息
```jacascript
serverSocket.on('message', msg => {
    console.log(message)
})
```
4. 发送消息
```javascript
serverSocket.emit('room', {
    roomId: "room1",
    message: {}
})
```
### 三、个人到个人消息
1. 接收消息
```jacascript
serverSocket.on('message', msg => {
    console.log(message)
})
```
2. 发送消息
```javascript
serverSocket.emit('p2p', {
    targetId: "{userId}",
    message: {}
})
```
