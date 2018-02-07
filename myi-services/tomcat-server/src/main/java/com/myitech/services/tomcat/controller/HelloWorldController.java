package com.myitech.services.tomcat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by A.T on 2018/1/26.
 */
@RestController
@RequestMapping("/spring")
public class HelloWorldController {
    @GetMapping
    public String sayHello() {
        return "Hello Tomcat from Spring DispatcherServlet !";
    }
}
