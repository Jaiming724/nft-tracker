package dev.scratch.nfttracker.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Document("nfts")
public class NFTMongo {
    @Id
    private String id;
    private String name;
    @Field(value = "contract_address")
    private String contractAddress;
    private String count;
    private List<Destination> servers;
    private List<Image> image;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public NFTMongo setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
        return this;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Destination> getServers() {
        return servers;
    }

    public void setServers(List<Destination> servers) {
        this.servers = servers;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }
}