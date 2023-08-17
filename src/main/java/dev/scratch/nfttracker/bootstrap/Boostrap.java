package dev.scratch.nfttracker.bootstrap;

import dev.scratch.nfttracker.service.NFTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Boostrap implements CommandLineRunner {
    @Value("${spring.data.mongodb.uri}")
    private String t;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("hello" +t);
    }
}
