package dev.scratch.nfttracker.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import dev.scratch.nfttracker.model.mongo.Destination;
import dev.scratch.nfttracker.service.NFTService;
import dev.scratch.nfttracker.service.ServerStatsService;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackNFTCommand implements CommandExecutor {
    private static Logger logger = LoggerFactory.getLogger(TrackNFTCommand.class);
    private NFTService nftService;
    private ServerStatsService serverStatsService;

    @Autowired
    public TrackNFTCommand(NFTService nftService, ServerStatsService serverStatsService) {
        this.nftService = nftService;
        this.serverStatsService = serverStatsService;
    }

    @Command(aliases = {"!add"}, description = "Add an NFT to Track", usage = "!add NFT_Name role")
    public String addNFT(String[] str, Channel channel, Server server) {
        String nftName = str[0];
        String roleID = str[1];
        logger.debug("Server {} has added {}", server.getIdAsString(), nftName);
        if (nftService.containNFt(nftName)) {
            logger.debug("{} already exists in NFT database, adding server", server.getIdAsString());
            Destination destination = new Destination(roleID, server.getIdAsString(), channel.getIdAsString());
            nftService.addDestination(nftName, destination);
        }
        return null;
    }
}
