package dev.scratch.nfttracker.model.collection;

import com.google.gson.annotations.SerializedName;

public class Collection {
    @SerializedName("collection")
    private CollectionData collection;

    public CollectionData getCollection() {
        return collection;
    }

    public void setCollection(CollectionData collection) {
        this.collection = collection;
    }



    public String getContractAddress() {
        return collection.getPrimary_asset_contracts().get(0).getAddress();
    }
}
