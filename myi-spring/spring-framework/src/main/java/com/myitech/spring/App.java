package com.myitech.spring;

import com.myitech.spring.component.HelloComponent;
import com.myitech.spring.component.HelloService;
import com.myitech.spring.component.HelloServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.myitech.spring"})
public class App 
{
    /**
     * Fuck,  这里函数的名称必须为 mockClassName()
     */
    @Bean
    HelloService mockHelloService() {
        return new HelloServiceImpl();
    }

    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        HelloComponent hello = context.getBean(HelloComponent.class);

        hello.say();
    }
}
