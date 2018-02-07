package com.myitech.services.tomcat;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * Created by A.T on 2018/1/28.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.myitech.services.tomcat"})
public class SpringAppConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Nullable
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Nullable
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ SpringAppConfig.class };
    }

    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
