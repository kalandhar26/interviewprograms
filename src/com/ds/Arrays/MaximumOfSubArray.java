package com.ds.Arrays;

public class MaximumOfSubArray {

    public static void main(String[] args) {
        int array[] = {-1,2,-3,-1};

        System.out.println(maximumSum(array));

        System.out.println(maximumSumNaive(array));

        System.out.println(minimumSum(array));

    }

    // When all elements are < 0 then it returns 0
    public static int maximumSum(int[] array) {
        int n = array.length, max_sum = array[0], current_sum = array[0];
        for (int i = 1; i <n; i++) {
            current_sum = Math.max(array[i], current_sum + array[i]);
            max_sum = Math.max(max_sum, current_sum);
        }
        return max_sum;
    }

    // Naive Solution
    public static int maximumSumNaive(int[] array) {
        int max_sum = array[0], n = array.length;

        for (int i = 0; i < n; i++) {
            int current_sum = 0;
            for (int j = i; j < n; j++) {
                current_sum = current_sum + array[j];
                max_sum = Math.max(max_sum, current_sum);
            }
        }

        return max_sum;
    }

    // Efficient Solution (min Sum)
    public static int minimumSum(int[] array){
        int n = array.length, currentSum=array[0],min_sum=array[0];

        for(int i=1;i<n;i++){
            currentSum = Math.min(array[i], currentSum+array[i]);
            min_sum=Math.min(min_sum,currentSum);
        }

        return  min_sum;
    }

    // Max_Sum (index)
    public static int[] maximumSumSubarrayIndices(int[] array) {
        int n = array.length;
        int max_sum = array[0];
        int current_sum = array[0];
        int start = 0;  // Start index of the current subarray with max sum
        int startIndex = 0;  // Start index of the subarray with max sum
        int endIndex = 0;    // End index of the subarray with max sum

        for (int i = 1; i < n; i++) {
            if (array[i] > current_sum + array[i]) {
                // If the current element is greater than the current_sum + array[i],
                // start a new subarray.
                current_sum = array[i];
                start = i;
            } else {
                // Extend the current subarray.
                current_sum = current_sum + array[i];
            }

            if (current_sum > max_sum) {
                // Update the max_sum and the corresponding indices.
                max_sum = current_sum;
                startIndex = start;
                endIndex = i;
            }
        }

        int[] result = new int[]{startIndex, endIndex};
        return result;
    }

}
