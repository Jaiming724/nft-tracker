package dev.scratch.nfttracker.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import dev.scratch.nfttracker.model.mongo.Destination;
import dev.scratch.nfttracker.model.mongo.NFTMongo;
import dev.scratch.nfttracker.model.mongo.TrackedNFT;
import dev.scratch.nfttracker.model.nft.NFT;
import dev.scratch.nfttracker.service.DataService;
import dev.scratch.nfttracker.service.NFTService;
import dev.scratch.nfttracker.service.ServerStatsService;
import dev.scratch.nfttracker.service.TrackedNFTService;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.logging.ExceptionLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackNFTCommand implements CommandExecutor {
    private static Logger logger = LoggerFactory.getLogger(TrackNFTCommand.class);
    private NFTService nftService;
    private ServerStatsService serverStatsService;
    private DataService dataService;
    private TrackedNFTService trackedNFTService;

    @Autowired
    public TrackNFTCommand(NFTService nftService, ServerStatsService serverStatsService, DataService dataService, TrackedNFTService trackedNFTService) {
        this.nftService = nftService;
        this.serverStatsService = serverStatsService;
        this.dataService = dataService;
        this.trackedNFTService = trackedNFTService;
    }

    @Command(aliases = {"!add"}, description = "Add an NFT to Track", usage = "!add NFT_Name role")
    public String addNFT(String[] str, Channel channel, Server server, User user) {
        if (!server.isAdmin(user)) {
            return "You need to have admin permissions to run this command";
        }
        String nftName = str[0];
        String roleID = str[1];
        logger.debug("Server {} has added {}", server.getIdAsString(), nftName);
        Destination destination = new Destination(roleID, server.getIdAsString(), channel.getIdAsString());

        if (trackedNFTService.containNFt(nftName)) {
            logger.debug("{} already exists in NFT database, adding channel", server.getIdAsString());
            trackedNFTService.addDestination(nftName, destination);
            return "Added NFT";
        } else {
            TrackedNFT trackedNFT = new TrackedNFT();
            trackedNFT.getServers().add(destination);
            dataService.getStats(nftName, trackedNFT)
                    .thenApply(this::addNFT)
                    .thenAccept(nft -> sendMessage(nft, channel));

        }
        return null;
    }

    private TrackedNFT addNFT(TrackedNFT nft) {
        if (nft != null) {
            trackedNFTService.insert(nft);
        }
        return nft;
    }

    private void sendMessage(TrackedNFT nft, Channel channel) {
        if (nft == null) {
            new MessageBuilder().append("That NFT is currently unsupported").send((TextChannel) channel).exceptionally(ExceptionLogger.get());
        } else {
            new MessageBuilder().append("Added NFT").send((TextChannel) channel).exceptionally(ExceptionLogger.get());
        }
    }
}
