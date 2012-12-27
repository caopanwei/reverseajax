package com.ajax.reverse.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.ajax.reverse.strategy.PushBackStrategy;

@Component
public class PushBackService {

	@Autowired
	private Set<PushBackStrategy> strategies;
	private Collection<ScriptSession> sessions;

	public PushBackService() {
		strategies = new HashSet<PushBackStrategy>();
		sessions = new HashSet<ScriptSession>();
	}

	public void notifyTheBrowsers(ApplicationEvent event) {
		WebContext webContext = WebContextFactory.get();
		registerScriptSession(webContext);
		applyStrategies(event);
	}

	private void applyStrategies(ApplicationEvent event) {
		for (ScriptSession session : sessions) {
			for (PushBackStrategy strategy : strategies) {
				strategy.push(event, session);
			}
		}
	}

	private void registerScriptSession(WebContext webContext) {
		sessions = webContext.getAllScriptSessions();
	}

}
