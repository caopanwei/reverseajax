package com.ajax.reverse.listener;

import org.directwebremoting.WebContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ajax.reverse.annotation.Trace;
import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.domain.ChannelMessage;
import com.ajax.reverse.domain.Message;
import com.ajax.reverse.event.MessageEvent;
import com.ajax.reverse.service.ChannelService;
import com.ajax.reverse.service.MessageService;
import com.ajax.reverse.service.PushBackService;

@Component
public class MessageEventListener implements ApplicationListener<MessageEvent> {

	@Autowired
	private ChannelService channelService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private PushBackService pushBackService;

	@Trace
	public void onApplicationEvent(MessageEvent event) {
		saveMessage(event, getCurrentPage());
		pushBackService.notifyTheBrowsers(event);
	}

	private String getCurrentPage() {
		return WebContextFactory.get() == null ? null : WebContextFactory.get()
				.getCurrentPage();
	}

	private void saveMessage(MessageEvent event, String currentPage) {
		if (currentPage != null) {
			saveMessageToDatabase(event.getMessage(),
					currentPage.substring(currentPage.lastIndexOf("/") + 1));
		} else if (event.getMessage().getChannel() != null) {
			saveMessageToDatabase(event.getMessage());
		}
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

}
