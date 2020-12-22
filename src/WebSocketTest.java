import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class WebSocketTest {
    public static void main(String[] args) throws InterruptedException {
        WebSocket.Listener listener = new WebSocket.Listener() {
            @Override
            public void onOpen(WebSocket webSocket) {
                System.out.println("已打开连接");
                webSocket.sendText("我是疯狂软件教育中心！", true);
                webSocket.request(1);
            }

            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence message, boolean last) {
                System.out.println(message);
                webSocket.request(1);
                return null;
            }
        };
        HttpClient client = HttpClient.newHttpClient();
        client.newWebSocketBuilder().buildAsync(URI.create("ws://127.0.0.1:8888/foo/simpleSocket"), listener);
        Thread.sleep(5000);
    }
}
