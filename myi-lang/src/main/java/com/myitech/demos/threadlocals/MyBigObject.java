package com.myitech.demos.threadlocals;

/**
 * 2016/6/6 16:04 <br>
 * Description:
 *
 * @author tonyan
 */
public class MyBigObject {
    private byte[] info = new byte[1024*1024*50];

    public void finalize() {
        System.out.println("MyBigObject 50MB finalized!");
    }
}
