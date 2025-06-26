package com.ds.linkedList;

public class CustomLinkedList {

    Integer data;
    CustomLinkedList next;

    CustomLinkedList(Integer x) {
        data = x;
        next = null;
    }


    public static void main(String[] args) {
        CustomLinkedList head = new CustomLinkedList(10);
        CustomLinkedList temp1 = new CustomLinkedList(20);
        CustomLinkedList temp2 = new CustomLinkedList(30);
        head.next = temp1;
        temp1.next = temp2;
    }
}
