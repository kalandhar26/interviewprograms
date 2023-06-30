package com.ds.hashing;

import java.util.HashSet;

public class PairWithGivenSum {

    public static void main(String[] args) {

        int[] array = {3,2,8,15,-8};

        System.out.println(pairWithGivenSum(array, 17));
        System.out.println(pairWithGivenSum1(array, 17));

    }

    // Naive
    public static boolean pairWithGivenSum(int[] array, int sum) {

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int result = array[i] + array[j];
                if (sum == result) {
                    return true;
                }
            }
        }

        return false;
    }

    // Efficient Solution

    public static boolean pairWithGivenSum1(int[] array, int sum) {

        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < array.length; i++) {
            if (set.contains(sum - array[i])) {
                return true;
            }
            set.add(array[i]);
        }

        return false;
    }
}
