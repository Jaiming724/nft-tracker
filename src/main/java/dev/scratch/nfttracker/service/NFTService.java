package dev.scratch.nfttracker.service;

import dev.scratch.nfttracker.model.mongo.Destination;
import dev.scratch.nfttracker.model.mongo.Image;
import dev.scratch.nfttracker.model.mongo.NFTMongo;
import dev.scratch.nfttracker.model.nft.NFT;
import dev.scratch.nfttracker.repositories.NFTRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class NFTService {
    private final NFTRepository nftRepository;
    private final MongoTemplate mt;

    @Autowired
    public NFTService(NFTRepository nftRepository, MongoTemplate mt) {
        this.nftRepository = nftRepository;
        this.mt = mt;
    }

    public void insert(NFTMongo nftMongo) {
        nftRepository.insert(nftMongo);
    }

    public void addImage(String tokenID, String nftLink, String nftName) {
        Document newImage = new Document("token_id", tokenID).append("link", nftLink);
        mt.updateFirst(Query.query(Criteria.where("name").is(nftName)), new Update().push("image", newImage), NFTMongo.class);
    }

    public void addDestination(String nftName, Destination destination) {
        mt.updateFirst(Query.query(Criteria.where("name").is(nftName)), new Update().push("servers", destination), NFTMongo.class);
    }

    public void setCount(String nftName, String count) {
        mt.updateFirst(Query.query(Criteria.where("name").is(nftName)), new Update().set("count", count), NFTMongo.class);
    }

    public NFTMongo findByName(String name) {
        return nftRepository.findNFTMongoByName(name);
    }

    public String getImageUrl(String name, String tokenID) {
        NFTMongo nft = findByName(name);
        if (nft != null) {
            for (Image image : nft.getImage()) {
                if (image.getTokenID().equals(tokenID)) {
                    return image.getLink();
                }
            }
        }
        return null;

    }

    public boolean containNFt(String name) {
        return findByName(name) != null;
    }
}
