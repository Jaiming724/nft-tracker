package dev.scratch.nfttracker.model.nft;

import java.util.ArrayList;

public class Properties {
    private String category;
    private ArrayList<File> files;
    private ArrayList<Object> creators;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public ArrayList<Object> getCreators() {
        return creators;
    }

    public void setCreators(ArrayList<Object> creators) {
        this.creators = creators;
    }
}
