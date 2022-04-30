package dev.scratch.nfttracker.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document()
public class TrackedNFT {
    private String name;
    private List<Destination> servers;
    private Integer count;

    public TrackedNFT() {
        servers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public TrackedNFT setName(String name) {
        this.name = name;
        return this;
    }

    public List<Destination> getServers() {
        return servers;
    }

    public TrackedNFT setServers(List<Destination> servers) {
        this.servers = servers;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public TrackedNFT setCount(Integer count) {
        this.count = count;
        return this;
    }
}
