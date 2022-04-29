package dev.scratch.nfttracker.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import dev.scratch.nfttracker.model.mongo.NFTMongo;
import dev.scratch.nfttracker.service.DataService;
import dev.scratch.nfttracker.service.ImageService;
import dev.scratch.nfttracker.service.NFTService;
import dev.scratch.nfttracker.util.NoContractAddressException;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.util.logging.ExceptionLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
public class NFTCommand implements CommandExecutor {
    private static Logger logger = LoggerFactory.getLogger(NFTCommand.class);
    private NFTService nftService;
    private DataService dataService;
    private ImageService imageService;

    @Autowired
    public NFTCommand(NFTService nftService, DataService dataService, ImageService imageService) {
        this.nftService = nftService;
        this.dataService = dataService;
        this.imageService = imageService;
    }


    @Command(aliases = {"!nft"}, description = "Preview an NFT", usage = "!nft nft_name token_number")
    public String onInfoCommand(String[] str, Channel channel) {
        if (str.length != 2) {
            return "Usage: !nft nft_name token_number";
        }
        String name = str[0].toLowerCase(Locale.ROOT);
        int tokenID;
        try {
            tokenID = Integer.parseInt(str[1]);
        } catch (NumberFormatException e) {
            return "token number is not a number";
        }

        NFTMongo nftMongo = new NFTMongo();
        nftMongo.setName(name);
        String fileName = String.format("%s-%d", name, tokenID);

        Path currentRelativePath = Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().toString();
        String filePath = String.format("%s/%s.jpg", absolutePath, fileName);

        logger.info("Received Request for {} {}", name, tokenID);

        dataService.getCollection(name)
                .thenCompose(collection -> {
                    if (collection.getCollection().getPrimary_asset_contracts().size() == 0) {
                        logger.warn(String.format("No Contract address found for %s %d", name, tokenID));
                        throw new NoContractAddressException();
                    }
                    nftMongo.setContractAddress(collection.getContractAddress())
                            .setCount(collection.getCollection().getPrimary_asset_contracts().get(0).getTotal_supply());
                    return dataService.getNFTMetaData(collection.getContractAddress(), tokenID);
                })
                .thenCompose((nft) -> imageService.getImage(nft, fileName, filePath, nftMongo, tokenID))
                .thenAccept(s -> {
                    File file = new File(filePath);
                    file.delete();
                    sendMessage(s, nftMongo, tokenID, channel);
                })
                .handle((msg, ex) -> {
                    if (ex != null) {
                        new MessageBuilder().append("This NFT is not yet supported").send((TextChannel) channel);
                        if (!ex.getMessage().contains("No Contract Address Found")) {
                            logger.error("Exception:", ex);
                        }
                    }
                    return null;
                });
        return null;
    }

    private void sendMessage(String imageUrl, NFTMongo nftMongo, int tokenID, Channel channel) {
        if (imageUrl != null) {
            new MessageBuilder().setEmbed(new EmbedBuilder()
                    .setTitle(String.format("%s #%d", nftMongo.getName(), tokenID))
                    .setUrl(String.format("https://opensea.io/assets/%s/%d", nftMongo.getContractAddress(), tokenID))
                    .setImage(imageUrl)
            ).send((TextChannel) channel).exceptionally(ExceptionLogger.get());
        } else {
            new MessageBuilder().append("Unable to fetch NFT due to missing info").send((TextChannel) channel).exceptionally(ExceptionLogger.get());
        }
    }


}