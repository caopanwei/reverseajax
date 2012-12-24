package com.ajax.reverse.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ajax.reverse.dto.JaxMessageDto;
import com.ajax.reverse.jaxws.MessageWebServiceInterface;

@Controller
public class WebServiceController {

	@Resource(name = "wsClient")
	private MessageWebServiceInterface messageWebServiceInterface;

	@RequestMapping(value = "/webservice/send", method = RequestMethod.POST)
	public String sendMessageViaJAXWS(JaxMessageDto message) {
		messageWebServiceInterface.sendMessageToChannel(message.getChannel(),
				message.getFrom(), message.getMessage());
		return "redirect:/";
	}

}
