package com.ajax.reverse.controller;

import java.lang.Thread.State;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ajax.reverse.service.ConsoleService;

@Controller
public class HomeController {
    @Autowired
    private ConsoleService consoleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        startConsolService();
        return "home";
    }

    private void startConsolService() {
        if (consoleService.getState() == State.NEW) {
            System.out.println("Console has started, from now on you can send messages by typing here");
            System.out.println("You can close the console by typing 'exit'");
            consoleService.start();
        }
    }

}
