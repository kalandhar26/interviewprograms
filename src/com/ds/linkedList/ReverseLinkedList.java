package com.ds.linkedList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ReverseLinkedList {

    public static void main(String[] args) {

        LinkedList<Integer> input = new LinkedList<>();
        input.add(1);
        input.add(3);
        input.add(6);
        input.add(2);
        input.add(9);

        Collections.reverse(input);

        LinkedList<Integer> output = reverseLinkedList(input);
        for(int x : output) {
            System.out.println(x);
        }

    }

    public static LinkedList<Integer> reverseLinkedList(LinkedList<Integer> linkedList){
        LinkedList<Integer> reversedLinkedList = new LinkedList<>();
        while(!linkedList.isEmpty()){
            reversedLinkedList.add(linkedList.removeLast());
        }
        return reversedLinkedList;
    }
}
