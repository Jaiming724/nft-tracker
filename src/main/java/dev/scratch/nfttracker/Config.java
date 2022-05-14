package dev.scratch.nfttracker;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
    @Value("${discordToken}")
    private String discordToken;
    @Value("${cloudinaryApiKey}")
    private String cloudinaryApiKey;
    @Value("${cloudinaryApiKeySecret}")
    private String cloudinaryApiKeySecret;

    @Bean
    @Scope("singleton")
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxc99hkhe",
                "api_key", cloudinaryApiKey,
                "api_secret", cloudinaryApiKeySecret));
    }

    @Bean
    @Scope("singleton")
    public DiscordApi discordApi() {
        return new DiscordApiBuilder().setToken(discordToken).login().join();
    }

    @Bean
    @Scope("singleton")
    public CommandHandler commandHandler() {
        return new JavacordHandler(discordApi());
    }
}
