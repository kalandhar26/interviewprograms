package com.ds.hashing;

import java.util.HashSet;

public class SubArrayWithGivenSum {

    public static void main(String[] args) {

        int[] array = {-4, 5, 6, -8, 0, 9};

        System.out.println(GivenSumExists(array, 11) + " " + GivenSumExists1(array, 9));

    }

    // Naive
    public static boolean GivenSumExists(int[] array, int givenSum) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            int currentSum = 0;
            for (int j = i; j < array.length; j++) {
                currentSum += array[j];
                if (currentSum == givenSum) {
                    // Add the below line to get Longest Subarray
                    result = Math.max(result, j - i + 1);
                    System.out.println(result);
                    return true;
                }
            }
        }

        return false;
    }

    // Efficient
    public static boolean GivenSumExists1(int[] array, int givenSum) {

        HashSet<Integer> set = new HashSet<>();
        int preSum = 0;
        for (int i = 0; i < array.length; i++) {
            preSum += array[i];
            if (preSum == givenSum)
                return true;
            if (set.contains(preSum - givenSum)) {
                return true;
            }
            set.add(preSum);
        }
        return false;
    }
}

