package com.java.multithreading.executorservice;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo1 {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        System.out.println(LocalDateTime.now());
        for(int i=0;i<25;i++){
            executorService.execute(new Service(i));
        }
        executorService.shutdown();
        boolean b = executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(b);
        System.out.println(LocalDateTime.now());
    }
}
