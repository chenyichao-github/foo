import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GetTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                //.proxy(ProxySelector.of(new InetSocketAddress("proxy.crazyit.com", 80)))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/foo/secret.jsp"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "text/html")
                .GET()
                .build();
        HttpResponse.BodyHandler<String> bh = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, bh);
        System.out.println("响应的状态吗：" + response.statusCode());
        System.out.println("响应头：" + response.headers());
        System.out.println("响应体：" + response.body());
    }
}
