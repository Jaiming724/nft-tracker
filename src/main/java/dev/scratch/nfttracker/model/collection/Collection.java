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

    public String getCount() {
        String count;
        try {
            count = collection.getPrimary_asset_contracts().get(0).getTotal_supply();
        } catch (NullPointerException e) {
            count = null;
        }
        return count;
    }

    public String getContractAddress() {
        String address;
        try {
            address = collection.getPrimary_asset_contracts().get(0).getAddress();
        } catch (NullPointerException e) {
            address = null;
        }
        return address;
    }
}
