package com.ajax.reverse.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.ajax.reverse.event.MessageEvent;

@Service
public class ConsoleService extends Thread implements ApplicationEventPublisherAware {

    private volatile boolean exit;
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (!exit) {
            try {
                String message = bufferedReader.readLine();
                if ("exit".equalsIgnoreCase(message)) {
                    exit = true;
                } else {
                    System.out.println("Publishing message: " + message);
                    applicationEventPublisher.publishEvent(new MessageEvent(this, message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Console is closed");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

}
