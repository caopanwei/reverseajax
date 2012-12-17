package com.ajax.reverse.listener;

import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.ajax.reverse.dao.ChannelRepository;
import com.ajax.reverse.dao.MessageRepository;
import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.domain.ChannelMessage;
import com.mongodb.Mongo;

/**
 * Application Lifecycle Listener implementation class InitializerListener
 *
 */
@WebListener
public class InitializerListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            MongoTemplate mongoTemplate = new MongoTemplate(new Mongo("localhost"), "reverseajax");
            ChannelRepository channelRepository = new ChannelRepository();
            channelRepository.setMongoTemplate(mongoTemplate);
            channelRepository.dropCollection();
            channelRepository.createCollection();
            Channel channel1 = channelRepository.create("default");
            Channel channel2 = channelRepository.create("test");
            MessageRepository messageRepository = new MessageRepository();
            messageRepository.setMongoTemplate(mongoTemplate);
            messageRepository.dropCollection();
            messageRepository.createCollection();
            for (int i = 0; i < 30; i++) {
                ChannelMessage message = new ChannelMessage("some1", String.valueOf(i));
                message.setChannel(channel1);
                messageRepository.save(message);
            }
            for (int i = 30; i < 60; i++) {
                ChannelMessage message = new ChannelMessage("any1", String.valueOf(i));
                message.setChannel(channel2);
                messageRepository.save(message);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent arg0) {
    }

}
