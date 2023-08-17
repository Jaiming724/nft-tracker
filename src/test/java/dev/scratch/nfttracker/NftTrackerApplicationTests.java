package dev.scratch.nfttracker;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@SpringBootTest
class NftTrackerApplicationTests {

    @Test
    void contextLoads() throws IOException, InterruptedException {
        final HttpClient client = HttpClient.newHttpClient();

        GsonBuilder builder = new GsonBuilder();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://api.opensea.io/api/v1/collection/doodles-official"))
                .build();
        HttpResponse<String> t = client.send(request, HttpResponse.BodyHandlers.ofString());


        Map o = (Map) builder.create().fromJson(t.body(), Map.class);

        System.out.println(o.get("collection"));
    }

}
