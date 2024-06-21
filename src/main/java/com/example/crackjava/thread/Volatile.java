package com.example.crackjava.thread;

public class Volatile {

    public int count;
    public volatile int countVolatile;
    public volatile boolean isChangedVolatile;

    public static void main(String[] args) throws InterruptedException {

        Volatile obj = new Volatile();

        System.out.println("Test 1: non thread safe");
        System.out.println("count initial value: " + obj.count);
        // create thread 1 and do increment
        Thread t1 = new Thread() {
            @Override
            public void run() {
                for(int i=0;i<100000;i++) obj.count++;
            }
        };
        // create thread 2 and do increment
        Thread t2 = new Thread() {
            @Override
            public void run() {
                for(int i=0;i<100000;i++) obj.count++;
            }
        };
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // count usually < 20,000
        System.out.println("count final value: " + obj.count);



        System.out.println("\nTest 2: non thread safe using volatile");
        System.out.println("count initial value: " + obj.countVolatile);
        // create thread 1 and do increment
        Thread t3 = new Thread() {
            @Override
            public void run() {
                for(int i=0;i<100000;i++) obj.countVolatile++;
            }
        };
        // create thread 2 and do increment
        Thread t4 = new Thread() {
            @Override
            public void run() {
                for(int i=0;i<100000;i++) obj.countVolatile++;
            }
        };
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        // count usually < 20,000
        System.out.println("count final value: " + obj.countVolatile);
        System.out.println("conclusion: volatile can't ensure atomic operation");
        System.out.println("should use lock or atomic integer");



        System.out.println("\nTest 3: volatile use case with boolean");
        // create thread 1 and do increment
        Thread t5 = new Thread() {
            @Override
            public void run() {
                obj.isChangedVolatile = true;
                System.out.println("thread1: change value");
            }
        };
        // create thread 2 and do increment
        Thread t6 = new Thread() {
            @Override
            public void run() {
                if (obj.isChangedVolatile) System.out.println("thread2: value is changed");
            }
        };
        t5.start();
        t6.start();
        t5.join();
        t6.join();
        System.out.println("when thread 1 changes the flag, thread2 will know immediately");

    }

}