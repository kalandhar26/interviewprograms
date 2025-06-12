package com.ds.Arrays;

import java.util.HashMap;
import java.util.Map;

public class ArrayDoubleFinder {

    public static void main(String[] args) {
        int[] array = {2,3,5,7,9};
        System.out.println(arrayDoubleFinder(array));
    }


    public static Boolean arrayDoubleFinder(int[] array){

        Map<Integer,Integer> doubleFinder = new HashMap<>();

        for(int j : array) {
            doubleFinder.put(j*2,j);
        }




        return false;

    }
}
