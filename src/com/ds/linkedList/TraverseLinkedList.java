package com.ds.linkedList;

public class TraverseLinkedList {

    public static void main(String[] args) {
        Node head = new Node(10);
        head.next = new Node(20);
        head.next.next = new Node(30);
        head.next.next.next = new Node(40);

        System.out.println(head.toString());
        traverseLinkedList(head);
        recursiveTraverseLinkedList(head);
    }

    // Iterative
    public static void traverseLinkedList(Node head){
        Node current = head;

        while(current != null){
            System.out.println(current.data+" ");
            current = current.next;
        }
    }

    // Recursive
    public static void recursiveTraverseLinkedList(Node head){
        if(head==null)
            return;
        System.out.println(head.data+" ");
        recursiveTraverseLinkedList(head.next);
    }
}
