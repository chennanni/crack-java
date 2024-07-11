package com.example.crackjava.thread.sequence;

/**
 *  要求：使用三个线程 T1、T2、T3，如何让他们按顺序交替打印 10 次 A B C
 *  实现：synchronized + conditional wait + notifyAll
 *  参考：<a href="https://blog.csdn.net/qq_44993268/article/details/131696180">...</a>
 */
public class ThreeThreadCoordinate {

    private final Object lock = new Object();
    private int count = 0;

    public void print(int num, String value) {
        synchronized (lock){
            while (count % 3 != num) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(Thread.currentThread().getName() + ":" + value);
            count++;
            lock.notifyAll();
        }
    }

    public static void main(String [] args) {
        ThreeThreadCoordinate t = new ThreeThreadCoordinate();

        new Thread(()-> {
            for(int i=0;i<10;i++) {
                t.print(0,"A");
            }
        }).start();

        new Thread(()-> {
            for(int i=0;i<10;i++) {
                t.print(1,"B");
            }
        }).start();

        new Thread(()-> {
            for(int i=0;i<10;i++) {
                t.print(2,"C");
            }
        }).start();
    }
}