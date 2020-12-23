import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class WebSocketChat {
    public static void main(String[] args) {
        WebSocket.Listener listener = new WebSocket.Listener() {
            @Override
            public void onOpen(WebSocket webSocket) {
                System.out.println("已打开链接");
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
        client.newWebSocketBuilder().buildAsync(URI.create("ws://127.0.0.1:8888/foo/chatSocket"), listener)
                .thenAccept(webSocket -> {
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            webSocket.sendText(line, true);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                })
                .join();
    }
}
