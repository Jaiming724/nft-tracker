package dev.scratch.nfttracker.bootstrap;

import dev.scratch.nfttracker.service.NFTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Boostrap implements CommandLineRunner {
    private final NFTService nftService;

    @Autowired
    public Boostrap(NFTService nftService) {
        this.nftService = nftService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running stuff");

    }
}
