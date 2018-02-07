# Spring
[Spring Framework Overview](https://docs.spring.io/spring/docs/current/spring-framework-reference/overview.html#overview)


1. Spring makes it easy to create Java Enterprise applications; (专注于业务，而不是专注于技术，很多框架项目，kariosdb，
jetty，guice，这些都没有使用Spring, Spring is very heavy!)
2. It provides everything you need to embrace the java language;
3. In a large enterprise, applications often exist for a long time and have to run on a JDK(6\7\8) and application 
server(jetty, tomcat...) whose upgrade cycle is beyond developer control.

# Spring framework

Documentation:
[Spring Framework Documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/)


## Structure
Core	    :   IoC container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP.
Testing	    :   Mock objects, TestContext framework, Spring MVC Test, WebTestClient.
Data Access	:   Transactions, DAO support, JDBC, ORM, Marshalling XML.
Web Servlet :   Spring MVC, WebSocket, SockJS, STOMP messaging.
Web Reactive:   Spring WebFlux, WebClient, WebSocket.
Integration :   Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Cache.
Languages   :   Kotlin, Groovy, Dynamic languages.

## Overview
1. Spring Framework is divided into modules. Application can choose which modules they need.
2. A configuration model, a dependency injection mechanism, messaging, transactional data, persistence, and web. 
3. Servlet-based Spring MVC web framework and Spring WebFlux reactive web framework.

# Spring history

Spring is complementary to Java EE. It integrates with carefully selected individual specification from the EE umbrella:

1. Servlet API(JSR 340)
2. WebSocket API(JSR 356)
3. Concurrency Utilities(JSR 236)
4. JSON Binding API(JSR 367)
5. Bean Validation(JSR 303)
6. JPA(JSR 338)
7. JMS(JSR 914)
8. Dependency Injection(JSR 330)
9. Common Annotation(JSR 250)

# Spring projects

1. Spring Framework
2. Spring Boot
3. Spring Security
4. Spring Data
5. Spring Cloud
6. Spring Batch

# Spring Initializer

[spring io](https://start.spring.io/)

# Spring boot examples

[spring boot samples](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples)

# Spring core
[spring core](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#spring-core)

## Ioc Container

ApplicationContext provides additional functionality in a more application framework-oriented style.
1. Access to messages in i18n-style, through the MessageSource interface.
2. Access to resources, such as URLs and files, through the ResourceLoader interface.
3. Event publication to namely beans implementing the ApplicationListener interface, through the use of the ApplicationEventPublisher interface.
4. Loading of multiple (hierarchical) contexts, allowing each to be focused on one particular layer, such as the web layer of an application, through the HierarchicalBeanFactory interface.

# Spring Web

Spring support for Servlet Stack, web applications built on the Servlet API and deployed to Servlet containers.

1. Spring Web MVC
2. View Technologies
3. CORS Support
4. WebSocket Support
5. Spring WebFlux (Web on Reactive Stack).

## Spring Web MVC

Parallel to Spring Web MVC, Spring Framework 5.0 introduced a reactive stack named Spring WebFlux.

## DispatcherServlet

1. The DispatcherServlet, as any Servlet, needs to be declared and mapped according to 
the Servlet specification(JSR 340 Servlet3.1) using Java configuration or in web.xml.
2. DispatcherServlet use Spring configuration to discover the delegate components it needs for request mapping, view resolution, 
exception handling and more.


In a Servlet3.0+ environment, you have the option of configuring the Servlet container programmatically as an 
alternative or in combination with a web.xml file. 

[mvc-container-config](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-container-config)

example 1: annotation way to configure
```java
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletCxt) {

        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(ac);
        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/app/*");
    }
}
```
example 2: xml way to configure
```java
import org.springframework.web.WebApplicationInitializer;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");

        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(appContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
```

In addition to using the ServletContext API directly, you can also extend:
> AbstractAnnotationConfigDispatcherServletInitializer 
> AbstractDispatcherServletInitializer

### Special Bean Types
The DispatcherServlet delegates to special beans to process requests and render the appropriate responses. 

1. HandlerMapping
2. HandlerAdapter
3. HandlerExceptionResolver
4. ViewResolver
5. LocaleResolver
6. ThemeResolver
7. MultipartResolver
8. FlashMapManager

For each type of special bean, the DispatcherServlet checks for the WebApplicationContext first. If there are no matching
bean types, if falls back on the default types listed in DispatcherServlet.properties.
```
# Default implementation classes for DispatcherServlet's strategy interfaces.
# Used as fallback when no matching beans are found in the DispatcherServlet context.
# Not meant to be customized by application developers.

org.springframework.web.servlet.LocaleResolver=org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
org.springframework.web.servlet.ThemeResolver=org.springframework.web.servlet.theme.FixedThemeResolver

org.springframework.web.servlet.HandlerMapping=org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,\
	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

org.springframework.web.servlet.HandlerAdapter=org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,\
	org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,\
	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter

org.springframework.web.servlet.HandlerExceptionResolver=org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver,\
	org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver,\
	org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver

org.springframework.web.servlet.RequestToViewNameTranslator=org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator

org.springframework.web.servlet.ViewResolver=org.springframework.web.servlet.view.InternalResourceViewResolver

org.springframework.web.servlet.FlashMapManager=org.springframework.web.servlet.support.SessionFlashMapManager
```


## Context Hierarchy

WebApplicationContext extends a plain ApplicationContext. DispatcherServlet expects a WebApplicationContext for its own
configuration.

DispatcherServlet << FrameworkServlet << HttpServlet

It is also possible to have a context hierarchy where one root WebApplicationContext is shared across multiple 
DispatcherServlet (or other Servlet) instances, each with its own child WebApplicationContext configuration.

The root WebApplicationContext typically contains infrastructure beans such as data repositories and business services 
that need to be shared across multiple Servlet instances. Those beans are effectively inherited and could be overridden 
(i.e. re-declared) in the Servlet-specific, child WebApplicationContext which typically contains beans local to 
the given Servlet:

Below is example configuration with  a WebApplicationContext hierarchy:
```java
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?[] { App1Config.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/app1/*" };
    }
 }
```
If an application context hierarchy is not required, applications may return all configuration via 
getRootConfigClasses() and null from getServletConfigClasses().

## web.xml

Below is an example of web.xml configuration to register and initialize the DispatcherServlet:
```xml
<web-app>

    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/app-context.xml</param-value>
    </context-param>

    <servlet>
        <servlet-name>app</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value></param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>app</servlet-name>
        <url-pattern>/app/*</url-pattern>
    </servlet-mapping>

</web-app>
```

Below is example configuration with  a WebApplicationContext hierarchy:

```xml
<web-app>

    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/root-context.xml</param-value>
    </context-param>

    <servlet>
        <servlet-name>app1</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/app1-context.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>app1</servlet-name>
        <url-pattern>/app1/*</url-pattern>
    </servlet-mapping>

</web-app>
```

If an application context hierarchy is not required, applications may configure a "root" context only and 
leave the contextConfigLocation Servlet parameter empty.





