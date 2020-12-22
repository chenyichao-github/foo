import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/simpleSocket")
public class SimpleEndpoint {
    @OnOpen
    public void start(Session session) {
        System.out.println("客户端连接进来了，session id:" + session.getId());
    }

    @OnMessage
    public void message(String message, Session session) throws IOException {
        System.out.println("接收到消息:" + message);
        RemoteEndpoint.Basic remote = session.getBasicRemote();
        remote.sendText("收到！收到！欢迎加入WebSocket的世界！");
    }

    @OnClose
    public void end(Session session, CloseReason closeReason) {
        System.out.println("客户端连接关闭了，session id:" + session.getId());
    }

    @OnError
    public void error(Session session, Throwable throwable) {
        System.err.println("客户端连接出错了，session id:" + session.getId());
    }
}
