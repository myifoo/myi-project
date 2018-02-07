package com.myitech.demos.concurrency.exmaple1;

/**
 * Description:
 *
 *      这里的 Drop 的作用其实就像是一个消息队列，有人put，有人take，然而同步在 Drop 中进行控制。
 *      Java 提供了很多支持 concurrency 的 List，Queue，Array，Map 实现，都是类似的实现模式。
 *
 * Created by A.T on 2018/02/01
 */
public class Drop {
    // Message sent from producer
    // to consumer.
    private String message;
    // True if consumer should wait
    // for producer to send message,
    // false if producer should wait for
    // consumer to retrieve message.
    private boolean empty = true;

    public synchronized String take() { // synchronized 获取 this.lock
        // Wait until message is
        // available.
        while (empty) {
            try {
                wait(); // wait 等待时，会释放掉 this.lock
            } catch (InterruptedException e) {}
        }
        // wait 结束之后，又重新获得 this.lock 因为有 synchronized

        // Toggle status.
        empty = true;
        // Notify producer that
        // status has changed.
        notifyAll(); //
        return message;
    }

    public synchronized void put(String message) {
        // Wait until message has
        // been retrieved.
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        // Toggle status.
        empty = false;
        // Store message.
        this.message = message;
        // Notify consumer that status
        // has changed.
        notifyAll();
    }

}
