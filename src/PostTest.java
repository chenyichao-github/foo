import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PostTest {
    public static void main(String[] args) throws IOException, InterruptedException {
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
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("POST请求的响应码：" + response.statusCode());
        System.out.println("POST请求的响应体：" + response.body());
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/foo/secret.jsp"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "text/html")
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET请求的响应码：" + response.statusCode());
        System.out.println("GET请求的响应体：" + response.body());
    }
}
