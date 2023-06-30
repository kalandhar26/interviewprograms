package com.ds.searching;

public class BinarySearch {

    public static void main(String[] args) {

        int[] array = {1, 10, 5, 10, 60, 10};

        System.out.println(binarySearch(array, 10));

    }

    // Iterative Solution
    public static int binarySearch(int[] array, int element) {
        int n = array.length;
        int low = 0;
        int mid = n / 2;
        int high = n - 1;

        while (low <= high) {
            if (element == array[mid]) {
                return mid;
            } else if (element < array[mid]) {
                high = mid - 1;
                mid = (low + high) / 2;
            } else {
                low = mid + 1;
                mid = (low + high) / 2;
            }
        }

        return -1;
    }

    // recursive solution

    public static int binarySearchIterative(int[] array, int element, int low, int high) {

        if (low > high)
            return -1;

        int mid = (low + high) / 2;

        if (element == array[mid]) {
            return mid;
        } else if (element < array[mid]) {
            return binarySearchIterative(array, element, low, mid - 1);
        } else {
            return binarySearchIterative(array, element, mid + 1, high);
        }
    }
}
