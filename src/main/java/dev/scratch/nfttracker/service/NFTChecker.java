package dev.scratch.nfttracker.service;

import dev.scratch.nfttracker.model.mongo.Destination;
import dev.scratch.nfttracker.model.mongo.NFTMongo;
import dev.scratch.nfttracker.model.mongo.TrackedNFT;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NFTChecker {
    boolean isEnabled = true;

    private final TrackedNFTService trackedNFTService;
    private DataService dataService;
    private DiscordApi api;
    private static final Logger logger = LoggerFactory.getLogger(NFTChecker.class);

    @Autowired
    public NFTChecker(TrackedNFTService trackedNFTService, DataService dataService, DiscordApi api) {
        this.trackedNFTService = trackedNFTService;
        this.dataService = dataService;
        this.api = api;
    }

    @Scheduled(fixedRate = 10000)
    public void loopNFTs() {
        if (isEnabled) {
            isEnabled = false;
            List<TrackedNFT> nfts = trackedNFTService.getAll();
            for (TrackedNFT nft : nfts) {
                dataService.getStats(nft.getName())
                        .thenAccept(count -> checkNFT(nft, count));
            }
            isEnabled = true;
        }
    }

    public void checkNFT(TrackedNFT nft, Integer count) {
        if (count != null) {
            logger.debug("Checking chance for {}, previous value was {}, current is {}", nft.getName(), nft.getCount(), count);
            if (count < nft.getCount()) {
                trackedNFTService.setCount(nft.getName(),count);
            }
            if (count > nft.getCount()) {
                logger.info("Detected change for {} from {} to {}", nft.getName(), nft.getCount(), count);
                for (int i = nft.getCount() + 1; i <= count; i++) {
                    NFTMongo nftMongo = new NFTMongo();
                    nftMongo.setName(nft.getName());

                    int finalI = i;
                    dataService.getImageURL(nft.getName(), nftMongo, i)
                            .thenAccept(url -> sendEmbed(nft, finalI, url));
                }
                trackedNFTService.setCount(nft.getName(), count);


            }
        } else {
            logger.warn(String.format("Supply for %s was empty. This NFT might no longer be supported", nft.getName()));
        }
    }

    public void sendEmbed(TrackedNFT nft, int count, String url) {
        for (Destination destination : nft.getServers()) {
            logger.info("Sending change for {} to {}", nft.getName(), destination.getChannelID());
            String msg;
            if (destination.getRoleID() == null) {
                msg = String.format("%s #%d was just minted", nft.getName(), count);
            } else {
                msg = String.format("%s #%d was just minted %s", nft.getName(), count, destination.getRoleID());
            }
            api.getChannelById(destination.getChannelID()).flatMap(Channel::asServerTextChannel).ifPresent(serverTextChannel -> serverTextChannel.sendMessage(msg));
            if (url != null) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle(String.format("%s #%d", nft.getName(), count))
                        .setImage(url);
                api.getChannelById(destination.getChannelID()).flatMap(Channel::asServerTextChannel).ifPresent(serverTextChannel -> serverTextChannel.sendMessage(embedBuilder));
            }
        }

    }

}
