package com.ds.stack;

import java.util.LinkedList;

public class DynamicStackWithLinkedList {
    protected LinkedList<Integer> data;

    public DynamicStackWithLinkedList() {
        data = new LinkedList<>();
    }

    public int size() {
        return data.size();
    }

    public void push(int element) {
        data.addLast(element);
    }

    public int pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("Stack is Empty");
        }
        return data.removeLast();
    }

    public int peek() throws Exception {
        if (isEmpty()) {
            throw new Exception("Stack is Empty");
        }
        return data.getLast();
    }

    public void display() {
        for (int element : data) {
            System.out.print(element + "->");
        }
        System.out.println();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
}