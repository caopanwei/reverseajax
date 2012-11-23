package com.ajax.reverse.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationEvent;

public class MessageEvent extends ApplicationEvent {

    private static final long serialVersionUID = -8689894315996197723L;
    private String message;
    private SimpleDateFormat date = new SimpleDateFormat("MMMM dd hh:mm:ss");
    private String dateString;

    public MessageEvent(Object source, String message) {
        super(source);
        this.message = message;
        dateString = date.format(new Date());
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
