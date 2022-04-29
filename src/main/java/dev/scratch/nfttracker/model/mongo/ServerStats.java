package dev.scratch.nfttracker.model.mongo;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class ServerStats {
    @BsonProperty(value = "server_id")
    private String serverID;
    @BsonProperty(value = "nft_names")
    private List<Destination> nftNames;

    private int count;

    public String getServerID() {
        return serverID;
    }

    public ServerStats setServerID(String serverID) {
        this.serverID = serverID;
        return this;
    }

    public List<Destination> getNftNames() {
        return nftNames;
    }

    public void setNftNames(List<Destination> nftNames) {
        this.nftNames = nftNames;
    }

    public int getCount() {
        return count;
    }

    public ServerStats setCount(int count) {
        this.count = count;
        return this;
    }
}