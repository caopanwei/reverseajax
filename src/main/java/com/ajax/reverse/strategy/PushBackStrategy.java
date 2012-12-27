package com.ajax.reverse.strategy;

import org.directwebremoting.ScriptSession;
import org.springframework.context.ApplicationEvent;

public interface PushBackStrategy {

	void push(ApplicationEvent event, ScriptSession session);

}
