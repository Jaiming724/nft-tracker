package dev.scratch.nfttracker.repositories;

import dev.scratch.nfttracker.model.mongo.TrackedNFT;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackedNFTRepository extends MongoRepository<TrackedNFT, String> {
    TrackedNFT findTrackedNFTByName(String name);
}
