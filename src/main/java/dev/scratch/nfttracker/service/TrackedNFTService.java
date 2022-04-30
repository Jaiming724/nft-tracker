package dev.scratch.nfttracker.service;

import dev.scratch.nfttracker.model.mongo.Destination;
import dev.scratch.nfttracker.model.mongo.NFTMongo;
import dev.scratch.nfttracker.model.mongo.TrackedNFT;
import dev.scratch.nfttracker.repositories.TrackedNFTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class TrackedNFTService {
    private final TrackedNFTRepository trackedNFTRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public TrackedNFTService(TrackedNFTRepository trackedNFTRepository, MongoTemplate mongoTemplate) {
        this.trackedNFTRepository = trackedNFTRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void insert(TrackedNFT nft) {
        trackedNFTRepository.insert(nft);
    }

    public void addDestination(String nftName, Destination destination) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(nftName)), new Update().push("servers", destination), TrackedNFT.class);
    }

    public void setCount(String nftName, int count) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(nftName)), new Update().set("count", count), TrackedNFT.class);
    }

    public TrackedNFT findByName(String name) {
        return trackedNFTRepository.findTrackedNFTByName(name);
    }

    public boolean containNFt(String name) {
        return findByName(name) != null;
    }

}
