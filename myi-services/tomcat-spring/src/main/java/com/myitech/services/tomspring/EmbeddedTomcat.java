package com.myitech.services.tomspring;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.servlet.ServletException;

/**
 * Created by A.T on 2018/1/25.
 */
public class EmbeddedTomcat implements ApplicationContextAware{
    private ApplicationContext context;
    static final int port = 8080;
    private Tomcat tomcat;

    public void start() throws Exception {
        String SERVER_ROOT = Class.class.getResource("/webapp").getPath();
        String APP_ROOT = Class.class.getResource("/webapp").getPath();


        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(SERVER_ROOT);
        tomcat.getHost().setAutoDeploy(false);
        Context appContext = tomcat.addWebapp("/webapp", APP_ROOT);

//        appContext.addContainerListener();

        /**
         *  思考：如何建立 Spring ApplicationContext <=> Tomcat ServletContext <=> Spring Dispacher-servlet 三者间的联系?
         *
         *  1. EmbeddedTomcat 实现 ApplicationContextAware 接口来获取自身的 Spring ApplicationContext;
         *  2. DispacherServlet 通过 ServletContext 中的属性 WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE 来获取所需的 Spring ApplicationContext;
         *  3. GenericWebApplicationContext 集成了 ServletContext 与 Spring ApplicationContext 两者的功能;
         */
//        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ((AbstractApplicationContext)context).getBeanFactory();
//        GenericWebApplicationContext springApplicationContext = new GenericWebApplicationContext(beanFactory, appContext.getServletContext());
//        appContext.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, springApplicationContext);

        tomcat.start();
        tomcat.getServer().await();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
