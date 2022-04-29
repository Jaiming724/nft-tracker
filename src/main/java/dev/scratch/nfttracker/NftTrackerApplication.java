package dev.scratch.nfttracker;

import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;
import dev.scratch.nfttracker.commands.NFTCommand;
import dev.scratch.nfttracker.commands.TrackNFTCommand;
import dev.scratch.nfttracker.util.Values;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class NftTrackerApplication {

    public static void main(String[] args) {
        Path currentRelativePath = Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().toString();

        Dotenv dotenv = Dotenv.configure().filename(".env").directory(absolutePath).load();
        Values.alchemyKey = dotenv.get("alchemyKey");
        Values.cloudinaryApiKey = dotenv.get("cloudinaryApiKey");
        Values.cloudinaryApiKeySecret = dotenv.get("cloudinaryApiKeySecret");


        ApplicationContext context = SpringApplication.run(NftTrackerApplication.class, args);
        DiscordApi api = new DiscordApiBuilder().setToken(dotenv.get("discordToken")).login().join();
        CommandHandler handler = new JavacordHandler(api);

        handler.registerCommand(context.getBean(NFTCommand.class));
        handler.registerCommand(context.getBean(TrackNFTCommand.class));
    }


}
