package com.java;

import java.util.Arrays;
import java.util.Comparator;

public class FindSecondLargestFromArray {

    public static void main(String[] args) {

        int[] array = {1, 2, 3, 4, 5, 9, 8, 7, 6, 5};

        System.out.println(getSecondLargestNumber(array));

        Integer integer = Arrays.stream(array).boxed().sorted(Comparator.comparingInt(Integer::intValue).reversed()).skip(1).findFirst().orElse(null);
        System.out.println(integer);
    }

    public static int getLargestNumber(int[] array) {
        int maximum = 0;
        for (int i = 0; i < array.length; i++) {
            maximum = Math.max(maximum, array[i]);
        }
        return maximum;
    }

    public static int getSecondLargestNumber(int[] array) {
        int maximum = Integer.MIN_VALUE;
        int secondMaximum = Integer.MIN_VALUE;

        // Find the largest number
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maximum) {
                maximum = array[i];
            }
        }

        // Find the second largest number
        for (int i = 0; i < array.length; i++) {
            if (array[i] > secondMaximum && array[i] < maximum) {
                secondMaximum = array[i];
            }
        }

        return secondMaximum;
    }
}
