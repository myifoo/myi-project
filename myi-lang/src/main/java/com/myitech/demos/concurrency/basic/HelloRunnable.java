package com.myitech.demos.concurrency.basic;

/**
 * Description:
 *
 *      1. This way to create thread is more general. 因为java支持多接口，不支持多继承！
 *      2.
 *
 * Created by A.T on 2018/01/31
 */
public class HelloRunnable implements Runnable {
    private Resource resource;
    private volatile int counter = 0;


    public HelloRunnable(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        System.out.println("HelloRunnable Thread id : "+Thread.currentThread().getId());
        System.out.println("HelloRunnable counter: " + ++counter);// Runnable 对象可以被多次执行，资源被共享。
        System.out.println("Resource counter: " + resource.counterIncrement());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
