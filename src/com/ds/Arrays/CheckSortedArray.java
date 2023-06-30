package com.ds.Arrays;

public class CheckSortedArray {

    public static void main(String[] args) {

        int array[] = {20,20,20,5};

        System.out.println(checkArraySorted(array));

    }

    public static boolean checkArraySorted(int[] array) {
        for (int i = 0; i < array.length-1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }
}
