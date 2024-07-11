package com.example.crackjava.model;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *  经典 消费者-生产者 模型
 *  producer一边生产，consumer一边消费
 *  核心就是使用线程安全的容器
 */
public class ProducerAndConsumer {

    public static void main(String args[]) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        Queue<String> queue = new ArrayBlockingQueue<>(100);

        new Thread(() -> {
            for (int i=0;i<100;i++) {
                producer.produce(queue, i);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                consumer.consume(queue);
            }
        }).start();
    }
}

class Producer {
    public void produce(Queue<String> queue, int i) {
        if (queue.size()<100) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            queue.add(String.valueOf(i));
            System.out.println("produce: " + i);
        }
    }
}

class Consumer {
    public void consume(Queue<String> queue) {
        if(!queue.isEmpty()) {
            String item = queue.poll();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("consume: " + item);
        }
    }
}