package com.ajax.reverse.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Channel {

    @Id
    private String name;
    @DBRef
    private Collection<ChannelMessage> messages;

    public Channel(String name) {
        this.name = name;
        messages = new ArrayList<ChannelMessage>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ChannelMessage> getMessages() {
        return messages;
    }

    public void setMessages(Collection<ChannelMessage> messages) {
        this.messages = messages;
    }

}
