package com.myitech.spring.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by A.T on 2018/1/26.
 */
@Component
public class HelloComponent {
    final private HelloService service;

    @Autowired
    public HelloComponent(HelloService service) {
        this.service = service;
    }

    public void say() {
        System.out.println(service.say());
    }
}
