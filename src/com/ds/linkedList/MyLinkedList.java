package com.ds.linkedList;

public class MyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        head = tail = null;
        size = 0;
    }


    // Insertion
    public void addFirst(int data) {
        Node n = new Node(data);
        n.next = head;
        head = n;
        if (size == 0) {
            tail = n;
        }
        size++;
    }

    public int removeFirst() throws Exception {
        if(size<=0){
            throw new Exception("LinkedList is empty");
        }
        int output = head.data;
        if(size==1){
            head = tail=null;
            size=0;
        }else{
            head = head.next;
            this.size--;
        }

        return output;
    }

    public void addLast(int data) {
        Node n = new Node(data);
        if (size == 0) {
            head = tail = n;
        }
        tail.next = n;
        tail = tail.next;
        size++;
    }

    public int removeLast() throws Exception {
        if(size<=0){
            throw new Exception("LinkedList is empty");
        }
        int output = tail.data;
        if(size==1){
            head = tail=null;
            size=0;
        }else {
            Node temp = head;
            while(temp.next != tail){
                temp = temp.next;
            }

            temp.next = null;
            tail = temp;
            size--;
        }

        return output;
    }

    public void addAt(int data, int index) throws Exception {
        if (index < 0 || index > size) {
            throw new Exception("IndexOutOfBound Error");
        } else if (index == 0) {
            addFirst(data);
        } else if (index == size) {
            addLast(data);
        } else {
            Node temp = head;
            for (int jump = 1; jump <= index - 1; jump++) {
                temp = temp.next;
            }
            Node n = new Node(data);
            n.next = temp.next;
            temp.next = n;
            size++;
        }
    }

    public int removeAt(int index) throws Exception {
        if (size <= 0) {
            throw new Exception("LinkedList is empty");
        } else if (index < 0 || index >= size ) {
            throw new Exception("OutOfBound Exception");
        }else if(index==0){
           return removeFirst();
        } else if (index == size-1) {
           return removeLast();
        }else{
            Node temp = head;
            for (int jump = 1; jump <= index; jump++) {
                temp = temp.next;
            }
            Node output = temp.next;
            temp.next = output.next;
            size--;
            return output.data;
        }

    }

    public void display() {
        Node temp = head;

        while (temp != null) {
            System.out.print(temp.data + "-> ");
            temp = temp.next;
        }
        System.out.println();
    }

    public int getSize() {
        return size;
    }


    public int getFirst() throws Exception {
        if(size==0){
            throw new Exception("LinkedList is Empty");
        }

        return head.data;
    }

    public int getLast() throws Exception {
        if(size==0){
            throw new Exception("LinkedList is Empty");
        }
        return tail.data;
    }

    public int getAt(int index) throws Exception {
        if(size==0 || index <0 || index > size){
            throw new Exception("LinkedList is Empty or OutOfBound Exception");
        }else if (index == 0) {
          return  getFirst();
        } else if (index == size-1) {
          return  getLast();
        } else {
            Node temp = head;
            for(int jump=1;jump<=index;jump++){
                temp = temp.next;
            }
            return temp.data;
        }

    }

    public void reverse(){
        if(size<=1)
            return;

        Node previous = null;
        Node current = head;
        Node next;

        // The old head becomes the new tail
        tail = head;

        while(current != null){
            next = current.next; // Store next node
            current.next = previous;  // Reverse the link
            previous = current; // Move prev to current
            current = next;  // Move current to next
        }

       head = previous; // the last node become the head
    }
}
