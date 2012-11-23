package com.ajax.reverse.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChannelMessage {

    @Id
    private ObjectId objectId = ObjectId.get();
    private String message;
    private String date;

    public ChannelMessage(String message) {
        super();
        setMessage(message);
        setDate(new SimpleDateFormat("MMMM dd hh:mm:ss").format(new Date()));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

}
