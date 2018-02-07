package com.myitech.demos.services.jetty.Usages;

import com.myitech.demos.services.jetty.EmbedJettyServer;

/**
 * Created by A.T on 2018/1/29.
 */
public interface WebAppContextUsage {
    EmbedJettyServer helloWebAppContext();
    EmbedJettyServer helloSpringWebApplication();
}
