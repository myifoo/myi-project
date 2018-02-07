package com.myitech.demos.services.jetty.Usages;

import com.myitech.demos.services.jetty.EmbedJettyServer;
import org.eclipse.jetty.server.Server;

/**
 *  A handle may:
 *      1. Examine/modify the HTTP request.
 *      2. Generate the complete HTTP response.
 *      3. Call another Handler (see HandlerWrapper).
 *      4. Select one or many Handlers to call (see HandlerCollection).
 *
 * Created by A.T on 2018/1/29.
 */
public interface HandlerUsage {
    EmbedJettyServer helloHandlers();
    EmbedJettyServer helloHandlerContainer();
    EmbedJettyServer helloResouceHandler();
    EmbedJettyServer helloContextHandler();
    EmbedJettyServer helloContextHandlers();
}
