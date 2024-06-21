package com.example.crackjava.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.execute(()->{
            for(int i=0;i<10;i++) System.out.println(Thread.currentThread().getName());
        });
        threadPool.execute(()->{
            for(int i=0;i<10;i++) System.out.println(Thread.currentThread().getName());
        });
    }
}
