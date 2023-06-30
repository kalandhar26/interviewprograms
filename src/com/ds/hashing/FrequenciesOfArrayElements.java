package com.ds.hashing;

import java.util.HashMap;
import java.util.Map;

public class FrequenciesOfArrayElements {

    public static void main(String[] args) {
        int[] array = {1, 2, 1,2};

        freq(array);
        freq1(array);
    }


    // Naive Solution
    public static void freq(int[] array) {

        // This loop traverse elements one after the other
        for (int i = 0; i < array.length; i++) {
            boolean flag = false; // set flag to false

            // This loop traverse from first element to the element current element of 1st loop
            for (int j = 0; j < i; j++) {
                if (array[i] == array[j]) {
                    flag = true;
                    break;
                }
            }


            if (flag == true)
                continue;

            int freq = 1;
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j])
                    freq++;
            }

            System.out.println(array[i] + " - " + freq + " times");
        }

    }

    // Efficient Solution

    public static void freq1(int[] array){

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int x : array){
            map.put(x,map.getOrDefault(x,0)+1);
        }

        for(Map.Entry<Integer,Integer> e : map.entrySet()){
            System.out.println(e.getKey()+" "+e.getValue());
        }
    }
}
