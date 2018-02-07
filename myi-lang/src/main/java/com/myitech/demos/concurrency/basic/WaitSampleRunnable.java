package com.myitech.demos.concurrency.basic;

/**
 * Description:
 * <p>
 * Created by A.T on 2018/02/01
 */
public class WaitSampleRunnable implements Runnable{
    private boolean status = false;
    private Object demoLock = new Object();

    @Override
    public void run() {
        // Always invoke wait inside a loop that tests for the condition being waited for. Don't assume
        // that the interrupt was for the particular condition you were waiting for, or that the condition is still true.
        // 一定要在 while loop 中调用 wait(), 因为无法知道到底是什么原因触发的中断，有可能是条件满足，也有可能是其他原因。
        long id = Thread.currentThread().getId();
        System.out.println("Enter in Thread id : " + id);

        while (!status) {
            /**
             *  要理解调用 wait 时，背后发生的事情：
             *      1. 首先，thread 会获取并 own demoLock 的 intrinsic lock，否则将抛出错误；也就是说当一个 thread 获取了
             *      demoLock's intrinsic lock 时，其他 threads 调用 wait 将抛出错误；
             *      2. 成功调用 wait 之后，thread 将释放调用 demoLock's intrinsic lock 并处于 waiting 状态；这时，
             *      其他 threads 也可以调用 wait 了；
             *      3. 本线程将一直 waiting，直到有其他线程调用 demoLock.notifyAll();
             *
             */
            synchronized (demoLock) {
                System.out.println("Waiting Thread id :  " + id); // 这里会释放掉拥有的 demoLock；
                try {
                    demoLock.wait(); // When invoke demoLock.wait(), thread must own the intrinsic lock for demoLock.
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Thread id : " + id);
                }
            }
        }
        System.out.println("Exit : " + id);
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void notifyAllWaits() {
        synchronized (demoLock) {
            demoLock.notifyAll(); // 也需要进行同步，当 intrinsic lock 被其他线程占用时，会抛出异常
        }
    }
}
