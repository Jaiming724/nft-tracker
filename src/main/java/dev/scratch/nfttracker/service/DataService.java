package dev.scratch.nfttracker.service;

import com.google.gson.Gson;
import dev.scratch.nfttracker.commands.NFTCommand;
import dev.scratch.nfttracker.model.collection.Collection;
import dev.scratch.nfttracker.model.nft.NFT;
import dev.scratch.nfttracker.model.stats.Stats;
import dev.scratch.nfttracker.util.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Service
public class DataService {
    private final HttpClient client;
    private final Gson gson;
    private static Logger logger = LoggerFactory.getLogger(DataService.class);

    public DataService() {
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }

    public CompletableFuture<Collection> getCollection(String name) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://api.opensea.io/api/v1/collection/" + name))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> gson.fromJson(s, Collection.class))
                .exceptionally(throwable -> {
                    logger.error("Exception:", throwable);
                    return null;
                }).toCompletableFuture();
    }

    public CompletableFuture<Stats> getStats(String name) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(String.format("https://api.opensea.io/api/v1/collection/%s/stats", name)))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> gson.fromJson(s, Stats.class))
                .exceptionally(throwable -> {
                    logger.error("Exception:", throwable);
                    return null;
                })
                .toCompletableFuture();
    }

    public CompletableFuture<NFT> getNFTMetaData(String address, int tokenID) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(String.format("https://eth-mainnet.alchemyapi.io/v2/%s/getNFTMetadata?tokenId=%d&tokenType=ERC721&contractAddress=%s", Values.alchemyKey, tokenID, address)))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> gson.fromJson(s, NFT.class))
                .exceptionally(throwable -> {
                    logger.error("Exception:", throwable);
                    return null;
                })
                .toCompletableFuture();
    }
}