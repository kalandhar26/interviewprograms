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

}
