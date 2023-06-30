package com.ds.Arrays;

public class LargestElementinArray {

    public static void main(String[] args) {

        int array[] = {19, 10, 21, 1, 43, 78, 98, 67, 45, 32, 62};

        System.out.println(largestElement(array) + " " + largestElementIndex(array));
        System.out.println(getLargestIndexValue(array) + " " + getLargestIndex(array));

    }

    private static int largestElement(int[] array) {
        int i;
        int largest = 0;

        if (array.length <= 1)
            return array[0];

        for (i = 0; i < (array.length) - 1; i++) {
            if (array[i] >= array[i + 1] && largest <= array[i])
                largest = array[i];
            else if (array[i] <= array[i + 1] && largest <= array[i + 1])
                largest = array[i + 1];
        }

        return largest;
    }

    private static int largestElementIndex(int[] array) {

        int i;
        int index = 0, largest = 0;
        if (array.length <= 1)
            return 0;

        for (i = 0; i < (array.length) - 1; i++) {
            if (array[i] >= array[i + 1] && largest <= array[i]) {
                largest = array[i];
                index = i;
            } else if (array[i] <= array[i + 1] && largest <= array[i + 1]) {
                largest = array[i + 1];
                index = i + 1;
            }
        }

        return index;
    }

    // Naive Solution

    public static int getLargestIndex(int array[]) {
        int res = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[res]) {
                res = i;

            }
        }

        return res;
    }

    // wrong code
    private static int getLargestIndexValue(int array[]) {
        int res = 0;
        for (int i = 1; i < array.length-1; i++) {
            if (array[i] > array[res]) {
                res = i;
            }
        }

        return array[res];
    }
}
