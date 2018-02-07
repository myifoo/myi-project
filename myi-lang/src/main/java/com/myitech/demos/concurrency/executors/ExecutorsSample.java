package com.myitech.demos.concurrency.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Description:
 *
 *      Executor Interfaces:
 *          Executor > ExecutorService > ScheduledExecutorService,
 *
 *      Thread Pools:
 *          1. 主要是为了线程复用，避免 create/destroy 造成的浪费；
 *          2. FixedThreadPool, CachedThreadPool,
 *
 *      Fork/Join:
 *          1. take advantage of multiple processors.
 *          2. Designed for work that can be broken into smaller pieces recursively.
 *          3. ForkJoinPool < AbstractExecutorService
 *
 * <p>
 * Created by A.T on 2018/02/01
 */
public class ExecutorsSample {
    private Executor executor;
    private ExecutorService service;
    private ScheduledExecutorService scheduledExecutorService;

}
