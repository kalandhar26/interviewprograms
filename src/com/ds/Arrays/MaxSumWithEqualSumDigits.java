package com.ds.Arrays;

import java.util.HashMap;
import java.util.Map;

public class MaxSumWithEqualSumDigits {
    public static void main(String[] args) {
        int[] array = {18,43,36,13,7,27};
        System.out.println(maxSumPairWithEqualSumDigits(array));
    }

    public static int maxSumPairWithEqualSumDigits(int[] array) {
        int maxSum = -1;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i : array) {
            int sum = currentSumofDigits(i);
            if (map.containsKey(sum)) {
                maxSum = Math.max(maxSum, i + map.get(sum));
                if (i > map.get(sum)) {
                    map.replace(sum, i);
                }
            } else {
                map.put(sum, i);
            }
        }
        return maxSum;
    }

    private static int currentDigitSum(int i) {
        if (i == 0)
            return 0;
        else
            return currentDigitSum(i / 10) + i % 10;
    }

    private static int currentSumofDigits(int i) {
        return i > 0 ? currentSumofDigits(i / 10) + i % 10 : 0;
    }
}
