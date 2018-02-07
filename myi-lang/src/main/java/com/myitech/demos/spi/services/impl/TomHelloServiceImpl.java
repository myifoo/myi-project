package com.myitech.demos.spi.services.impl;

import com.myitech.demos.spi.services.HelloService;

/**
 * Created by A.T on 2018/1/26.
 */
public class TomHelloServiceImpl implements HelloService{
    public String sayHello() {
        return "Tom :  Hello Java SPI!";
    }
}
