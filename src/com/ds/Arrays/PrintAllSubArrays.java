package com.ds.Arrays;

public class PrintAllSubArrays {

    public static void main(String[] args) {

        int array[] = {-1, -2, -3};

        printAllSubArrays(array);

    }

    public static void printAllSubArrays(int[] array) {
        int n = array.length;

        for (int i = 0; i < n; i++) { // This loop select start element of sub array
            for (int j = i; j < n; j++) { // This loop select end element of sub array
                for (int k = i; k <= j; k++) // This loop prints sub arrays
                    System.out.print(array[k] + " ");
                System.out.println();
            }
        }
    }
}
