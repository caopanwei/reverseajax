package com.ajax.reverse.domain;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class ChannelReplyMessage implements ReplyMessage {

    @Id
    private ObjectId objectId;
    private ObjectId parent;
    private String message;
    private String date;
    private String from;
    private Collection<ReplyMessage> replies;

    public ChannelReplyMessage(ObjectId parent, String from, String message) {
        super();
        setFrom(from);
        setParent(parent);
        setMessage(message);
        setDate(new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date()));
        replies = new ConcurrentLinkedQueue<ReplyMessage>();
        objectId = new ObjectId(); //embedded object
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
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public Collection<ReplyMessage> getReplies() {
        return replies;
    }

    @Override
    public void addReply(ReplyMessage message) {
        replies.add(message);
    }

    @Override
    public ObjectId getParent() {
        return parent;
    }

    @Override
    public void setParent(ObjectId parent) {
        this.parent = parent;
    }

}
