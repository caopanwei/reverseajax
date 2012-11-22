package com.ajax.reverse.listener;

import java.util.Collection;
import java.util.Iterator;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ajax.reverse.event.MessageEvent;

@Component
public class MessageEventListener implements ApplicationListener<MessageEvent>{

    public void onApplicationEvent(MessageEvent event) {
        WebContext webContext = WebContextFactory.get();
        String currentPage = webContext.getCurrentPage();
        
        ScriptBuffer scriptBuffer = new ScriptBuffer();
        scriptBuffer.appendCall("showMessage", event.getMessage());
        
        Collection<ScriptSession> sessionsByPage = webContext.getScriptSessionsByPage(currentPage);
        for (Iterator<ScriptSession> iterator = sessionsByPage.iterator(); iterator.hasNext(); ) {
            ScriptSession session = (ScriptSession) iterator.next();
            session.addScript(scriptBuffer);
        }        
    }

}
