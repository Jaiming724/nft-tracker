package dev.scratch.nfttracker.model.nft;

import java.util.ArrayList;

public class NFT {
    private Contract contract;
    private Id id;
    private String title;
    private String description;
    private TokenUri tokenUri;
    private ArrayList<Medium> media;
    private Metadata metadata;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TokenUri getTokenUri() {
        return tokenUri;
    }

    public void setTokenUri(TokenUri tokenUri) {
        this.tokenUri = tokenUri;
    }

    public ArrayList<Medium> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<Medium> media) {
        this.media = media;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
