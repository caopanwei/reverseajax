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
import org.springframework.web.util.HtmlUtils;

import com.ajax.reverse.annotation.Trace;
import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.domain.ChannelMessage;
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

    @Trace
    public void onApplicationEvent(MessageEvent event) {
        ScriptBuffer scriptBuffer = new ScriptBuffer();
        WebContext webContext = WebContextFactory.get();
        String currentPage = registerScriptSession(webContext);
        saveMessage(event, currentPage);
        addJavaScriptAction(event, scriptBuffer);
        broadcastMessage(scriptBuffer);
    }

    private void addJavaScriptAction(MessageEvent event, ScriptBuffer scriptBuffer) {
        scriptBuffer.appendCall("showMessage", htmlEscape(event.getFrom()), htmlEscape(event.getTextMessage()), htmlEscape(event.getDate()),
                htmlEscape(String.valueOf(event.getMessage().getObjectId())));
    }

    private void saveMessage(MessageEvent event, String currentPage) {
        if (currentPage != null) {
            saveMessageToDatabase(event.getMessage(), currentPage.substring(currentPage.lastIndexOf("/") + 1));
        } else if (event.getMessage().getChannel() != null) {
            saveMessageToDatabase(event.getMessage());
        }
    }

    private String registerScriptSession(WebContext webContext) {
        String currentPage = null;
        if (webContext != null) {
            currentPage = webContext.getCurrentPage();
            sessionsByPage = webContext.getScriptSessionsByPage(currentPage);
        }
        return currentPage;
    }

    private String htmlEscape(String string) {
        return HtmlUtils.htmlEscape(string);
    }

    private void saveMessageToDatabase(Message message) {
        if (message instanceof ChannelMessage) {
            messageService.save(message);
        }
    }

    private void saveMessageToDatabase(Message message, String channel) {
        if (message instanceof ChannelMessage) {
            Channel channel2 = channelService.findByName(channel);
            if (channel2 != null) {
                message.setChannel(channel2);
                messageService.save(message);
            }
        }
    }

    private void broadcastMessage(ScriptBuffer scriptBuffer) {
        for (Iterator<ScriptSession> iterator = sessionsByPage.iterator(); iterator.hasNext();) {
            ScriptSession session = (ScriptSession) iterator.next();
            session.addScript(scriptBuffer);
        }
    }

}
