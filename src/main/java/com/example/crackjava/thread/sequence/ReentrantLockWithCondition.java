package com.example.crackjava.thread.sequence;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * two thread prints 1-100 in sequence
 * key: ReentrantLock + Condition
 */
public class ReentrantLockWithCondition {

    public int count;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void add(int num) {
        try {
            lock.lock();
            if (count % 2 != num) {
                condition.await();
            } else {
                System.out.println(Thread.currentThread().getName() + ":" + ++count);
            }
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockWithCondition r = new ReentrantLockWithCondition();

        Thread t1 = new Thread(() -> {
            for (int i=0;i<100;i++) {
                r.add(0);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i=0;i<100;i++) {
                r.add(1);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
