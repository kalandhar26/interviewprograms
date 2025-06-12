package com.ds.Arrays;

/**
 * Given an array nums of size n, find the maximum sum of a subarray of size k.
 */
public class MaxSumOfSubArrayOfSize {

    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7};
        int size =6;
        System.out.println(findMaximum(array,size));
    }

    public static int findMaximum(int[] array, int size) {

        if(array.length == 0 || size > array.length || size<0){
            return -1;
        }

        int windowSum = 0;
        int maxSum = 0;

        for(int i=0;i<size;i++){
            windowSum = windowSum + array[i];
        }
        maxSum = windowSum;

        for (int i = size; i < array.length; i++) {
            windowSum = windowSum + array[i] - array[i - size];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }
}
