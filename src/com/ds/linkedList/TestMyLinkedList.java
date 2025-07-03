package com.ds.linkedList;

public class TestMyLinkedList {

    public static void main(String[] args) throws Exception {
        MyLinkedList ll = new MyLinkedList();
        ll.addFirst(10);
        ll.addFirst(20);
        ll.addFirst(30);
        ll.addLast(40);
        ll.addLast(50);
        ll.addAt(4,3);
        ll.addAt(24,0);
        ll.addAt(100,ll.getSize());
        ll.display();
        System.out.println("First: "+ll.getFirst()+" Last: "+ll.getLast());

        System.out.println("Get At: "+ll.getAt(5)+" | Get First: "+ll.getFirst()+" | Get Last: "+ll.getLast());
        System.out.println("Remove At: "+ll.removeAt(5)+" | Remove First: "+ll.removeFirst()+" | Remove Last: "+ll.removeLast());
        ll.display();
        ll.reverse();
        ll.display();
    }
}
