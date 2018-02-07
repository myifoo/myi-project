package com.myitech.demos.concurrency.basic;

/**
 * Description:
 *
 *      1. Thread class defines a number of methods useful for thread management.
 *
 * Created by A.T on 2018/01/31
 */
public class HelloThread extends Thread {
    private Resource resource;

    public HelloThread(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        System.out.println("HelloThread Thread id : "+Thread.currentThread().getId());

        try {
            /**
             * Thread.sleep period can be terminated by interrupts. So in any case, you cannot
             * assume that invoking sleep will suspend the thread fro precisely the time period
             * specified.
             */
            Thread.sleep(100000); // Checked Exception: catch or specify
        } catch (InterruptedException e) {
            // It's up to the programmer to decide exactly how the thread responds to an interrupt.
            // The thread can call Thread.isInterrupted method to check the status.
            System.out.println("InterruptedException occurs");
            System.out.println("HelloThread counter :" + resource.counterIncrement());
        }
    }
}
