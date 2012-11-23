package com.ajax.reverse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ajax.reverse.domain.Message;
import com.ajax.reverse.service.ChannelService;
import com.ajax.reverse.service.MessageService;

@Controller
public class HomeController {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private MessageService messageService;
    private static final int MESSAGE_LIMIT = 12;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("channelList", channelService.getChannelList());
        return "home";
    }

    @RequestMapping(value = "/{channel}", method = RequestMethod.GET)
    public String showChannel(@PathVariable String channel, Model model) {
        if (channelService.doesTheChannelExist(channel)) {
            model.addAttribute("channel", channelService.findByName(channel));
            addChannelMessagesToModel(channel, model);
            return "chat";
        } else {
            return "redirect:/temporary/" + channel;
        }
    }

    private void addChannelMessagesToModel(String channel, Model model) {
        model.addAttribute("messages", messageService.findMessagesByChannel(channelService.findByName(channel), MESSAGE_LIMIT, 0));
    }

    @RequestMapping(value = "/temporary/{channel}", method = RequestMethod.GET)
    public String showTemporaryChannel(@PathVariable String channel) {
        return "chat_temp";
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/more", method = RequestMethod.POST)
    public List<Message> loadMoreMessages(@PathVariable String channel) {
        return (List<Message>) messageService.findMessagesByChannel(channelService.findByName(channel), MESSAGE_LIMIT, MESSAGE_LIMIT);
    }

}
