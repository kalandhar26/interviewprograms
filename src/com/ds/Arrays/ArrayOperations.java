package com.ds.Arrays;

public class ArrayOperations {

    public static void main(String[] args) {

        int[] array = {8, -8, 9, -9, 10, -11, 12};
        System.out.println(mindiffAdjElements(array));
    }

    public static int mindiffAdjElements(int[] array) {
        int result = array[array.length - 1] - array[0];

        for (int i = 1; i < array.length; i++) {
            result = Math.min(array[i] - array[i - 1], result);
        }

        return result;
    }


}
