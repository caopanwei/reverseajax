package com.ajax.reverse.domain;

public class WrappedMessage {

    private String message;
    private String date;
    private String objectId;
    private String from;

    public WrappedMessage(Message message) {
        setMessage(message.getMessage());
        setDate(message.getDate());
        setObjectId(String.valueOf(message.getObjectId()));
        setFrom(message.getFrom());
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getFrom() {
        return from;
    }

}
