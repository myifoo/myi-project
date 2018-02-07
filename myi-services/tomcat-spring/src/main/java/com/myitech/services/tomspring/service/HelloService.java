package com.myitech.services.tomspring.service;

import org.springframework.stereotype.Component;

/**
 * 2016/5/23 12:19 <br>
 * Description:
 *
 * @author tonyan
 */
@Component
public class HelloService {
    public String sayHello( ) {
        return "Hello Service!";
    }
}
