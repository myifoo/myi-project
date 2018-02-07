package com.myitech.demos.spi;

import com.myitech.demos.spi.services.HelloService;

import java.util.ServiceLoader;

/**
 * Created by A.T on 2018/1/26.
 */
public class SpiMain {
    public static void main(String[] args) {
        System.out.println("Main ....");
        ServiceLoader<HelloService> loader = ServiceLoader.load(HelloService.class);

        for (HelloService hello : loader) {
            System.out.println(hello.sayHello());
        }
    }
}
