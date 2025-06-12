package com.java.multithreading.synchronization;

import java.time.LocalDateTime;

public class ThreadTester {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"Started from "+ LocalDateTime.now());

        Stack stack = new Stack(5);

        new Thread(()->{
            int counter=0;
            while (++counter<10)
                System.out.println("Pushed: "+stack.push(100));
        },"Pushed").start();

        new Thread(()->{
            int counter=0;
            while (++counter<10)
                System.out.println("Popped: "+stack.pop());
        },"Popped").start();

        System.out.println(Thread.currentThread().getName()+"Ended at "+ LocalDateTime.now());
    }
}
