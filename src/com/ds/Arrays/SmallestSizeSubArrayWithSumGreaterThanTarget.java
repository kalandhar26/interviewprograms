package com.ds.Arrays;

public class SmallestSizeSubArrayWithSumGreaterThanTarget {

    public static void main(String[] args) {
        int[] array = {1, 4, 44, 8, 0, 19};
        int target = 18;
        smallestSizeSubArrayWithSumGreaterThanTarget(array,target);
    }

    public static void smallestSizeSubArrayWithSumGreaterThanTarget(int[] array, int target) {
        int minLength = Integer.MAX_VALUE;
        int currentSum = 0;
        int left = 0;

        for (int right = 0; right < array.length; right++) {
            currentSum = currentSum + array[right];

            while(currentSum > target) {
                minLength = Math.min(minLength, right - left + 1);
                currentSum = currentSum - array[left];
                left++;
            }
        }

        System.out.println(minLength == Integer.MAX_VALUE ? 0 : minLength);
    }
}
