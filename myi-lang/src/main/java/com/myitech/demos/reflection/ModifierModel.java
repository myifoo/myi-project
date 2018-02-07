package com.myitech.demos.reflection;

import com.myitech.demos.annotations.MyAnnotation;

import java.io.Serializable;

/**
 * Description:
 * <p>
 * Created by A.T on 2018/01/31
 */
@MyAnnotation("reflection")
public class ModifierModel extends Thread implements Serializable{
    private String slogon = "Hello Reflection!";

    public void run() {
        System.out.println(slogon);
    }


}
