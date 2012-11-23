package com.ajax.reverse.listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ajax.reverse.domain.Message;
import com.ajax.reverse.event.MessageEvent;
import com.ajax.reverse.service.ChannelService;
import com.ajax.reverse.service.MessageService;

@Component
public class MessageEventListener implements ApplicationListener<MessageEvent> {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private MessageService messageService;
    private Collection<ScriptSession> sessionsByPage = new HashSet<ScriptSession>();

    public void onApplicationEvent(MessageEvent event) {
        WebContext webContext = WebContextFactory.get();
        ScriptBuffer scriptBuffer = new ScriptBuffer();
        scriptBuffer.appendCall("showMessage", event.getTextMessage(), event.getDate());
        if (webContext != null) {
            String currentPage = webContext.getCurrentPage();
            saveMessageToDatabase(event.getMessage(), currentPage.substring(currentPage.lastIndexOf("/") + 1));
            sessionsByPage = webContext.getScriptSessionsByPage(currentPage);
            broadcastMessage(scriptBuffer);
        } else {
            broadcastMessage(scriptBuffer);
        }
    }

    private void saveMessageToDatabase(Message message, String channel) {
        message.setChannel(channelService.findByName(channel));
        messageService.save(message);
    }

    private void broadcastMessage(ScriptBuffer scriptBuffer) {
        for (Iterator<ScriptSession> iterator = sessionsByPage.iterator(); iterator.hasNext();) {
            ScriptSession session = (ScriptSession) iterator.next();
            session.addScript(scriptBuffer);
        }
    }

}
