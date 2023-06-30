package com.ds.Arrays;

public class ConsecutiveOnes {

    public static void main(String[] args) {

        int[] array = {0,0,0,0,0,1};
        System.out.println(consecutiveOnes(array));
    }

    public static int consecutiveOnes(int[] array) {
        int n = array.length, result = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (array[i] == 1)
                count++;
            else
                count = 0;

            result = Math.max(result, count);
        }
        return result;
    }
}
