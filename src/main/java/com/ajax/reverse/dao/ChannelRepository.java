package com.ajax.reverse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.ajax.reverse.domain.Channel;

@Repository
public class ChannelRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void createCollection() {
        if (!mongoTemplate.collectionExists(Channel.class)) {
            mongoTemplate.createCollection(Channel.class);
        }
    }

    public void dropCollection() {
        if (mongoTemplate.collectionExists(Channel.class)) {
            mongoTemplate.dropCollection(Channel.class);
        }
    }

    public void createChannel(String name) {
        mongoTemplate.save(new Channel(name));
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

}
