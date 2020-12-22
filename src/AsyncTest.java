import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AsyncTest {
    public static void main(String[] args) throws InterruptedException {
        CookieHandler.setDefault(new CookieManager());
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .cookieHandler(CookieHandler.getDefault())
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/foo/login.jsp"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("name=crazyit.org&pass=leegang"))
                .build();
        HttpRequest getReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/foo/secret.jsp"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "text/html")
                .GET()
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(
                resp -> new Object[]{resp.statusCode(), resp.body()})
                .thenAccept(rt -> {
                    System.out.println("POST请求的响应码：" + rt[0]);
                    System.out.println("POST请求的响应体：" + rt[1]);
                    client.sendAsync(getReq, HttpResponse.BodyHandlers.ofString())
                            .thenAccept(resp -> {
                                System.out.println("GET请求的响应码：" + resp.statusCode());
                                System.out.println("GET请求的响应体：" + resp.body());
                            });
                });
        System.out.println("--程序结束--");
        Thread.sleep(1000);
    }
}
