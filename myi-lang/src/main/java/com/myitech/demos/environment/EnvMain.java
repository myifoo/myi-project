package com.myitech.demos.environment;

import java.io.File;
import java.util.Properties;

/**
 * Description:
 *
 *      An application runs in a platform environment, defined by the underlying operating system,
 *      the Java virtual machine, the class libraries, and various configuration data supplied when the application
 *      is launched.
 *
 *      Configuration Utilities:
 *          1. Properties, System Properties
 *
 *      System Utilities:
 *          1. Runtime , System
 *          2. System
 *
 *      PATH and CLASSPATH:
 *
 * Created by A.T on 2018/02/01
 */
public class EnvMain {
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        System.getenv();
        File[] roots = File.listRoots();

    }
}
