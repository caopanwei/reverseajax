package com.ajax.reverse.dao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.domain.Message;
import com.ajax.reverse.service.ChannelService;

@Repository
public class MessageRepository {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ChannelService channelService;

    public void createCollection() {
        if (!mongoTemplate.collectionExists(Message.class)) {
            mongoTemplate.createCollection(Message.class);
        }
    }

    public void dropCollection() {
        if (mongoTemplate.collectionExists(Message.class)) {
            mongoTemplate.dropCollection(Message.class);
        }
    }

    public void save(Message message) {
        mongoTemplate.save(message);
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ChannelService getChannelService() {
        return channelService;
    }

    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    public Collection<Message> findMessagesByChannel(Channel channel, int messageLimit, int skip) {
        Query query = new Query(Criteria.where("channel.$id").is(channel.getName())).limit(messageLimit).with(new Sort(Direction.DESC, "date")).skip(skip);
        return mongoTemplate.find(query, Message.class);
    }

}
