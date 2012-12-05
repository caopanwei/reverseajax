package com.ajax.reverse.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.ajax.reverse.domain.Message;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Item;

@Component("rssService")
public class RssService extends AbstractRssFeedView {

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        feed.setTitle(model.get("channel") + " channel's messages");
        feed.setDescription("rss for channel messages");
        feed.setLink(model.get("link").toString());
        super.buildFeedMetadata(model, feed, request);
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Item> items = new ArrayList<Item>();
        @SuppressWarnings("unchecked")
        Collection<Message> messages = (Collection<Message>) model.get("messages");
        Collections.reverse((List<?>) messages);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MM-dd HH:mm:ss");
        for (Message message : messages) {
            Item item = new Item();
            item.setTitle("message");
            item.setAuthor(message.getFrom());
            item.setPubDate(simpleDateFormat.parse(message.getDate()));

            Content content = new Content();
            content.setValue(message.getMessage());
            item.setContent(content);

            items.add(item);
        }
        return items;
    }

}
