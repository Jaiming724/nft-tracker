package dev.scratch.nfttracker;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dev.scratch.nfttracker.util.Values;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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
}
