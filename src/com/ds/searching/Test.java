package com.ds.searching;

public class Test {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 4, 6, 2, 3, 7, 8, 9};
        int k = 3;

        System.out.println(maxSumSubarray(arr, k));
    }

    public static int maxSumSubarray(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int maxSum = 0;
        int currentSum = 0;

        // Calculate the sum of the first subarray of length k
        for (int i = 0; i < k; i++) {
            currentSum += arr[i];
        }

        // Initialize maxSum with the sum of the first subarray
        maxSum = currentSum;

        // Slide the window through the array and find the maximum sum
        for (int i = k; i < arr.length; i++) {
            currentSum = currentSum - arr[i - k] + arr[i];
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }
}
