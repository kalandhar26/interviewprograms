package com.ds.Arrays;

import java.util.HashMap;
import java.util.Map;

public class ContiguiousSubArray {

    public static void main(String[] args) {
        int[] array = {1, 1, 0, 0, 1, 1, 0, 0, 1, 1};
        System.out.println(findMaxLength(array));
    }

    public static int findMaxLength(int[] nums) {
        // Map to store the first occurrence of each cumulative sum
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // Initialize with sum 0 at index -1

        int maxLen = 0; // Maximum length of subarray
        int sum = 0;    // Cumulative sum

        for (int i = 0; i < nums.length; i++) {
            // Treat 0 as -1 and 1 as 1
            sum += (nums[i] == 0) ? -1 : 1;

            // If the sum has been seen before, update maxLen
            if (map.containsKey(sum)) {
                maxLen = Math.max(maxLen, i - map.get(sum));
            } else {
                // Otherwise, store the first occurrence of the sum
                map.put(sum, i);
            }
        }

        return maxLen;
    }
}
