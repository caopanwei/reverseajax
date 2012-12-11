package com.ajax.reverse.rss;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import com.ajax.reverse.domain.Message;
import com.ajax.reverse.service.ChannelService;
import com.ajax.reverse.service.MessageService;
import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.feed.atom.Person;

@Component
public class AtomRss extends AbstractAtomFeedView {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private MessageService messageService;

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String channel = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));

        feed.setId(url);
        feed.setTitle("rss for channel messages");

        model.put("messages", messageService.findMessagesByChannel(channelService.findByName(channel), 100, 0));
    }

    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Entry> entries = new ArrayList<Entry>();
        @SuppressWarnings("unchecked")
        Collection<Message> messages = (Collection<Message>) model.get("messages");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MM-dd HH:mm:ss");
        for (Message message : messages) {
            Entry item = new Entry();
            item.setTitle("message");
            item.setModified(simpleDateFormat.parse(message.getDate()));

            List<Person> authors = new ArrayList<Person>();
            Person author = new Person();
            author.setName(message.getFrom());
            authors.add(author);
            item.setAuthors(authors);

            Content content = new Content();
            content.setValue(message.getMessage());
            item.setContents(Arrays.asList(content));

            entries.add(item);
        }

        return entries;

    }

}
