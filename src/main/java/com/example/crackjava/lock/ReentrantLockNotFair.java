package com.example.crackjava.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockNotFair {
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public static void writeAndCreate(int milisToSleep) {
        System.out.println(Thread.currentThread().getName() + " 开始尝试获取写锁");
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " !!! 获取写锁，开始执行");
            Thread.sleep(milisToSleep);
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放写锁");
            reentrantLock.unlock();

            System.out.println(">>>>>创建新的线程，看新线程是否能插队队列中的线程");
            new Thread(() -> {
                int subThreadNumber = 100;
                Thread[] subThreads = new Thread[subThreadNumber];
                for (int i = 0; i < subThreadNumber; i++) {
                    subThreads[i] = new Thread(() -> process(1), "   Sub Thread + " + i);
                }
                for (int k = 0; k < subThreadNumber; k++) {
                    subThreads[k].start();
                }
            }).start();

        }
    }

    public static void process(int milisToSleep) {
        System.out.println(Thread.currentThread().getName() + " 开始尝试获取写锁");
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " ### 获取写锁，开始执行");
            Thread.sleep(milisToSleep);
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放写锁");
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {

        int threadNumber = 5;

        System.out.println("==========验证 非公平锁 可以插队==========");

        System.out.println(">>>>>创建1个长时间线程获取锁");

        new Thread(() -> writeAndCreate(10*threadNumber), "Zero Thread").start();

        System.out.println(">>>>>再创建n个线程，获取不到锁，进入队列等待");

        // create threads
        Thread[] threads = new Thread[threadNumber];
        for (int i = 0; i < threadNumber; i++) {
            threads[i] = new Thread(() -> process(1), "Thread - " + i);
        }
        // start threads
        for (int i = 0; i < threadNumber; i++) {
            threads[i].start();
            // sleep a while when creating thread to ensure sequence
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}