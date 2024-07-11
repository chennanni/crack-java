package com.example.crackjava.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  消费者-生产者，使用了LinkedList+Synchronized来保证线程安全
 *  producer生产一个，consumer消费一个
 */
public class ProducerAndConsumerWithSync {

    public static void main(String args[]) throws InterruptedException {
        ProducerWithSync producer = new ProducerWithSync();
        ConsumerWithSync consumer = new ConsumerWithSync();

        Queue<String> queue = new LinkedList<>();

        Thread t1 = new Thread(() -> {
            for (int i=0;i<100;i++) {
                producer.produce(queue, i);
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                consumer.consume(queue);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

class ProducerWithSync {
    public void produce(Queue<String> queue, int i) {
        synchronized (queue) {
            // if queue not empty, then wait
            if (!queue.isEmpty()) {
                try {
                    System.out.println("producer waiting");
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // else start to produce
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            queue.add(String.valueOf(i));
            System.out.println("produce: " + i);
            queue.notify();
        }
    }
}

class ConsumerWithSync {
    public void consume(Queue<String> queue) {
        synchronized (queue) {
            // if queue is empty, wait
            if(queue.isEmpty()) {
                try {
                    System.out.println("consumer waiting");
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // else start to consume
            String item = queue.poll();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("consume: " + item);
            queue.notify();
        }
    }
}