package dev.scratch.nfttracker;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import de.btobastian.sdcf4j.CommandHandler;
import dev.scratch.nfttracker.commands.HelpCommand;
import dev.scratch.nfttracker.commands.NFTCommand;
import dev.scratch.nfttracker.commands.TrackNFTCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableEncryptableProperties
public class NftTrackerApplication {
    public static void main(String[] args) {

        if (!args[0].equals("production")) {
            System.setProperty("spring.data.mongodb.uri", "mongodb://localhost:27017/NFT");
        }
        ApplicationContext context = SpringApplication.run(NftTrackerApplication.class, args);
        CommandHandler handler = context.getBean(CommandHandler.class);

        handler.registerCommand(context.getBean(NFTCommand.class));
        handler.registerCommand(context.getBean(TrackNFTCommand.class));
        handler.registerCommand(context.getBean(HelpCommand.class));
    }


}
