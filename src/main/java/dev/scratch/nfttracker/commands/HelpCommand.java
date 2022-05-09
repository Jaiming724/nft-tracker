package dev.scratch.nfttracker.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements CommandExecutor {
    private MessageBuilder messageBuilder = new MessageBuilder()
            .append("NFT Tracker can alert your server when a new NFT is minted and generate a preview. For any questions or suggestions, please message Pose#1715")
            .setEmbed(new EmbedBuilder()
                    .setTitle("How to use NFT Tracker")
                    .setDescription("Currently only supports NFT on Opensea")
                    .addField("!nft nft_name token_number", "Generates a preview of an nft. To get the nft name, please find the name of the nft on Opensea, Opensea follows the format of https://opensea.io/collection/nft-name")
                    .addField("!add nft_name ping(optional)", "Add NFT to be tracked. When a new NFT is minted, the bot will alert users in the channel the command was sent with ping if configured")
                    .addField("!remove nft_name", "Stop tracking the NFT")
            );

    @Command(aliases = {"!help"}, description = "Give information about the bot", usage = "!help")
    public void help(String[] str, Channel channel) {
        messageBuilder.send((TextChannel) channel);
    }

}
