package com.ajax.reverse.listener;

import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.ajax.reverse.dao.ChannelRepository;
import com.ajax.reverse.dao.MessageRepository;
import com.mongodb.Mongo;

/**
 * Application Lifecycle Listener implementation class InitializerListener
 *
 */
public class InitializerListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            MongoTemplate mongoTemplate = new MongoTemplate(new Mongo("localhost"), "reverseajax");
            ChannelRepository channelRepository = new ChannelRepository();
            channelRepository.setMongoTemplate(mongoTemplate);
            channelRepository.dropCollection();
            channelRepository.createCollection();
            channelRepository.create("default");
            MessageRepository messageRepository = new MessageRepository();
            messageRepository.setMongoTemplate(mongoTemplate);
            messageRepository.dropCollection();
            messageRepository.createCollection();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent arg0) {
    }

}
