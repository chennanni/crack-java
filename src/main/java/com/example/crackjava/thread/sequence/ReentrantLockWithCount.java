package com.example.crackjava.thread.sequence;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * two thread prints 1-100, not in sequence
 */
public class ReentrantLockWithCount {

    public int count;

    private final Lock lock = new ReentrantLock();

    public void add() {
        try {
            if(lock.tryLock() || lock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    // note: should not double lock
                    // lock.lock();
                    System.out.println(Thread.currentThread().getName() + ":" + ++count);
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + ": can't get lock, timeout");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockWithCount r = new ReentrantLockWithCount();

        Thread t1 = new Thread(() -> {
            for (int i=0;i<50;i++) {
                r.add();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i=0;i<50;i++) {
                r.add();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
