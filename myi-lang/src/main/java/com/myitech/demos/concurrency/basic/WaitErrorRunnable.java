package com.myitech.demos.concurrency.basic;

/**
 * Description:
 * <p>
 * Created by A.T on 2018/02/01
 */
public class WaitErrorRunnable implements Runnable {
    @Override
    public void run() {
        try {
            wait(); // 无法获取 intrinsic lock
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
