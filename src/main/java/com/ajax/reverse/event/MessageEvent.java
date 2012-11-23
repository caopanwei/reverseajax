package com.ajax.reverse.event;

import org.springframework.context.ApplicationEvent;

import com.ajax.reverse.domain.ChannelMessage;

public class MessageEvent extends ApplicationEvent {

    private static final long serialVersionUID = -8689894315996197723L;
    private ChannelMessage message;

    public MessageEvent(Object source, ChannelMessage message) {
        super(source);
        this.message = message;
    }

    public String getDate() {
        return message.getDate();
    }

    public String getMessage() {
        return message.getMessage();
    }

}
