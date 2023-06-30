package com.ds.iterates;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TraverseArrayList {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("Baba", "Kala", "super", "bumper", "anjum");
        // There are n ways
        way1(list);
        way2(list);
        way3(list);
        way4(list);
        way5(list);
        way6(list);

    }


    // for loop
    public static void way1(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    // for each loop
    public static void way2(List<String> list) {
        for (String i : list) {
            System.out.println(i);
        }
    }

    // while loop
    public static void way3(List<String> list) {
        int i = 0;
        while (i < list.size()) {
            System.out.println(list.get(i));
            i++;
        }
    }

    // Iterator
    public static void way4(List<String> list) {

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    // List Iterator
    public static void way5(List<String> list) {
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }


    // Java 8 Streams
    public static void way6(List<String> list) {
        list.stream().forEach(x-> System.out.println("6 "+x));
    }
}


