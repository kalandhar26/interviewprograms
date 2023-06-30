package com.ds.searching;

public class CountOccurances {

    public static void main(String[] args) {

        int[] array = {1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 7, 8, 9, 10, 10, 10};

        System.out.println(countOccurancs(array, 10));
        System.out.println(countIterative(array, 10));

    }

    // Naive
    public static int countOccurancs(int[] array, int element) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (element == array[i]) {
                count++;
            }
        }

        return count;
    }

    // BS Iterative
    public static int countIterative(int[] array, int element) {

        int first = IndexOfFirstOccurance.firstOccuranceBSI(array, element);

        if (first == -1)
            return 0;
        else
            return IndexOfLastOccurance.IOLOI(array, element) - first + 1;
    }

    // BS Recursive
    public static int countRecursive(int[] array, int element, int low, int high) {

        return 0;
    }
}
