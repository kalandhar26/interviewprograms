package com.java.multithreading.synchronization;

import javax.swing.*;

public class Stack {

    private int[] array;
    private int stackTop;

    private Object lock;

    public Stack(int capacity){
        array = new int[capacity];
        stackTop=-1;
        lock = new Object();
    }

    public boolean isEmpty(){
        return stackTop<0;
    }

    public boolean isFull(){
        return stackTop >= array.length-1;
    }

    public boolean push(int element){
        synchronized (lock) {
            if (isFull()) return false;
            ++stackTop;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            array[stackTop] = element;
            return true;
        }
    }

    public int pop(){
        synchronized (lock) {
            if (isEmpty()) return Integer.MIN_VALUE;
            int obj = array[stackTop];
            array[stackTop] = Integer.MIN_VALUE;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            stackTop--;
            return obj;
        }
    }
}
