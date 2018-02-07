package com.myitech.services.tomcat;

import com.myitech.services.tomcat.servlets.HelloServlet;
import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;

/**
 * Created by A.T on 2018/1/25.
 */
public class EmbedHttpServer {
    static final int port = 8080;
    static final String SERVER_ROOT = Class.class.getResource("/webroot").getPath();
    static final String APP_ROOT = Class.class.getResource("/webapps").getPath();
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(SERVER_ROOT);
        tomcat.getHost().setAutoDeploy(false);

        String contextPath = ""; // do not use "/"
        StandardContext context = new StandardContext();
        context.setPath(contextPath); // Set the context path for this Context.
        context.setDocBase(SERVER_ROOT); // Set the document root for this Context.
        context.addLifecycleListener(new Tomcat.FixContextListener());
        tomcat.getHost().addChild(context);

        /**
         *  Add a self developed servlet: HelloServlet
         *
         *      http://localhost:8080/hello
         */
        tomcat.addServlet(contextPath, "helloServlet", new HelloServlet());
        context.addServletMappingDecoded("/hello", "helloServlet");

        /**
         *  Use DefaultServlet for fetching static resources ：
         *
         *      http://localhost:8080/static/js/hello.js
         */
        tomcat.addServlet(contextPath, "defaultServlet", new DefaultServlet());
        context.addServletMappingDecoded("/static/*", "defaultServlet");


        /**
         * 1. Add a web app, then tomcat will auto-load all web apps under webroot.
         * 2. According to Servlet3.0+ specification, jar with service implement ServletContainerInitializer will be added
         * to all web apps !!!!!
         *
         *  http://localhost:8080/app/hi
         *  http://localhost:8080/app/spring
         *  http://localhost:8080/demo/hi
         *
         * @see SpringAppConfig
         */
        tomcat.addWebapp("/app", APP_ROOT+"/app");
        tomcat.addWebapp("/demo", APP_ROOT+"/demo");
        tomcat.addWebapp("/docs", APP_ROOT + "/docs");


        tomcat.start();
        tomcat.getServer().await();
    }
}
