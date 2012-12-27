package com.ajax.reverse.strategy;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class NotificationStrategy implements PushBackStrategy {

	@Override
	public void push(ApplicationEvent event, ScriptSession session) {
		String currentPage = session.getPage();
		if(currentPage.equalsIgnoreCase("/reverse/")){
			ScriptBuffer scriptBuffer = new ScriptBuffer();
			scriptBuffer.appendCall("showNotification", "alma");
			session.addScript(scriptBuffer);
		}
	}
	
}
