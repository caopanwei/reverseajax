package com.ajax.reverse.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.HtmlUtils;

import com.ajax.reverse.exception.IllegalChannelNameException;

public class ChannalNameInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String channel = request.getParameter("channel");
        if (channel != null) {
            if (doesItContainIllegalCharacters(channel) || channel.trim().length() < 1) {
                throw new IllegalChannelNameException("Channal name does not meet the requirements");
            }
        }
        return super.preHandle(request, response, handler);
    }

    private boolean doesItContainIllegalCharacters(String channel) {
        return !(HtmlUtils.htmlEscape(channel).equalsIgnoreCase(channel));
    }

}
