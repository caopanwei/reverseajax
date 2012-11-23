package com.ajax.reverse.listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ajax.reverse.event.MessageEvent;

@Component
public class MessageEventListener implements ApplicationListener<MessageEvent> {

    private Collection<ScriptSession> sessionsByPage = new HashSet<ScriptSession>();

    public void onApplicationEvent(MessageEvent event) {
        WebContext webContext = WebContextFactory.get();

        ScriptBuffer scriptBuffer = new ScriptBuffer();
        scriptBuffer.appendCall("showMessage", event.getMessage(), event.getDate());

        if (webContext != null) {
            String currentPage = webContext.getCurrentPage();

            sessionsByPage = webContext.getScriptSessionsByPage(currentPage);
            broadcastMessage(scriptBuffer);
        } else {
            broadcastMessage(scriptBuffer);
        }
    }

    private void broadcastMessage(ScriptBuffer scriptBuffer) {
        for (Iterator<ScriptSession> iterator = sessionsByPage.iterator(); iterator.hasNext();) {
            ScriptSession session = (ScriptSession) iterator.next();
            session.addScript(scriptBuffer);
        }
    }

}
