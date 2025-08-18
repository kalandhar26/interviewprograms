package com.ds.multiThreading;

public class PrintOddEven {

    private static final int MAX = 10;
    private int count = 1;

    public static void main(String[] args) {

        PrintOddEven obj = new PrintOddEven();
        Thread t1 = new Thread(obj::printEven, "EvenThread");
        Thread t2 = new Thread(obj::printOdd, "OddThread");

        t1.start();
        t2.start();
    }

    private void printEven() {
        while (true) {
            synchronized (this) {
                while (count <= MAX) {
                    if (count % 2 != 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + " -> " + count);
                        count++;
                        notify();
                    }
                }
            }
        }
    }

    private void printOdd() {
        while (true) {
            synchronized (this) {
                while (count <= MAX) {
                    if (count % 2 == 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + " -> " + count);
                        count++;
                        notify();
                    }
                }
            }
        }
    }
}
