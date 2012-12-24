package com.ajax.reverse.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.ajax.reverse.exception.IllegalChannelNameException;

@ControllerAdvice
public class GlobalExceptionHandlers {

	@ExceptionHandler(IllegalChannelNameException.class)
	public ModelAndView handleIllegalMessageException(Exception exception) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("errorMessage", exception.getMessage());
		return modelAndView;
	}

}
