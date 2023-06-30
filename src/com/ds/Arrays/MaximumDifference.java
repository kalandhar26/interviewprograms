package com.ds.Arrays;

public class MaximumDifference {

    public static void main(String[] args) {
        int array[] = {30, 10, 8, 2};

        System.out.println(maximumDifference1(array));
    }

    // Naive Solution
    public static int maximumDifference(int[] array) {
        int maximum = array[1] - array[0];
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                maximum = Math.max(maximum, (array[j] - array[i]));
            }
        }
        return maximum;
    }

    // Efficient algorithm
    public static int maximumDifference1(int[] array) {
        int maximum = array[1] - array[0], min = array[0];
        for (int i = 1; i < array.length; i++) {
            maximum = Math.max(maximum, (array[i] - min));
            min = Math.min(min, array[i]);
        }
        return maximum;
    }

}
