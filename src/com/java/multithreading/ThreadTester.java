package com.java.multithreading;

public class ThreadTester {

    public static void main(String[] args) {

        // First way of creating thread using Thread class
        Thread1 t1 = new Thread1("Thread-Thread1");
        t1.setDaemon(true);
        t1.start();

        for(int i=0;i<10;i++) {
            System.out.println(Thread.currentThread().getName()+"-"+i);
        }

        // 2nd way of Creating Thread using Runnable interface
        Thread2 t2 = new Thread2();
        Thread t = new Thread(t2,"Runnable-Thread2");
        t.start();

        // 3rd way of creating thread using lambda expressions
        Thread t3 = new Thread(()->{
            for(int i=0;i<10;i++){
                System.out.println(Thread.currentThread().getName()+"-"+i);
            }
        },"Lambda-Thread");
        t3.start();
    }
}
