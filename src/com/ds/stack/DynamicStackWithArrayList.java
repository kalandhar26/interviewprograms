package com.ds.stack;

import java.util.ArrayList;

public class DynamicStackWithArrayList {
    protected ArrayList<Integer> data;

    public DynamicStackWithArrayList() {
        data = new ArrayList<>();
    }

    public int size() {
        return data.size();
    }

    public void push(int element) {
        data.add(element);
    }

    public int pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("Stack is Empty");
        }
        return data.remove(data.size() - 1);
    }

    public int peek() throws Exception {
        if (isEmpty()) {
            throw new Exception("Stack is Empty");
        }
        return data.get(data.size() - 1);
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