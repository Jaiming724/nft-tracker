package dev.scratch.nfttracker.model.nft;


import dev.scratch.nfttracker.model.TokenMetadata;

public class Id {
    private String tokenId;
    private TokenMetadata tokenMetadata;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public TokenMetadata getTokenMetadata() {
        return tokenMetadata;
    }

    public void setTokenMetadata(TokenMetadata tokenMetadata) {
        this.tokenMetadata = tokenMetadata;
    }
}
