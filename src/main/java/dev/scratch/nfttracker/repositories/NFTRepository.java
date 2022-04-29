package dev.scratch.nfttracker.repositories;

import dev.scratch.nfttracker.model.mongo.NFTMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NFTRepository extends MongoRepository<NFTMongo, String> {
    NFTMongo findNFTMongoByName(String name);
}
