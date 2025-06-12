package com.ds.Arrays;

/**
 * Given an integer array nums,
 * find the maximum sum of a subarray that can be achieved even when there are negative numbers in the array.
 */
public class MaxSumOfSubArrayswithNegativeNumbers {

    public static void main(String[] args) {
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSum(array));
    }

    public static int maxSum(int[] array){
        int currentSum = array[0];
        int maxSum = array[0];

        for(int i=1;i<array.length;i++){
            // Decide whether to start a new subarray or continue the current one
            currentSum = Math.max(array[i],currentSum+array[i]);
            // Update the maximum sum if the current subarray sum is greater
            maxSum = Math.max(currentSum,maxSum);
        }
        return maxSum;
    }
}
