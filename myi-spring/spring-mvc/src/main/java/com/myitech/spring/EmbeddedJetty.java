package com.myitech.spring;

import org.eclipse.jetty.server.Server;

/**
 * Created by A.T on 2018/1/29.
 */
public class EmbeddedJetty {
    public void start() throws Exception {
        Server server = new Server(8080);

        String SERVER_ROOT = Class.class.getResource("/webroot").getPath();


        System.out.println("Server Root :" + SERVER_ROOT);

        // Step 1: config server

        // Step 2: add web app


        server.start();
        server.join();
    }
}
