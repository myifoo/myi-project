# Tomcat Server

1. BaseDir: 

安装tomcat时，会配置CATALINA_HOME环境变量，就相当于通过 setBaseDir 设置的这个目录。Tomcat 运行后会在该目录下创建 work 目录。

2. StandardContext:

创建一个 Context，用来添加特定的 Servlet。
调用 setDocBase 来配置该 Context 的文件目录。


# HelloServlet
  

# DefaultServlet

DefaultServlet的路径匹配机制还需要好好研究！很多盲点。


# WebApp

1. 配置 docBase 目录: 
2. 配置 docBase/WEB-INF/web.xml, 添加需要的 Servlet。

## app webapp

contextPath: /app
servlet:     AppServlet, "/hi"

> http://localhost:8080/app/hi

## demo webapp

contextPath: /demo
servlet:     DemoServlet, "/"

> http://localhost:8080/demo/

## docs webapp

contextPath: /docs
servlet:     DefaultServlet, "/"

> http://localhost:8080/docs/

## spring webapp

思考： Spring DispatcherServlet 到底会加载到哪个 ContextPath 下面呢？

> http://localhost:8080/app/spring

因为 app webapp 的 “/” 目录没有配置特定的 Servlet，Spring 的 DispatcherServlet 会加载在该 Context 目录下。
而 /demo, /docs 两个 web app 都配置了 “/” 对应的 Servlet，因此不会将 DispatcherServlet 挂载到其 context 下。  
