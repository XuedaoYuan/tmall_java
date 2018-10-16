package com.tmall.springboot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class OnlineCount implements HttpSessionListener {
    public Integer count = 0;
    /**
     * Notification that a session was created.
     *
     * @param se
     *            the notification event
     */
    @Override
    public void sessionCreated(HttpSessionEvent se){
        System.out.println("sessionCreated");
        count++;
        se.getSession().getServletContext().setAttribute("Count", count);
    }

    /**
     * Notification that a session is about to be invalidated.
     *
     * @param se
     *            the notification event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se){
        System.out.println("sessionDestroyed");
        count--;
        se.getSession().getServletContext().setAttribute("Count", count);
    }
}
