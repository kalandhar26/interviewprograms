package com.ds.searching;

import java.util.Arrays;

public class IndexOfFirstOccurance {

    public static void main(String[] args) {

        int[] array = {5,10,10,15,15};

        System.out.println(firstOccuranceBSI(array, 15));
        System.out.println(firstOccuranceBSR(array, 15, 0, 4));

    }

    // Naive Solution
    public static int firstOccur(int[] array, int element) {

        int n = array.length;
        int x = -1;
        for (int i = 0; i < n; i++) {
            if (element == array[i]) {
                x = i;
                return x; // add this line to get firstOccurance
            }
        }
        return x;
    }

    // Binary Search Approach (Iterative)

    public static int firstOccuranceBSI(int[] array, int element) {
        int x = -1;
        Arrays.sort(array);
        int low = 0;
        int high = array.length - 1;
        int mid = (low + high) / 2;

        while (low <= high) {
            if (element < array[mid]) {
                high = mid - 1;
                mid = (low + high) / 2;
            } else if (element > array[mid]) {

                low = mid + 1;
                mid = (low + high) / 2;
            } else {
                if (mid == 0 || array[mid - 1] != array[mid])
                    return mid;
                else
                    high = mid - 1;
                mid = (low + high) / 2;
            }
        }
        return x;
    }

    // Binary Search Recursive

    public static int firstOccuranceBSR(int[] array, int element, int low, int high) {
        Arrays.sort(array);

        if (low > high)
            return -1;

        int mid = (low + high) / 2;

        if (element > array[mid]) {
            return firstOccuranceBSR(array, element, mid + 1, high);
        } else if (element < array[mid]) {
            return firstOccuranceBSR(array, element, low, mid - 1);
        } else {
            if (mid == 0 || array[mid - 1] != array[mid])
                return mid;
            else
                return firstOccuranceBSR(array, element, low, mid - 1);
        }
    }
}
