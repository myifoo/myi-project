package com.myitech.demos.concurrency;

import com.myitech.demos.concurrency.basic.*;
import com.myitech.demos.concurrency.exmaple1.Consumer;
import com.myitech.demos.concurrency.exmaple1.Drop;
import com.myitech.demos.concurrency.exmaple1.Producer;
import com.myitech.demos.concurrency.lock.SafeLock;


/**
 * Description:
 *
 *      Processes and Threads(进程与线程)
 *      1. IPC，Concurrency, processor, ProcessBuilder
 *      2. Creating a new thread requires fewer resources than creating a new process.
 *      3. Threads share the process's resources, including memory and open files.
 *      4. Every java application has some system threads that do things like memory management and
 *      signal handling. While the programmer only cares about the Main thread.
 *
 *      Synchronization：
 *      1. Threads communicate primarily by sharing access to fields and the objects reference fields refer to.
 *      2. This form of communication is extremely efficient, but makes two kinds of errors possible:
 *         thread interference and memory consistency errors.
 *      3. Synchronization prevent above errors at the cost of  introducing thread contention(Starvation and live lock).
 *      4. Thread Interference, Memory Consistency Errors, Synchronized methods, Implicit Locks and Synchronization,
 *      Atomic Access.
 *
 *      Threads，Concurrency，Block/Unblock, 深入、仔细的理解这些概念！
 *
 *      Concurrency 的场景：Java 的并发机制是如何实现以下场景和功能的？
 *          1. 资源竞争： lock，synchronized，互斥锁（mutex），atomic
 *          2. 条件等待： condition，
 *          3. 信号量： semaphore，适合较长时间等待
 *          4. 自旋锁： Spanlock，短时间等待，不会睡眠，只是自旋并询问锁状态
 *          5. reentrant lock:
 *
 *      有些场景可以在语言层面提供解决方案，有些则是在设计层面解决，比如Event bus，Subscribe/Listener
 *
 *      Guarded Blocks: 参见各种 Concurrency Collections 的实现；
 *
 *      Immutable Objects:
 *          1. An object is considered immutable if its state cannot change after it is constructed.
 *          2. Immutable objects are particularly useful in concurrent applications.
 *          3. Immutable Objects VS Concurrency control
 *
 *      Rules for creating immutable objects.
 *          1. Don't provide setting methods.
 *          2. Make all fields final and private.
 *          3. Don't allow subclass to override methods by declaring the class final or to make the constructor private.
 *          4. Any modify would create a new object.
 *
 *      Higher-level Concurrency Objects: java.util.concurrent
 *          1. Lock objects: java.util.concurrent.locks
 *          2. Executors:
 *          3. Concurrent Collections:
 *          4. Atomic variables:
 *          5. ThreadLocalRandom:
 *
 *
 * <p>
 * Created by A.T on 2018/01/31
 */
public class ConcurrencyMain {

    public static void main(String[] args) {
        waitSample();
//        waitError();
        SafeLock.safeLockSample();

    }

    public static void producerSample() {
        Drop drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }

    public static void simpleSample() {
        System.out.println("Main thread id : " + Thread.currentThread().getId());

        // thread 1,2,3 多线程会对 resource 对象资源进行竞争；
        Resource resource = new Resource("Demo", 18, Resource.GENDER.FEMALE, 20);

        // thread 1,3 会对 hello 对象资源进行竞争
        Runnable hello = new HelloRunnable(resource);
        Thread thread1 = new Thread(hello);
        Thread thread3 = new Thread(hello);
        thread1.start();
        thread3.start();

        Thread thread2 = new HelloThread(resource);
        thread2.start();

        try {
            Thread.sleep(1000);
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.interrupt();
    }

    public static void waitError() {
        Runnable waitRunnable = new WaitErrorRunnable();

        // Main thread own waitRunnable object intrinsic lock, so wait() in WaitSampleRunnable.run() will
        // throw error.
        synchronized (waitRunnable) {
            new Thread(waitRunnable).start();
        }
    }

    public static void waitSample() {
        WaitSampleRunnable waitRunnable = new WaitSampleRunnable();
        Thread thread1 = new Thread(waitRunnable);
        Thread thread2 = new Thread(waitRunnable);
        Thread thread3 = new Thread(waitRunnable);

        try {
            thread1.start();
            thread2.start();
            thread3.start();

            Thread.sleep(1000);
            thread1.interrupt();
            Thread.sleep(1000);
            thread2.interrupt();
            Thread.sleep(1000);
            thread3.interrupt();

            waitRunnable.setStatus(true);
            waitRunnable.notifyAllWaits();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
