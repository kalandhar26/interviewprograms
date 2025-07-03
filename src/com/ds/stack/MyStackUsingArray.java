package com.ds.stack;

public class MyStackUsingArray {

    protected int[] data;
    protected int top;

    public static final int DEFAULT_CAPACITY = 15;

    MyStackUsingArray() {
        data = new int[DEFAULT_CAPACITY];
        top = -1;
    }

    MyStackUsingArray(int capacity) throws Exception {
        if (capacity < 1) {
            throw new Exception("Invalid Stack Capacity");
        }
        data = new int[capacity];
        top = -1;
    }

    public int size() {
        return top + 1;
    }

    public void push(int element) throws Exception {
        if (size() == data.length) {
            throw new Exception("Stack is Full");
        }
        top++;
        data[top] = element;
    }

    public int pop() throws Exception {
        if (size() <= 0) {
            throw new Exception("Stack is Empty");
        }
        int removedElement = data[top];
        data[top] = 0;
        top--;

        return removedElement;
    }

    public int peek() throws Exception {
        if (size() <= 0) {
            throw new Exception("Stack is Empty");
        }
        return data[top];
    }


    public void display() {
        for (int i = 0; i < size(); i++) {
            System.out.print(data[i] + "->");
        }
        System.out.println();
    }

    public boolean isEmpty() {
        return size() <= 0;
    }
}
