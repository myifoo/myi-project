package com.myitech.demos.concurrency.basic;

/**
 * Description:
 * <p>
 * Created by A.T on 2018/01/31
 */
public class Resource {
    public enum GENDER {MALE, FEMALE}

    private String name;
    private int age;
    private Enum<GENDER> gender;
    private int counter;

    private Object lock1 = new Object();

    public Resource(String name, int age, Enum<GENDER> gender, int counter) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.counter = counter;
    }

    /**
     *  To make a method synchronized, simply add the synchronized keyword to its declaration.
     *
     *  1. When one thread is executing a synchronized method for an object, all other threads
     *  that invoke synchronized methods for the same object block (suspend execution) until the
     *  first thread is done with the object.
     *
     *   2. Changes to the state of the object are visible to all threads.
     */
    public synchronized int counterIncrement() {
       return ++counter;
    }

    /**
     *  monitor lock:
     *      1. Every object has a monitor lock.
     *
     */
    public int counterIncrementSync() {

        // synchronized statement 1
        synchronized (this) {
            ++counter;
        }

        // synchronized statement 2
        synchronized (lock1) {
            ++counter;
        }

        // Every object has a monitor lock.
        synchronized (name) {
            ++counter;
        }

        return counter;

    }
}
