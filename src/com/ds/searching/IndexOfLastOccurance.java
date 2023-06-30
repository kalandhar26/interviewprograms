package com.ds.searching;

public class IndexOfLastOccurance {


    public static void main(String[] args) {

        int[] array = {1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 7, 8, 9, 10, 10, 10};

        System.out.println(IOLON(array, 10));
        System.out.println(IOLOI(array, 10));
        System.out.println(IOLOR(array, 10, 0, 15));

    }


    // Naive
    public static int IOLON(int[] array, int element) {
        int x = -1;
        for (int i = array.length - 1; i >= 0; i--) {
            if (element == array[i]) {
                x = i;
                return x;
            }
        }

        return x;
    }
    // BS Iterative

    public static int IOLOI(int[] array, int element) {
        // use Binary Search
        int low = 0, high = array.length, mid = (low + high) / 2;

        while (low <= high) {

            if (element < array[mid]) {
                high = mid - 1;
                mid = (low + high) / 2;
            } else if (element > array[mid]) {
                low = mid + 1;
                mid = (low + high) / 2;
            } else {
                if (mid == array.length - 1 || array[mid + 1] != array[mid]) {
                    return mid;
                } else {
                    low = mid + 1;
                    mid = (low + high) / 2;
                }

            }
        }

        return -1;
    }

    // BS Recursive

    public static int IOLOR(int[] array, int element, int low, int high) {

        if (low > high)
            return -1;
        int mid = (low + high) / 2;

        if (element < array[mid]) {
            return IOLOR(array, element, low, mid - 1);
        } else if (element > array[mid]) {
            return IOLOR(array, element, mid + 1, high);
        } else {
            if (mid == array.length - 1 || array[mid] != array[mid + 1]) {
                return mid;
            } else {
                return IOLOR(array, element, mid + 1, high);
            }
        }

    }
}
