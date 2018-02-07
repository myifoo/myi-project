package com.myitech.demos.services.jetty;

import com.myitech.demos.services.jetty.Usages.HandlerUsage;
import com.myitech.demos.services.jetty.Usages.ServletUsage;
import com.myitech.demos.services.jetty.Usages.WebAppContextUsage;
import com.myitech.demos.services.jetty.handlers.HelloHandler;
import com.myitech.demos.services.jetty.handlers.SecondHandler;
import com.myitech.demos.services.jetty.servlets.HelloServlet;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.util.Arrays;


/**
 * Slogan:
 *
 *        Don't deploy your application in jetty, deploy jetty in your application!
 *
 * Created by A.T on 2018/1/24.
 *
 * @link {http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html}
 *
 */

public class EmbedJettyServer implements HandlerUsage, ServletUsage, WebAppContextUsage {

    private final Server server;

    /**
     *  1. Create a Server instance.
     *  2. Add/Configure Connectors.
     *  3. Add/Configure Handlers and/or Contexts and/or Servlets.
     *  4. Start the Server.
     *  5. Wait on the server or do something else with your thread.
     */
    public EmbedJettyServer() {
        this.server = new Server(8080);
    }

    /**
     *  Add Connector!
     */
    public EmbedJettyServer(String host, int port) {
        this.server = new Server();
        ServerConnector http = new ServerConnector(server);
        http.setPort(port);
        http.setHost(host);
        http.setIdleTimeout(30000);

        server.addConnector(http);
    }

    /**
     *  The simplest possible Jetty Server
     */
    public void start() {
        try {
            server.start();
            server.dumpStdErr();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  1. One or more handlers do all request handling in Jetty.
     *  2. Some handlers select other specific handlers (for example, a ContextHandlerCollection uses the context
     *  path to select a ContextHandler);
     *  3. others use application logic to generate a response (for example, the ServletHandler passes the request
     *  to an application Servlet),
     *  4. while others do tasks unrelated to generating the response (for example, RequestLogHandler or StatisticsHandler).
     *
     */
    public EmbedJettyServer helloHandlers() {
        HandlerList handlers = new HandlerList();


        /**
         *  Jetty has several implementations of the HandlerContainer interface:
         *      1. HandlerCollection
         *      2. HandlerList
         *      3. HandlerWrapper
         *      4. ContextHandlerCollection
         */
        handlers.setHandlers(new Handler[] {new HelloHandler(), new SecondHandler()});
        server.setHandler(handlers);

        return this;
    }


    public EmbedJettyServer helloHandlerContainer() {
        return this;
    }

    /**
     *  http://localhost:8080/index.html
     *  http://localhost:8080/js/hello.js
     */
    public EmbedJettyServer helloResouceHandler() {
        // 对应 target/classes 目录下的 public 目录
        String contentPath = this.getClass().getResource("/public").getPath();

        // Create the ResourceHandler. It is the object that will actually handle the request for a given file. It is
        // a Jetty Handler object so it is suitable for chaining with other handlers as you will see in other examples.
        ResourceHandler resource_handler = new ResourceHandler();

        // Configure the ResourceHandler. Setting the resource base indicates where the files should be served out of.
        // In this example it is the current directory but it can be configured to anything that the jvm has access to.
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        resource_handler.setResourceBase(contentPath);

        // Add the ResourceHandler to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
        server.setHandler(handlers);

        return this;
    }

    /**
     *  1. A Classloader that is set as the Thread context classloader while request handling is in scope.
     *  2. A set of attributes that is available via the ServletContext API.
     *  3. A set of init parameters that is available via the ServletContext API.
     *  4. A base Resource which is used as the document root for static resource requests via the ServletContext API.
     *  5. A set of virtual host names.
     *
     */
    public EmbedJettyServer helloContextHandler() {
        ContextHandler context = new ContextHandler();
        context.setContextPath("/app");
        context.setHandler(new HelloHandler());

        server.setHandler(context);

        return this;
    }

    /**
     *  When many contexts are present, you can embed a ContextHandlerCollection to efficiently
     *  examine a request URI to then select the matching ContextHandler(s) for the request.
     */
    public EmbedJettyServer helloContextHandlers() {
        ContextHandler context = new ContextHandler("/");
        context.setContextPath("/");
        context.setHandler(new HelloHandler("Root Hello"));

        ContextHandler contextFR = new ContextHandler("/fr");
        contextFR.setHandler(new HelloHandler("Bonjoir"));

        ContextHandler contextIT = new ContextHandler("/it");
        contextIT.setHandler(new HelloHandler("Bongiorno"));

        ContextHandler contextV = new ContextHandler("/");
        contextV.setVirtualHosts(new String[] { "127.0.0.2" });
        contextV.setHandler(new HelloHandler("Virtual Hello"));

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { context, contextFR, contextIT,
                contextV });

        server.setHandler(contexts);


        return this;
    }

    public EmbedJettyServer helloServlet() {
        /**
         *  1. The ServletHandler is a dead simple way to create a context handler that is
         *  backed by an instance of a Servlet.This handler then needs to be registered with the Server object.
         */
        ServletHandler handler = new ServletHandler();

        server.setHandler(handler);
        handler.addServletWithMapping(HelloServlet.class, "/*");

        return this;
    }


    /**
     * A ServletContextHandler is a specialization of ContextHandler with support for standard sessions and Servlets.
     *
     */
    public EmbedJettyServer helloServletContextHandler() {
        /**
         *  http://localhost:8080/index.html
         */
        String resourceBase = this.getClass().getResource("/public").getPath();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(resourceBase);
        server.setHandler(context);

        context.addServlet(DefaultServlet.class, "/");

        return this;
    }

    public EmbedJettyServer helloWebAppContext() {
        /**
         *     The WebAppContext is the entity that controls the environment in
         *     which a web application lives and breathes. In this example the
         *     context path is being set to "/" so it is suitable for serving root
         *     context requests and then we see it setting the location of the war.
         *     A whole host of other configurations are available, ranging from
         *     configuring to support annotation scanning in the webapp (through
         *     PlusConfiguration) to choosing where the webapp will unpack itself.
         */
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        String appBase = this.getClass().getResource("/webapps/jmx-webapp").getPath();

        // TODO build后生成的 jmx-webapp 丢失一部分文件，没研究什么原因，直接拷贝到target目录跳过去了。
        webapp.setWar(appBase);
        // A WebAppContext is a ContextHandler as well so it needs to be set to
        // the server so it is aware of where to send the appropriate requests.
        server.setHandler(webapp);
        return this;
    }

    /**
     *  详细讨论，参考：
     *      https://stackoverflow.com/questions/13222071/spring-3-1-webapplicationinitializer-embedded-jetty-8-annotationconfiguration
     *
     * @return
     */
    public EmbedJettyServer helloSpringWebApplication() {
        final WebAppContext webAppContext = new WebAppContext();

        //route all requests via this web-app.
        webAppContext.setContextPath("/");

        /*
         * point to location where the jar into which this class gets packaged into resides.
         * this could very well be the target directory in a maven development build.
         */
        webAppContext.setResourceBase(this.getClass().getResource(".").getPath());

        //no web inf for us - so let the scanning know about location of our libraries / classes.
        webAppContext.getMetaData().setWebInfClassesDirs(Arrays.asList(webAppContext.getBaseResource()));

        //Scan for annotations (servlet 3+)
        final AnnotationConfiguration configuration = new AnnotationConfiguration();
        webAppContext.setConfigurations(new Configuration[]{configuration});

        //Set a handler to handle requests.
        server.setHandler(webAppContext);

        return this;
    }
}
