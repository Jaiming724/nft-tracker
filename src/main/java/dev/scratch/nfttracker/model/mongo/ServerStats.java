package dev.scratch.nfttracker.model.mongo;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class ServerStats {
    @BsonProperty(value = "server_id")
    private String serverID;
    @BsonProperty(value = "nft_names")
    private List<Destination> destinations;

    public ServerStats() {
        destinations = new ArrayList<>();
    }

    private int count;

    public String getServerID() {
        return serverID;
    }

    public ServerStats setServerID(String serverID) {
        this.serverID = serverID;
        return this;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public int getCount() {
        return count;
    }

    public ServerStats setCount(int count) {
        this.count = count;
        return this;
    }
}