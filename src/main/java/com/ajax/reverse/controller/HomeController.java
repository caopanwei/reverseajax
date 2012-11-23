package com.ajax.reverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ajax.reverse.service.ChannelService;

@Controller
public class HomeController {

    @Autowired
    private ChannelService channelService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("channelList", channelService.getChannelList());
        return "home";
    }

    @RequestMapping(value = "/{channel}", method = RequestMethod.GET)
    public String showChannel(@PathVariable String channel) {
        if (channelService.doesTheChannelExist(channel)) {
            return "chat";
        } else {
            return "redirect:/temporary/" + channel;
        }
    }

    @RequestMapping(value = "/temporary/{channel}", method = RequestMethod.GET)
    public String showTemporaryChannel(@PathVariable String channel) {
        return "chat_temp";
    }

}
