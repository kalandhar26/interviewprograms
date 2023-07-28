package com.ds.interviewquestions;

import java.util.concurrent.Semaphore;

public class DesiredOutPutWithThreads {

    private static Semaphore ponySemaphore = new Semaphore(1);
    private static Semaphore tailSemaphore = new Semaphore(0);

    public static void main(String[] args) {
        Thread ponyThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    ponySemaphore.acquire();
                    System.out.println("Pony");
                    System.out.println("Pony");
                    tailSemaphore.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread tailThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    tailSemaphore.acquire();
                    System.out.println("Tail");
                    System.out.println("Tail");
                    ponySemaphore.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ponyThread.start();
        tailThread.start();

        try {
            // Wait for both threads to finish
            ponyThread.join();
            tailThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Threads stopped.");
    }


//    private static final int SETS_TO_PRINT = 10;
//    private static volatile boolean keepPrinting = true;
//
//    public static void main(String[] args) {
//        Thread ponyThread = new Thread(() -> {
//            int count = 0;
//            while (keepPrinting && count < SETS_TO_PRINT) {
//                System.out.println("Pony");
//                System.out.println("Pony");
//                count++;
//            }
//        });
//
//        Thread tailThread = new Thread(() -> {
//            int count = 0;
//            while (keepPrinting && count < SETS_TO_PRINT) {
//                System.out.println("Tail");
//                System.out.println("Tail");
//                count++;
//            }
//        });
//
//        ponyThread.start();
//        tailThread.start();
//
//        try {
//            // Wait for both threads to finish
//            ponyThread.join();
//            tailThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Threads stopped.");
//    }
}
