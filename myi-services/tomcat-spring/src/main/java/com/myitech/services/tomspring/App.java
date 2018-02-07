package com.myitech.services.tomspring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        EmbeddedTomcat tomcat = (EmbeddedTomcat) applicationContext.getBean("tomcat");

        try {
            tomcat.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
