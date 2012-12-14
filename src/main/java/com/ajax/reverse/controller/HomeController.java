package com.ajax.reverse.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.domain.Message;
import com.ajax.reverse.domain.WrappedMessage;
import com.ajax.reverse.dto.JaxMessageDto;
import com.ajax.reverse.exception.IllegalChannelNameException;
import com.ajax.reverse.service.ChannelService;
import com.ajax.reverse.service.MessageService;

@Controller
public class HomeController {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private MessageService messageService;
    private static final int MESSAGE_LIMIT = 10;

    @ModelAttribute("message")
    public JaxMessageDto getJaxMessageDto() {
        return new JaxMessageDto();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        Collection<Channel> channelList = channelService.getChannelList();
        for (Channel channel : channelList) {
            channel.setName(HtmlUtils.htmlUnescape(channel.getName()));
            channel.setName(HtmlUtils.htmlEscape(channel.getName()));
        }
        model.addAttribute("channelList", channelList);
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

    @RequestMapping(value = "/temporary/{channel}", method = RequestMethod.GET)
    public String showTemporaryChannel(@PathVariable String channel) {
        return "chat_temp";
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/more", method = RequestMethod.POST)
    public Collection<WrappedMessage> loadMoreMessages(@PathVariable String channel, @RequestParam int skip) throws InterruptedException {
        Thread.sleep(2000);
        Collection<Message> messagesByChannel = messageService.findMessagesByChannel(channelService.findByName(channel), MESSAGE_LIMIT, skip);
        htmlEscapeMessages(messagesByChannel);
        return wrapMessages(messagesByChannel);
    }

    @RequestMapping(value = "/{channel}/rss", method = RequestMethod.GET)
    public String showRss(@PathVariable String channel, Model model, HttpServletRequest request) {
        model.addAttribute("url", request.getRequestURL().toString());
        model.addAttribute("channel", channel);
        return "rssService";
    }

    private Collection<WrappedMessage> wrapMessages(Collection<Message> messagesByChannel) {
        Collection<WrappedMessage> wrappedMessages = new ArrayList<WrappedMessage>();
        for (Message msg : messagesByChannel) {
            wrappedMessages.add(new WrappedMessage(msg));
        }
        return wrappedMessages;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewChannel(@RequestParam String channel, HttpServletRequest request) {
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdbmdkSAAAAAFo_sUAlShyOY3qioTZPYIp-PKzU");
        String remoteAddr = request.getRemoteAddr();
        String challengeField = request.getParameter("recaptcha_challenge_field");
        String responseField = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challengeField, responseField);
        if (reCaptchaResponse.isValid()) {
            channelService.create(channel);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/{channel}/remove", method = RequestMethod.POST)
    public String removeChannel(@PathVariable String channel) {
        channelService.delete(channelService.findByName(channel));
        return "redirect:/";
    }

    private void htmlEscapeMessages(Collection<Message> messagesByChannel) {
        for (Message message : messagesByChannel) {
            message.setMessage(HtmlUtils.htmlEscape(message.getMessage()));
            message.setFrom(HtmlUtils.htmlEscape(message.getFrom()));
            message.setDate(HtmlUtils.htmlEscape(message.getDate()));
        }
    }

    private void addChannelMessagesToModel(String channel, Model model) {
        Collection<Message> messagesByChannel = messageService.findMessagesByChannel(channelService.findByName(channel), MESSAGE_LIMIT, 0);
        htmlEscapeMessages(messagesByChannel);
        model.addAttribute("messages", messagesByChannel);
    }

    @ExceptionHandler(IllegalChannelNameException.class)
    public ModelAndView handleIllegalMessageException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        return modelAndView;
    }

}
