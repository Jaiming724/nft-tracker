package dev.scratch.nfttracker.repositories;

import dev.scratch.nfttracker.model.mongo.Destination;
import dev.scratch.nfttracker.model.mongo.ServerStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerStatsRepository extends MongoRepository<ServerStats, String> {
    ServerStats findServerStatsByServerID(String id);

}
