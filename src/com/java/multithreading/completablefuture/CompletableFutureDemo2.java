package com.java.multithreading.completablefuture;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        System.out.println(Thread.currentThread().getName() + "-" + LocalDateTime.now());
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " Inside Completable Future 1");
                        Thread.sleep(5000);
                        System.out.println(Thread.currentThread().getName() + " Inside Completable Future 2");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "data from supplyAsync() !!";
                });

        System.out.println(stringCompletableFuture.get());
        System.out.println(Thread.currentThread().getName() + "-" + LocalDateTime.now());
        Thread.sleep(2500);
        System.out.println(Thread.currentThread().getName() + "-" + LocalDateTime.now());
    }
}
