import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ServerEndpoint(value = "/chatSocket")
public class ChatEndpoint {
    static List<Session> clients = Collections.synchronizedList(new ArrayList<Session>());

    @OnOpen
    public void start(Session session) {
        clients.add(session);
    }

    @OnMessage
    public void message(String message, Session session) throws IOException {
        for (Session s : clients) {
            RemoteEndpoint.Basic remote = s.getBasicRemote();
            remote.sendText(message);
        }
    }

    @OnClose
    public void end(Session session, CloseReason closeReason) {
        clients.remove(session);
    }

    @OnError
    public void error(Session session, Throwable throwable) {
        clients.remove(session);
    }
}
