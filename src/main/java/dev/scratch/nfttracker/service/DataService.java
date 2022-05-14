package dev.scratch.nfttracker.service;

import com.google.gson.Gson;
import dev.scratch.nfttracker.model.collection.Collection;
import dev.scratch.nfttracker.model.mongo.NFTMongo;
import dev.scratch.nfttracker.model.mongo.TrackedNFT;
import dev.scratch.nfttracker.model.nft.NFT;
import dev.scratch.nfttracker.model.stats.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Service
public class DataService {
    private final HttpClient client;
    private final Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);
    private ImageService imageService;
    @Value("${alchemyKey}")
    private String alchemyKey;

    @Autowired
    public DataService(ImageService imageService) {
        client = HttpClient.newHttpClient();
        gson = new Gson();
        this.imageService = imageService;
    }

    public CompletableFuture<NFTMongo> getCollection(String name, NFTMongo nftMongo) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://api.opensea.io/api/v1/collection/" + name))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> gson.fromJson(s, Collection.class))
                .thenApply(collection -> {
                    nftMongo.setContractAddress(collection.getContractAddress());
                    return nftMongo;
                })
                .exceptionally(throwable -> {
                    logger.error("Exception:", throwable);
                    return null;
                }).toCompletableFuture();
    }

    public CompletableFuture<TrackedNFT> getStats(String name, TrackedNFT nft) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(String.format("https://api.opensea.io/api/v1/collection/%s/stats", name)))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> gson.fromJson(s, Stats.class))
                .thenApply(stats -> {
                    int count;
                    try {
                        count = stats.getStatsHelper().getCount();
                    } catch (NullPointerException e) {
                        return null;
                    }
                    nft.setName(name).setCount(count);
                    return nft;
                })
                .exceptionally(throwable -> {
                    logger.error("Exception:", throwable);
                    return null;
                })
                .toCompletableFuture();
    }

    public CompletableFuture<Integer> getStats(String name) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(String.format("https://api.opensea.io/api/v1/collection/%s/stats", name)))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> gson.fromJson(s, Stats.class))
                .thenApply(stats -> {
                    int count;
                    try {
                        count = stats.getStatsHelper().getCount();
                    } catch (NullPointerException e) {
                        return null;
                    }
                    return count;
                })
                .exceptionally(throwable -> {
                    logger.error("Exception:", throwable);
                    return null;
                })
                .toCompletableFuture();
    }

    public CompletableFuture<NFTMongo> getNFTMetaData(NFTMongo nftMongo, int tokenID) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(String.format("https://eth-mainnet.alchemyapi.io/v2/%s/getNFTMetadata?tokenId=%d&tokenType=ERC721&contractAddress=%s", alchemyKey, tokenID, nftMongo.getContractAddress())))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> gson.fromJson(s, NFT.class))
                .thenApply(nft -> {
                    if (nft.getMedia().get(0).getGateway() != null) {
                        nftMongo.setMediaLink(nft.getMedia().get(0).getGateway());
                    }
                    return nftMongo;
                })
                .exceptionally(throwable -> {
                    logger.error("Exception:", throwable);
                    return null;
                })
                .toCompletableFuture();
    }

    public CompletableFuture<String> getImageURL(String name, NFTMongo nftMongo, int tokenID) {
        String fileName = String.format("%s-%d", name, tokenID);

        Path currentRelativePath = Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().toString();
        String filePath = String.format("%s/%s.jpg", absolutePath, fileName);
        return getCollection(name, nftMongo)
                .thenCompose(nftMongo1 -> getNFTMetaData(nftMongo1, tokenID))
                .thenCompose((nftMongo1) -> imageService.getImage(fileName, filePath, nftMongo1, tokenID))
                .exceptionally(throwable -> {
                    logger.error("Exception: ", throwable);
                    return null;
                });
    }
}
