package com.ds.searching;

public class TernarySerach {

    public static void main(String[] args) {

        int[] array = {1, 3, 5, 10, 60, 10};

        System.out.println(ternarySearchRecursive(array, 10,0, array.length-1));

    }


    public static int ternarySearchIterative(int[] array, int element) {
        int low = 0;
        int n = array.length - 1;
        int high = n;

        while (low <= high) {
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;
            if (element == array[mid1])
                return mid1;
            else if (element == array[mid2]) {
                return mid2;
            } else if (element < array[mid1]) {
                high = mid1 - 1;
            } else if (element > array[mid1] && element < array[mid2]) {
                low = mid1 + 1;
                high = mid2 - 1;
            } else {
                low = mid2 + 1;
            }
        }

        return -1;
    }

    public static int ternarySearchRecursive(int[] array, int element, int low, int high) {
        if (low > high)
            return -1;

        int mid1 = low + (high - low) / 3;
        int mid2 = high - (high - low) / 3;

        if (element == array[mid1]) {
            return mid1;
        } else if (element == array[mid2]) {
            return mid2;
        } else if (element < array[mid1]) {
            return ternarySearchRecursive(array, element, low, mid1 - 1);
        } else if (element > array[mid1] && element < array[mid2]) {
            return ternarySearchRecursive(array, element, mid1 + 1, mid2 - 1);
        } else {
            return ternarySearchRecursive(array, element, mid2 + 1, high);
        }
    }

}



