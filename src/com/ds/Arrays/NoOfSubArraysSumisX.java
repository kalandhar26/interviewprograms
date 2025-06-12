package com.ds.Arrays;

import java.util.HashMap;
import java.util.Map;

public class NoOfSubArraysSumisX {

    public static void main(String[] args) {

        int[] array = {1,2,3,1,5,2};
        int sum =2;
        System.out.println(subArraySum(array,sum));

    }

    public static int subArraySum(int[] array, int k){
       Map<Integer,Integer> map = new HashMap<>();
       map.put(0,1);
       int sum=0;
       int count=0;

       for(int i=0;i<array.length;i++){
           sum = sum + array[i];
           if(map.containsKey(sum-k)){
                count = count + map.get(sum-k);
           }
           map.put(sum,map.getOrDefault(sum,0)+1);
       }

       return count;
    }
}
