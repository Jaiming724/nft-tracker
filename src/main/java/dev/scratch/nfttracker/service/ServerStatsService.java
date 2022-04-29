package dev.scratch.nfttracker.service;

import dev.scratch.nfttracker.model.mongo.Destination;
import dev.scratch.nfttracker.model.mongo.ServerStats;
import dev.scratch.nfttracker.repositories.ServerStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ServerStatsService {
    private final ServerStatsRepository serverStatsRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ServerStatsService(ServerStatsRepository serverStatsRepository, MongoTemplate mongoTemplate) {
        this.serverStatsRepository = serverStatsRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void insert(ServerStats serverStats) {
        serverStatsRepository.insert(serverStats);
    }

    public ServerStats getCount(String serverID) {
        return serverStatsRepository.findServerStatsByServerID(serverID);
    }

    public void addNFT(String nftName, String serverID, Destination server) {
        int count = getCount(serverID).getCount();
        mongoTemplate.updateFirst(Query.query(Criteria.where("server_id").is(serverID)), new Update().set("count", count + 1), ServerStats.class);

        mongoTemplate.updateFirst(Query.query(Criteria.where("server_id").is(serverID)), new Update().push(nftName, server), ServerStats.class);
    }
}
