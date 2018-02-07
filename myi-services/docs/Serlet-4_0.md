# ServletConfig

A servlet configuration object used by a servlet container to pass information to a servlet during initialization. 

初始化阶段，Servlet容器通过ServletConfig对象往servlet中传递信息。这些信息都是开发者通过web.xml或注解进行配置的。

servletName
servletInitParameter

ServletContext: 

# ServletContext

Defines a set of methods that a servlet uses to communicate with its servlet container.

There is one context per "web application" per Java Virtual Machine. A "web application" is a collection of servlets 
and content installed under a specific subset of the server's URL namespace such as '/catalog' and possibly installed 
via a *.war file. 也可以通过编程的形式添加 webApp。

ServletContext 可以用来存储该context下的所有 Servlet 的共享信息( distributed 例外)。

Request URI : context path + servlet path + other uri

# Deployment Descriptor 部署描述文件

也就是 web.xml 文件；

加载顺序： context-param > listener -> filter -> servlet

context-param： 提供 ServletContext level 的配置信息；所有的 filter,listener,servlet 都可以获取这些配置信息；
```xml
<context>
        <param-name>paramKey</param-name>
        <param-value>paramValue</param-value>
</context>
```
如果想要获得参数值，可以在servlet中调用： 
(1) getServletContext.getInitParameter(“”); 
(2) getServletConfig().getServletContext().getInitParameter(); 
在web应用部署完成以后，值没法改变。

web 容器启动时初始化每个 filter 时，是按照 filter 配置节出现的顺序来初始化的，当请求资源匹配多个 filter-mapping 时， 
filter 拦截资源是按照 filter-mapping 配置节出现的顺序来依次调用 doFilter() 方法的。

## Listener

```xml
 <listener>
     <listener-class></listener-class>
 </listener>
 
```
web容器自己会根据listener-class中的类implements什么类型的接口进行判断。

1. javax.servlet.ServletContextListener, 
2. javax.servlet.ServletContextAttributeListener,
3. javax.servlet.ServletRequestListener,
4. javax.servlet.ServletRequestAttributeListener, 
5. javax.servlet.http.HttpSessionListener,
6. javax.servlet.http.HttpSessionAttributeListener,
7. javax.servlet.http.HttpSessionIdListener,

## Filter

```xml
<filter>
     <filter-name></filer-name>
     <filer-class></filer-class>
     <init-param>
         <param-name></param-name>
         <param-value></param-value>
     </init-param>
 </filter>
```
```xml
<filer-mapping>
    <filer-name></filer-name>
    <url-pattern></url-pattern>
</filer-mapping>
```

filter-mapping 有两种方式，一种是url-pattern，一种是匹配 servlet.

## Servlet

```xml
<servlet>
     <servlet-name></servlet-name>
     <servlet-class></servlet-class>
     <init-param>
         <param-name></param-name>
         <param-value></param-value>
     </init-param>
     <init-param>
         <param-name></param-name>
         <param-value></param-value>
     </init-param>
     <load-on-startup></load-on-startup>
 </servlet>
```
```xml
<servlet-mapping>
    <servlet-name></servlet-name>
    <url-pattern></url-pattern>
</servlet-mapping>
```

## SessionConfig
```xml
 <session-config>
     <session-timeout></session-timeout>
 </session-config>
```
如果某个会话在一定时间内未被访问，服务器可以抛弃它以节省内存。




