package com.ajax.reverse.service;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RemoteProxy(name = "DwrService")
public class DwrService {

    @Autowired
    private MessageService messageService;

    @RemoteMethod
    public void sendMessage(String from, String message) {
        messageService.sendMessage(from, message);
    }
    
    @RemoteMethod
    public void sendTemporaryMessage(String from, String message) {
        messageService.sendTemporaryMessage(from, message);
    }

}
