package com.myitech.demos.services.jetty.guice.resources;


import com.myitech.demos.services.jetty.guice.services.HelloService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by A.T on 2018/1/24.
 */
@Path("/api")
public class HelloResource {

    @Inject
    HelloService helloService;

    @GET
    @Path("/hello")
    public void say() {
        helloService.say();
    }
}

