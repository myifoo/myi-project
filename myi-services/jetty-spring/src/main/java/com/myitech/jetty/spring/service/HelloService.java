package com.myitech.jetty.spring.service;

import org.springframework.stereotype.Component;

/**
 * 2016/5/23 12:19 <br>
 * Description:
 *
 * @author tonyan
 */
@Component
public class HelloService {
    static public String test;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String sayHello( ) {
        return user + "Hello Service!";
    }

    public static String sayTest(){
        return test;
    }
}
