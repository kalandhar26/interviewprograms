package com.ds.iterates;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayIterate {

    public static void main(String[] args) {

        int[] array={1,2,3,4,5,6,7,8,9};

        way1(array);
        way2(array);
        way3(array);
        way4(array);
    }

    // There are 4 ways

    // 1. for loop
    public static void way1(int[] array){
        for(int i=0;i< array.length;i++){
            System.out.println(array[i]);
        }
    }

    // enhanced for loop
    public static void way2(int[] array){

        for(int x : array)
            System.out.println(" "+x);

    }

    // while loop
    public static void way3(int[] array){
        int i=0;
        while(i<array.length){
            System.out.println(array[i]);
            i++;
        }
    }

    // Iterator (supports hasNext, next, remove)
    public static void way4(int[] array){
        Iterator<Integer> iterator = Arrays.stream(array).iterator();
        while (iterator.hasNext()){
            System.out.println(" "+iterator.next());
        }
    }
}
