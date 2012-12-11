package com.ajax.reverse.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.ajax.reverse.annotation.CheckMessage;
import com.ajax.reverse.exception.IllegalMessageException;

@Aspect
@Component
public class CheckMessageAspect {

    @Around(value = "@annotation(checkMessage)", argNames = "checkMessage")
    public void check(ProceedingJoinPoint joinPoint, CheckMessage checkMessage) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String from = (String) args[0];
        String message = (String) args[1];
        if (doesItContainIllegalStrings(from) || doesItContainIllegalStrings(message)) {
            throw new IllegalMessageException("The message or the name contains illegal characters");
        } else {
            joinPoint.proceed(args);
        }
    }

    private boolean doesItContainIllegalStrings(String string) {
        return !(HtmlUtils.htmlEscape(string).equalsIgnoreCase(string));
    }

}
