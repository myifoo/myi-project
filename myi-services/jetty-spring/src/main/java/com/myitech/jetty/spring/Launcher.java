package com.myitech.jetty.spring;

import com.myitech.jetty.spring.server.InnerJetty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 2016/5/23 11:05 <br>
 * Description:
 *
 * @author tonyan
 */
public class Launcher {

       public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Launcher.class);
        logger.debug("Start main .....");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        InnerJetty jetty = (InnerJetty)applicationContext.getBean("jetty");
        jetty.startJetty();
    }
}