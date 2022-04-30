package dev.scratch.nfttracker.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
    private List<Image> image;

    public String getId() {
        return id;
    }

    @Transient
    private String mediaLink;

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public NFTMongo setName(String name) {
        this.name = name;
        return this;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public NFTMongo setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
        return this;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }
}
