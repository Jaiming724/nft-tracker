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

import static org.springframework.data.mongodb.core.query.Criteria.where;


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

    public int getCount(String serverID) {
        return serverStatsRepository.findServerStatsByServerID(serverID).getCount();
    }

    public void addNFT(String serverID, Destination server) {
        int count = getCount(serverID);
        mongoTemplate.updateFirst(Query.query(where("serverID").is(serverID)), new Update().set("count", count + 1), ServerStats.class);

        mongoTemplate.updateFirst(Query.query(where("serverID").is(serverID)), new Update().push("destinations", server), ServerStats.class);
    }

    public void removeNFT(String nftName, String serverID, String channelID) {
        int count = getCount(serverID);
        mongoTemplate.updateFirst(Query.query(where("serverID").is(serverID)), new Update().set("count", count - 1), ServerStats.class);

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("channel_id").is(channelID),
                Criteria.where("nftName").is(nftName)
        );
        Query query = new Query(criteria);
        mongoTemplate.updateMulti(new Query(where("serverID").is(serverID)), new Update().pull("destinations", query), ServerStats.class);

    }

    public ServerStats findByID(String id) {
        return serverStatsRepository.findServerStatsByServerID(id);
    }

    public boolean containsServer(String id) {
        return findByID(id) != null;
    }

    public boolean containsNFT(String nftName, String serverID, String channelID) {
        ServerStats serverStats = serverStatsRepository.findServerStatsByServerID(serverID);
        if (serverStats != null) {
            for (Destination destination : serverStats.getDestinations()) {
                if (destination.getNftName().equals(nftName) && destination.getChannelID().equals(channelID)) {
                    System.out.println(destination.getNftName());
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteAll() {
        serverStatsRepository.deleteAll();

    }
}
