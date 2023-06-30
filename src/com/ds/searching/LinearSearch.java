package com.ds.searching;

public class LinearSearch {

    public static void main(String[] args) {
        int[] array = {1, 4, 6, 8, 9, 11, 15};
        System.out.println(linearSearch(array, 15));
    }

    public static boolean linearSearch(int[] array, int element) {
        int n = array.length;

        for (int i = 0; i < n; i++) {
            if (array[i] == element) {
                System.out.println("Element " + element + " found at index " + i);
                return true;
            }
        }
        return false;
    }
}
