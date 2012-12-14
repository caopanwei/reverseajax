package com.ajax.reverse.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ajax.reverse.dto.JaxMessageDto;
import com.ajax.reverse.exception.IllegalChannelNameException;
import com.ajax.reverse.jaxws.MessageWebServiceInterface;

@Controller
public class WebServiceController {

    @Resource(name = "wsClient")
    private MessageWebServiceInterface messageWebServiceInterface;

    @RequestMapping(value = "/webservice/send", method = RequestMethod.POST)
    public String sendMessageViaJAXWS(JaxMessageDto message) {
        messageWebServiceInterface.sendMessageToChannel(message.getChannel(), message.getFrom(), message.getMessage());
        return "redirect:/";
    }
    
    @ExceptionHandler(IllegalChannelNameException.class)
    public ModelAndView handleIllegalMessageException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        return modelAndView;
    }
}
