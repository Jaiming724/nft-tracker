package dev.scratch.nfttracker;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;
import dev.scratch.nfttracker.util.Values;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class Config {
    @Bean
    @Scope("singleton")
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxc99hkhe",
                "api_key", Values.cloudinaryApiKey,
                "api_secret", Values.cloudinaryApiKeySecret));
    }

    @Bean
    @Scope("singleton")
    public DiscordApi discordApi() {
        Path currentRelativePath = Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().toString();

        Dotenv dotenv = Dotenv.configure().filename(".env").directory(absolutePath).load();
        return new DiscordApiBuilder().setToken(dotenv.get("discordToken")).login().join();
    }

    @Bean
    @Scope("singleton")
    public CommandHandler commandHandler() {
        return new JavacordHandler(discordApi());
    }
}
