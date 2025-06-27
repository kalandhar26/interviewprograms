package com.ds.linkedList;

public class Node {

    int data;
    Node next;

    Node(int x) {
        data = x;
        next = null;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }
}

