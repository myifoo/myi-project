package com.myitech.spring;

import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.File;
import java.io.IOException;

/**
 * Created by A.T on 2018/1/26.
 */
public class Main {

    public static void main(String[] args) {

        try {
            new EmbeddedTomcat().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static final int PORT = 8080;

//    public static void main(String[] args) throws Exception {
//        String appBase = ".";
//        Tomcat tomcat = new Tomcat();
//        tomcat.setBaseDir(createTempDir());
//        tomcat.setPort(PORT);
//        tomcat.getHost().setAppBase(appBase);
//        tomcat.addWebapp("", appBase);
//        tomcat.start();
//        tomcat.getServer().await();
//    }
//
//    // based on AbstractEmbeddedServletContainerFactory
//    private static String createTempDir() {
//        try {
//            File tempDir = File.createTempFile("tomcat.", "." + PORT);
//            tempDir.delete();
//            tempDir.mkdir();
//            tempDir.deleteOnExit();
//            return tempDir.getAbsolutePath();
//        } catch (IOException ex) {
//            throw new RuntimeException(
//                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"),
//                    ex
//            );
//        }
//    }
}
