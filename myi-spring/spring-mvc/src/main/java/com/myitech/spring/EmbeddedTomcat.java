package com.myitech.spring;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * Created by A.T on 2018/1/26.
 */
public class EmbeddedTomcat {
    public void start() throws Exception {
        Tomcat tomcat = new Tomcat();

        String SERVER_ROOT = Class.class.getResource("/webroot").getPath();
        System.out.println("Server Root :" + SERVER_ROOT);
        tomcat.setBaseDir(SERVER_ROOT); // basedir The Tomcat base folder on which all others will be derived
        tomcat.setPort(8080);
        tomcat.getHost().setAppBase(".");
        /**
         *  @param contextPath The context mapping to use, "" for root context.
         *  @param docBase Base directory for the context, for static files.Must exist, relative to the server home
         */
        tomcat.addWebapp("/demo", SERVER_ROOT+"/demoapp");
        tomcat.start();
        tomcat.getServer().await();
    }
}
