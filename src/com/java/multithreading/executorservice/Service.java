package com.java.multithreading.executorservice;

public class Service implements Runnable{

    int i;

    public Service(int i){
        this.i=i;
    }
    @Override
    public void run() {
        System.out.println(i+" ");
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
