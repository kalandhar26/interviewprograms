package com.ds.stack;

import com.ds.linkedList.MyLinkedList;

import java.util.LinkedList;

public class DynamicStackWithMyLinkedList {

    private final MyLinkedList list;

    public DynamicStackWithMyLinkedList() {
        list = new MyLinkedList();
    }

    public int size() {
        return list.getSize();
    }

    public void push(int element) {
        // Using addFirst to make push/pop O(1) operations
        list.addFirst(element);
    }

    public int pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("Stack is Empty");
        }
        return list.removeFirst();
    }

    public int peek() throws Exception {
        if (isEmpty()) {
            throw new Exception("Stack is Empty");
        }
        return list.getFirst();
    }

    public void display() {
        list.display();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // Optional: Reverse the stack
    public void reverse() {
        list.reverse();
    }
}
