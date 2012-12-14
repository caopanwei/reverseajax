package com.ajax.reverse.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.domain.ChannelMessage;
import com.ajax.reverse.domain.Message;
import com.ajax.reverse.service.ChannelService;
import com.ajax.reverse.service.MessageService;

@WebService(name = "messageWebService")
@Component
public class MessageWebService {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ChannelService channelService;

    @WebMethod(operationName = "sendMessageToChannel")
    public boolean sendMessageToChannel(@WebParam(name = "channel") String channelName, @WebParam(name = "from") String from,
            @WebParam(name = "message") String message) {
        Channel channel = channelService.findByName(channelName);
        if (channel != null) {
            Message channelMessage = new ChannelMessage(from, message);
            channelMessage.setChannel(channel);
            messageService.sendMessage(channelMessage);
            return true;
        }
        return false;
    }

}
