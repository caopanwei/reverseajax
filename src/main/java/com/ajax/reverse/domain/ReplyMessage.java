package com.ajax.reverse.domain;

import java.util.Collection;

import org.bson.types.ObjectId;

public interface ReplyMessage {

    ObjectId getParent();

    void setParent(ObjectId parent);

    String getMessage();

    void setMessage(String message);

    String getDate();

    void setDate(String date);

    ObjectId getObjectId();

    void setObjectId(ObjectId objectId);

    String getFrom();

    void setFrom(String from);

    Collection<ReplyMessage> getReplies();

    void addReply(ReplyMessage message);
}
