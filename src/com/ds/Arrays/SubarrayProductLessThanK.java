package com.ds.Arrays;

public class SubarrayProductLessThanK {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};
        int k = 4;
        System.out.println(subarrayProductLessThanK(array, k));

    }

    public static int subarrayProductLessThanK(int[] array, int k) {
        if (k <= 1) return 0;

        int count = 0;
        int product = 1;
        int left = 0;

        for (int i = 0; i < array.length; i++) {
            product = product * array[i];

            while (product >= k) {
                product = product / array[left];
                left++;
            }

            count = count + i - left + 1;
        }

        return count;
    }
}
