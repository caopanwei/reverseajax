package com.ajax.reverse.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class ChannelMessage implements Message {

    @Id
    private ObjectId objectId;
    private String message;
    private String date;
    private String from;
    @DBRef
    @Indexed
    private Channel channel;

    public ChannelMessage(String from, String message) {
        super();
        setFrom(from);
        setMessage(message);
        setDate(new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date()));
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public ObjectId getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

}
