package com.ajax.reverse.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public interface Message {

    String getMessage();

    void setMessage(String message);

    String getDate();

    void setDate(String date);

    ObjectId getObjectId();

    void setObjectId(ObjectId objectId);
    
    Channel getChannel();
    
    void setChannel(Channel channel);
}
