package dev.scratch.nfttracker.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import dev.scratch.nfttracker.model.mongo.Destination;
import dev.scratch.nfttracker.model.mongo.ServerStats;
import dev.scratch.nfttracker.model.mongo.TrackedNFT;
import dev.scratch.nfttracker.service.DataService;
import dev.scratch.nfttracker.service.NFTService;
import dev.scratch.nfttracker.service.ServerStatsService;
import dev.scratch.nfttracker.service.TrackedNFTService;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
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

    @Command(aliases = {"!add"}, description = "Add an NFT to Track", usage = "!add NFT_Name role(optional)")
    public String addNFT(String[] str, Channel channel, Server server, User user) {
        if (!server.isAdmin(user)) {
            return "You need to have admin permissions to run this command";
        }
        if (str.length < 1 || str.length > 2) {
            return "Please follow format !add NFT_Name role(optional)";
        }
        String nftName;
        String roleID;
        if (str.length == 1) {
            nftName = str[0];
            roleID = null;
        } else {
            nftName = str[0];
            roleID = str[1];
        }

        logger.debug("Server {} has added {}", server.getIdAsString(), nftName);
        Destination destination = new Destination(roleID, server.getIdAsString(), channel.getIdAsString()).setNftName(nftName);
        if (serverStatsService.containsServer(server.getIdAsString())) {
            if (serverStatsService.getCount(server.getIdAsString()) >= 1) {
                return "Sorry, you can only track 1 NFT in one channel per server at this time. Please contact Pose#1715 if you are interested in adding more";
            }
            if (serverStatsService.containsNFT(nftName, server.getIdAsString(), channel.getIdAsString())) {
                return "You have already added this NFT";
            }
            serverStatsService.addNFT(server.getIdAsString(), destination);

        } else {
            ServerStats serverStats = new ServerStats()
                    .setServerID(server.getIdAsString())
                    .setCount(1);
            serverStats.getDestinations().add(destination);
            serverStatsService.insert(serverStats);
        }

        if (trackedNFTService.containNFt(nftName)) {
            logger.debug("{} already exists in NFT database, adding channel", nftName);
            trackedNFTService.addDestination(nftName, destination);
            return "Added NFT";
        } else {
            TrackedNFT trackedNFT = new TrackedNFT();
            trackedNFT.getServers().add(destination);
            dataService.getStats(nftName, trackedNFT)
                    .thenApply(this::insertNFT)
                    .thenAccept(nft -> sendMessage(nft, channel));
        }
        return null;
    }

    @Command(aliases = {"!remove"}, description = "Remove an NFT", usage = "!remove NFT_Name")
    public String removeNFT(String nftName, Channel channel, Server server, User user) {
        if (nftName == null)
            return "Please follow format !remove NFT_Name";

        if (!server.isAdmin(user))
            return "You need to have admin permissions to run this command";

        if (!serverStatsService.containsServer(server.getIdAsString()) || !serverStatsService.containsNFT(nftName, server.getIdAsString(), channel.getIdAsString()))
            return "NFT already removed !";


        serverStatsService.removeNFT(nftName, server.getIdAsString(), channel.getIdAsString());

        return "Removed NFT";
    }

    private TrackedNFT insertNFT(TrackedNFT nft) {
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
