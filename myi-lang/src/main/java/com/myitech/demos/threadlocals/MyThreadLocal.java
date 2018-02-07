package com.myitech.demos.threadlocals;

/**
 * 2016/6/6 16:01 <br>
 * Description:
 *
 * @author tonyan
 */
public class MyThreadLocal<T> extends ThreadLocal<T> {
    private byte[] info = new byte[1024*1024];

    public void finalize() {
        System.out.println("MyThreadLocal info finalized!");
    }
}
