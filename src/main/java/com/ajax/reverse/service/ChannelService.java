package com.ajax.reverse.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajax.reverse.dao.ChannelRepository;
import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.domain.Message;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private MessageService messageService;

    public Collection<Channel> getChannelList() {
        return channelRepository.getAll();
    }

    public boolean doesTheChannelExist(String name) {
        return findByName(name) == null ? false : true;
    }

    public void save(Channel channel) {
        channelRepository.save(channel);
    }

    public Channel findByName(String name) {
        return channelRepository.findByName(name);
    }
    
    public Channel create(String name){
        return channelRepository.create(name);
    }

    public void delete(Channel channel) {
        deleteMessagesForChannel(channel);
        channelRepository.delete(channel);
    }

    private void deleteMessagesForChannel(Channel channel) {
        Collection<Message> collection = messageService.findMessagesByChannel(channel);
        for(Message message : collection){
            messageService.remove(message);
        }
    }

}
