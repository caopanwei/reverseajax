package com.ajax.reverse.strategy;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.ajax.reverse.event.MessageEvent;

@Component
public class NotificationStrategy implements PushBackStrategy {

	@Override
	public void push(ApplicationEvent event, ScriptSession session) {
		String currentPage = session.getPage();
		if (currentPage.lastIndexOf("/") == currentPage.length() - 1) {
			MessageEvent messageEvent = (MessageEvent) event;
			ScriptBuffer scriptBuffer = new ScriptBuffer();
			if (doesChannelExist(messageEvent)) {
				scriptBuffer.appendCall("showNotification", messageEvent
						.getMessage().getChannel().getName());
				session.addScript(scriptBuffer);
			}
		}
	}

	private boolean doesChannelExist(MessageEvent messageEvent) {
		return messageEvent.getMessage().getChannel() != null;
	}

}
