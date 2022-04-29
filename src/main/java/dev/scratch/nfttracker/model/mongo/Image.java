package dev.scratch.nfttracker.model.mongo;

import org.springframework.data.mongodb.core.mapping.Field;

public class Image {
    @Field(value = "token_id")
    private String tokenID;
    private String link;

    public Image() {
    }

    public Image(String tokenID, String link) {
        this.tokenID = tokenID;
        this.link = link;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
