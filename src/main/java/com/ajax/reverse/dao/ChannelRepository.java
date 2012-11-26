package com.ajax.reverse.dao;

import java.util.Collection;

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

    public Collection<Channel> getAll() {
        return mongoTemplate.findAll(Channel.class);
    }

    public Channel create(String name) {
        Channel channel = new Channel(name);
        save(channel);
        return channel;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Channel findByName(String name) {
        return mongoTemplate.findById(name, Channel.class);
    }

    public void save(Channel channel) {
        mongoTemplate.save(channel);
    }

}
