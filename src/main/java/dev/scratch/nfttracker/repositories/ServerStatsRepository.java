package dev.scratch.nfttracker.repositories;

import dev.scratch.nfttracker.model.mongo.ServerStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerStatsRepository extends MongoRepository<ServerStats, String> {
    ServerStats findServerStatsByServerID(String id);
}
