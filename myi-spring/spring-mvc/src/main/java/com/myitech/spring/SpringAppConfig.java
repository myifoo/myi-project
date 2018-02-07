package com.myitech.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Created by A.T on 2018/1/26.
 *  1. Servlet3.0 通过 ServletContainerInitializer 实现 code-based 方式配置 servlet；
 *  2. ServletContainerInitializer 基于 Java Spi 技术实现；SpringServletContainerInitializer 会加载所有 WebApplicationInitializer 实现类，起到桥接作用
 *  3. spi 配置文件在 spring-web.jar 中 /META-INFO/services/javax.servlet.ServletContainerInitializer
 *  4. Servlet3.0 容器启动时会自动加载 /META-INFO/services 中的配置文件，并调用onStartUp方法
 *  5. 注意：只能是 jar 而不能使 War 的 /META-INFO/services 目录
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.myitech.spring"})
public class SpringAppConfig implements WebApplicationInitializer {
    public void onStartup(ServletContext container) {

//         Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringAppConfig.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container
                .addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
