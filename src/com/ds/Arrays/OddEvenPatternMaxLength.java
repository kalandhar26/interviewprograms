package com.ds.Arrays;

public class OddEvenPatternMaxLength {

    public static void main(String[] args) {
        int[] array = {10, 12, 8, 4};

        System.out.println(MaxLength(array));

    }

    public static int MaxLength(int[] array) {
        int n = array.length;
        int count = 1, result = 0;

        for (int i = 0; i < n - 1; i++) {
            if ((array[i] % 2 == 0 && array[i + 1] % 2 == 1) || (array[i] % 2 == 1 && array[i + 1] % 2 == 0)) {
                count++;
                result = Math.max(count, result);
            } else {
                count = 1;
            }
        }

        return result;
    }
}
