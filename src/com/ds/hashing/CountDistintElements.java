package com.ds.hashing;

import java.util.Arrays;
import java.util.HashSet;

public class CountDistintElements {

    public static void main(String[] args) {

        int[] array = {10, 11, 13, 14, 14, 15};
        Integer[] array1 = {10, 11, 13, 14, 14, 15};

        System.out.println(countDistinctElements(array));
        System.out.println(countDistinctHS(array));
        System.out.println(countDistinctHS1(array1));

    }

    // Naive Solution
    public static int countDistinctElements(int[] array) {

        int result = 0, n = array.length;
        ;

        for (int i = 0; i < n; i++) {
            boolean flag = false;
            for (int j = 0; j < i; j++) {
                if (array[i] == array[j]) {
                    flag = true;
                    break;
                }
            }
            if (flag == false)
                result++;
        }

        return result;
    }

    // Using HashSet
    public static int countDistinctHS(int[] array) {
        HashSet<Integer> s = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            s.add(array[i]);
        }

        return s.size();
    }

    // Improved Efficient Solution
    public static int countDistinctHS1(Integer[] array) {
        HashSet<Integer> s = new HashSet<>(Arrays.asList(array));
        return s.size();
    }

}
