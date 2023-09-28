package com.java;

import java.util.*;

public class GetAllOnesToStartingOfArray {

    public static void main(String[] args) {
        int[] array=  {1,2,3,1,1,2,2,1,3,2,1};
        int n = array.length; // n=10
        int[] result = new int[n];
        int count = n-1;
        for(int i = n-1; i>=0;i--){
            if(array[i]!=1){
                result[count] = array[i];
                count--;
            }
        }

        while(count>=0){
            result[count]=1;
            count--;
        }

        for(int x : result)
            System.out.print(x+" ");


        List<Integer> list = new ArrayList<>();

        list.add(12);

        Optional<Integer> max = list.stream().max(Comparator.comparingInt(Integer::intValue));

        System.out.println(max);
    }
}
