package com.ds.Arrays;

import java.util.Arrays;

/**
 * Given an integer array nums, find the sum of the elements between indices i and j (inclusive)
 * for multiple queries. You need to implement the method sumRange(i, j) that returns the sum of
 * elements between indices i and j inclusive.
 */
public class PrefixSum {

    public static void main(String[] args) {
        int[] array = {1, 6, -7, 8, 4, -5, 4, 0, -1};
        System.out.println(prefixSum(array, 2, 8));
        System.out.println(prefixSum1(array, 2, 8));
        int[][] queries = {{1,3},{2,6},{3,5},{0,5}};
        int[] result = prefixSumArray2D(array,queries);
        System.out.println(Arrays.toString(result));
    }

    public static int prefixSum(int[] array, int start, int end) {
        int n = array.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = array[i] + (i > 0 ? result[i - 1] : 0);
        }
        int sum = result[end] - (start > 0 ? result[start - 1] : 0);

        return sum;

    }

    public static int prefixSum1(int[] array, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum = sum + array[i];
        }
        return sum;
    }

    public static int[] prefixSumArray2D(int[] array, int[][] queries) {
        int n = array.length;
        int[] prefix = new int[n];
        int[] results = new int[queries.length];

        prefix[0] = array[0];

        for (int i = 1; i < array.length; i++) {
            prefix[i] = prefix[i - 1] + array[i];
        }

        for (int k = 0; k < queries.length; k++) {
            int i = queries[k][0];
            int j = queries[k][1];
            results[k] = (i == 0) ? prefix[j] : prefix[j] - prefix[i - 1];
        }
        return results;
    }
}
