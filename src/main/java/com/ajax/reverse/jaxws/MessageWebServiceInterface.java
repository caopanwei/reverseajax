package com.ajax.reverse.jaxws;

public interface MessageWebServiceInterface {

    boolean sendMessageToChannel(String channelName, String from, String message);

}