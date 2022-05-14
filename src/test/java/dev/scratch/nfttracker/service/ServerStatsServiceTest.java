//package dev.scratch.nfttracker.service;
//
//import dev.scratch.nfttracker.model.mongo.Destination;
//import dev.scratch.nfttracker.model.mongo.ServerStats;
//import dev.scratch.nfttracker.repositories.ServerStatsRepository;
//import org.junit.Ignore;
//import org.junit.jupiter.api.*;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@DataMongoTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class ServerStatsServiceTest {
//
//    @Autowired
//    ServerStatsRepository serverStatsRepository;
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    ServerStatsService serverStatsService;
//    ServerStats serverStats;
//
//    @BeforeEach
//    public void setUp() {
//        serverStatsService = new ServerStatsService(serverStatsRepository, mongoTemplate);
//        serverStats = new ServerStats();
//        serverStats.setServerID("testingID")
//                .setCount(4);
//    }
//
//
//    @Order(1)
//    void insert() {
//        serverStatsService.deleteAll();
//
//        serverStatsService.insert(serverStats);
//        assertEquals(serverStatsService.findByID("testingID").getCount(), 4);
//        serverStatsService.deleteAll();
//
//    }
//
//    @Order(2)
//    void addNFT() {
//
//        serverStatsService.insert(serverStats);
//
//        Destination destination = new Destination("roleId", "testingID", "channelID").setNftName("nftName");
//        serverStatsService.addNFT("testingID", destination);
//        assertEquals(serverStatsService.findByID("testingID").getCount(), 5);
//        assertTrue(serverStatsService.containsNFT("nftName", "testingID", "channelID"));
//
//        serverStatsService.removeNFT("nftName", "testingID", "channelID");
//        assertEquals(serverStatsService.findByID("testingID").getCount(), 4);
//        assertFalse(serverStatsService.containsNFT("nftName", "testingID", "channelID"));
//    }
//
//}