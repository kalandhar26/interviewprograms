package com.ds.Arrays;

import java.util.HashMap;
import java.util.Map;

public class RabbitsInForest {

    public static void main(String[] args) {
        int[] array = {1,1,1,2};
        System.out.println(numRabbits(array));
        System.out.println(noOfRabbits(array));
    }

    public static int noOfRabbits(int[] answers) {
        int total = 0;
        int[] count = new int[1000];
        for (int k : answers) {
            int groupSize = k+1;
            if (count[k] % groupSize == 0) {
                total = total +groupSize;
            }
            count[k]++;
        }
        return total;
    }


    public static int numRabbits(int[] answers) {
        if (answers == null || answers.length == 0) {
            return 0;
        }

        Map<Integer, Integer> countMap = new HashMap<>();

        for (int k : answers) {
            countMap.put(k, countMap.getOrDefault(k, 0) + 1);
        }

        int total = 0;

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            int k = entry.getKey();
            int v = entry.getValue();

            int groupSize = k + 1;

            int groups = (v + groupSize - 1) / groupSize;

            total += groups * groupSize;
        }

        return total;
    }
}

