package com.ajax.reverse.strategy;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.ajax.reverse.event.MessageEvent;

@Component
public class MessageStrategy implements PushBackStrategy {

	@Override
	public void push(ApplicationEvent event, ScriptSession session) {
		String currentPage = session.getPage();
		if (!currentPage.equalsIgnoreCase("/reverse/")) {
			MessageEvent messageEvent = (MessageEvent) event;
			ScriptBuffer scriptBuffer = new ScriptBuffer();
			scriptBuffer.appendCall("showMessage", htmlEscape(messageEvent
					.getFrom()), htmlEscape(messageEvent.getTextMessage()),
					htmlEscape(messageEvent.getDate()), htmlEscape(String
							.valueOf(messageEvent.getMessage().getObjectId())));
			session.addScript(scriptBuffer);
		}

	}

	private String htmlEscape(String string) {
		return HtmlUtils.htmlEscape(string);
	}

}
