package com.java.multithreading.synchronization;

import javax.swing.*;

public class Stack {

    private final int[] array;
    private int stackTop;

    private final Object lock;

    public Stack(int capacity) {
        array = new int[capacity];
        stackTop = -1;
        lock = new Object();
    }

    public boolean isEmpty() {
        return stackTop < 0;
    }

    public boolean isFull() {
        return stackTop >= array.length - 1;
    }

    public boolean push(int element) {
        synchronized (lock) {
            if (isFull()) return false;
            ++stackTop;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            array[stackTop] = element;
            return true;
        }
    }

    public int pop() {
        synchronized (lock) {
            if (isEmpty()) return Integer.MIN_VALUE;
            int obj = array[stackTop];
            array[stackTop] = Integer.MIN_VALUE;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            stackTop--;
            return obj;
        }
    }
}
