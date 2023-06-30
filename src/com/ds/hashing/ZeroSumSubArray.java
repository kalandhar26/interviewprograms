package com.ds.hashing;

import java.util.HashSet;

public class ZeroSumSubArray {

    public static void main(String[] args) {

        int[] array = {-4,5,6,-8,0,9};

        System.out.println(zeroSumisExists(array)+" "+zeroSumisExists1(array));

    }

    // Naive
    public static boolean zeroSumisExists(int[] array) {

        for (int i = 0; i < array.length; i++) {
            int current_sum = 0;
            for (int j = i; j < array.length; j++) {
                current_sum = current_sum + array[j];
                if (current_sum == 0)
                    return true;
            }
        }

        return false;
    }

    // Efficient
    public static boolean zeroSumisExists1(int[] array) {

        HashSet<Integer> set = new HashSet<>();

        int pre_sum=0;

        for(int i=0;i<array.length;i++){
            pre_sum = pre_sum + array[i];
            if(set.contains(pre_sum))
                return true;
            if(pre_sum ==0)
                return true;
            set.add(pre_sum);
        }

        return false;
    }

}
