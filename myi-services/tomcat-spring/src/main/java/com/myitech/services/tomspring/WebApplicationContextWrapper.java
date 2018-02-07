package com.myitech.services.tomspring;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by A.T on 2018/1/25.
 */
public class WebApplicationContextWrapper extends GenericWebApplicationContext{
    private AbstractApplicationContext applicationContext;

    public WebApplicationContextWrapper(AbstractApplicationContext applicationContext, ServletContext servletContext) {
        super((DefaultListableBeanFactory) applicationContext.getBeanFactory(), servletContext);
        this.applicationContext = applicationContext;
    }

    public void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
}


