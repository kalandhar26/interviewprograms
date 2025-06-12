package com.java.multithreading.completablefuture;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo1 {

    private static int data;
    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.currentThread().getName()+"-"+data+"-"+ LocalDateTime.now());
        CompletableFuture.runAsync(
                ()->{
                    try{
                        System.out.println(Thread.currentThread().getName()+" Inside Completable Future 1");
                        Thread.sleep(1500);
                        System.out.println(Thread.currentThread().getName()+" Inside Completable Future 2");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    data=10;
                });

        System.out.println(Thread.currentThread().getName()+"-"+data+"-"+ LocalDateTime.now());
        Thread.sleep(2500);
        System.out.println(Thread.currentThread().getName()+"-"+data+"-"+ LocalDateTime.now());
    }
}
