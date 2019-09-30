package com.cn.company.config;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.mongo.eventsourcing.eventstore.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Configuration
public class CommandRepositoryConfiguration {

    @Value("${spring.data.mongodb.url}")
    private String mongoUrl;

    @Value("${spring.data.mongodb.dbname}")
    private String mongoDbName;

    @Value("${spring.data.mongodb.events.collection.name}")
    private String eventsCollectionName;

    @Value("${spring.data.mongodb.events.snapshot.collection.name}")
    private String snapshotCollectionName;

    @Bean
    public Serializer axonJsonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    public EventStorageEngine eventStorageEngine(){
        return new MongoEventStorageEngine(
                axonJsonSerializer(),null, axonMongoTemplate(), new DocumentPerEventStorageStrategy());
    }

    @Bean(name = "axonMongoTemplate")
    public MongoTemplate axonMongoTemplate() {
        MongoTemplate template = new DefaultMongoTemplate(mongoClient(), mongoDbName, eventsCollectionName, snapshotCollectionName);
        return template;
    }

    @Bean
    public MongoClient mongoClient(){
        MongoFactory mongoFactory = new MongoFactory();
        mongoFactory.setMongoAddresses(Arrays.asList(new ServerAddress(mongoUrl)));
        return mongoFactory.createMongo();
    }

    @Bean
    public SagaStore sagaStore(){
        org.axonframework.mongo.eventhandling.saga.repository.MongoTemplate mongoTemplate =
                new org.axonframework.mongo.eventhandling.saga.repository.DefaultMongoTemplate(mongoClient());
        return new MongoSagaStore(mongoTemplate, axonJsonSerializer());
    }
}
