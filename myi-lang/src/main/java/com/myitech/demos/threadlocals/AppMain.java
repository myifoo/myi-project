package com.myitech.demos.threadlocals;

/**
 * 2016/6/6 15:58 <br>
 * Description:
 *
 * @author tonyan
 */
public class AppMain {
    public static void main(String args[]){
        System.out.println("Hello World!");

        ThreadLocal<MyBigObject> t1 = new MyThreadLocal<MyBigObject>();
        t1.set(new MyBigObject());

        t1=null;
        System.out.println("Full GC");
        try {
            System.gc();
            Thread.sleep(1000);
            System.gc();
            Thread.sleep(1000);
            System.gc();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThreadLocal<String> tmp = new ThreadLocal<String>() {
            public void clearThreadlocalMap(){
            }
        };


        tmp.set("null");
        System.out.println("Full GC");
        try {
            System.gc();
            Thread.sleep(1000);
            System.gc();
            Thread.sleep(1000);
            System.gc();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main end!");
    }
}
