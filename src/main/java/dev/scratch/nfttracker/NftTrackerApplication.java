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
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableScheduling
public class NftTrackerApplication {

    public static void main(String[] args) {
        Path currentRelativePath = Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().toString();

        Dotenv dotenv = Dotenv.configure().filename(".env").directory(absolutePath).load();
        Values.alchemyKey = dotenv.get("alchemyKey");
        Values.cloudinaryApiKey = dotenv.get("cloudinaryApiKey");
        Values.cloudinaryApiKeySecret = dotenv.get("cloudinaryApiKeySecret");
        if (args[0].equals("production")) {
            Values.mongoUrl = dotenv.get("mongoUrlProduction");
        } else {
            Values.mongoUrl = dotenv.get("mongoUrlLocal");
        }
        System.setProperty("spring.data.mongodb.uri", Values.mongoUrl);


        ApplicationContext context = SpringApplication.run(NftTrackerApplication.class, args);
        CommandHandler handler =context.getBean(CommandHandler.class);

        handler.registerCommand(context.getBean(NFTCommand.class));
        handler.registerCommand(context.getBean(TrackNFTCommand.class));
    }


}
