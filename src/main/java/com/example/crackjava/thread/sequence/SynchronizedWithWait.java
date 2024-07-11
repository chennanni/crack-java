package com.example.crackjava.thread.sequence;

/**
 * two threads print 1-100 in sequence
 * key: synchronized + wait/notify
 */
public class SynchronizedWithWait {
    public int count;

    public void add(int num) {
        synchronized (this) {
            if (count%2 !=num) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println(Thread.currentThread().getName() + ":" + ++count);
            }
            this.notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedWithWait s = new SynchronizedWithWait();

        Thread t1 = new Thread(() -> {
            for (int i=0;i<100;i++) {
                s.add(0);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i=0;i<100;i++) {
                s.add(1);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
