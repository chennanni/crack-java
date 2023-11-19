package com.example.thread;

public class ThreadState {
    public static void main(String[] args) {
        System.out.println(Thread.State.NEW);
        System.out.println(Thread.State.RUNNABLE);
        System.out.println(Thread.State.WAITING);
        System.out.println(Thread.State.TIMED_WAITING);
        System.out.println(Thread.State.TERMINATED);
    }
}