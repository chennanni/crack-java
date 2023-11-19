package com.example.thread;

public class ThreadWait {

    public static void main(String[] args) throws InterruptedException {

        Object sharedObj = new Object();

        System.out.println("Main thread: create thread 1");
        new Thread(() -> {
            try {
                synchronized (sharedObj) {
                    System.out.println("  Thread 1 wait: before");
                    sharedObj.wait();
                    System.out.println("  Thread 1 wait: after");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        System.out.println("Main thread: sleep for a while, make sure thread 1 creation is finished");
        Thread.sleep(100);

        System.out.println("Main thread: create thread 2");
        new Thread(() -> {
            synchronized (sharedObj) {
                System.out.println("  Thread 2 notify: before");
                sharedObj.notify();
                System.out.println("  Thread 2 notify: after");
            }
        }).start();
    }

}
