package com.ajax.reverse.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.ajax.reverse.dao.MessageRepository;
import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.domain.ChannelMessage;
import com.ajax.reverse.domain.ChannelTemporaryMessage;
import com.ajax.reverse.domain.Message;
import com.ajax.reverse.event.MessageEvent;

@Service
public class MessageService implements ApplicationEventPublisherAware {

    @Autowired
    private MessageRepository messageRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void sendMessage(String from, String message) {
        publishEvent(new MessageEvent(this, new ChannelMessage(from, message)));
    }

    public void sendTemporaryMessage(String from, String message) {
        publishEvent(new MessageEvent(this, new ChannelTemporaryMessage(from, message)));
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Collection<Message> findMessagesByChannel(Channel channel, int messageLimit, int skip) {
        return messageRepository.findMessagesByChannel(channel, messageLimit, skip);
    }

    public Collection<Message> findMessagesByChannel(Channel channel) {
        return messageRepository.findMessagesByChannel(channel);
    }

    public long countByChannel(Channel channel) {
        return messageRepository.countByChannel(channel);
    }

    public void remove(Message message) {
        messageRepository.remove(message);
    }

    private void publishEvent(MessageEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

}
