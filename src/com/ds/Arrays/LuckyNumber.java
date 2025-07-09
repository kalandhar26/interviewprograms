package com.ds.Arrays;

import java.util.HashMap;
import java.util.Map;

public class LuckyNumber {
    public static void main(String[] args) {
        int[] array = {1, 2, 2, 3, 4, 5};
        System.out.println(getLuckyNumber(array));
    }

    public static int getLuckyNumber(int[] array) {

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : array) {
            frequencyMap.merge(num, 1, Integer::sum);
        }
        return frequencyMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(entry.getValue()))
                .mapToInt(Map.Entry::getKey).max().orElse(-1);
    }
}
