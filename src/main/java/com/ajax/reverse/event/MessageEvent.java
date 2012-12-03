package com.ajax.reverse.event;

import org.springframework.context.ApplicationEvent;

import com.ajax.reverse.domain.Message;

public class MessageEvent extends ApplicationEvent {

    private static final long serialVersionUID = -8689894315996197723L;
    private Message message;

    public MessageEvent(Object source, Message message) {
        super(source);
        this.message = message;
    }

    public String getDate() {
        return message.getDate();
    }

    public String getTextMessage() {
        return message.getMessage();
    }

    public Message getMessage() {
        return message;
    }

    public String getFrom() {
        return message.getFrom();
    }

}
