package com.ds.Arrays;

import java.util.*;

public class SuminArray {

    public static void main(String[] args) {
        int[] array = {1, 3, 2, 7, 9};
        int sum = 9;


//        int[] result = suminArray(array, sum);
//        int[] result1 = suminArray1(array, sum);
        List<int[]> result3 = findSum(array,sum);

        for(int[] x : result3){
            System.out.println(Arrays.toString(x));
        }


//        for (int x : result)
//            System.out.println(x);
//
//        for (int x : result1)
//            System.out.println(x);
    }


    public static int[] suminArray(int[] array, int sum) {
        int[] array1 = new int[2];

        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < array.length; j++) {
                if (array[i] + array[j] == sum) {
                    array1[0] = i;
                    array1[1] = j;
                }
            }
        }
        return array1;
    }


    public static int[] suminArray1(int[] array, int sum) {
        int[] array1 = new int[2];
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(sum - array[i])) {
                array1[0] = map.get(sum - array[i]);
                array1[1] = i;
            } else {
                map.put(array[i], i);
            }
        }

        if (map.size() >= array.length) {
            System.out.println("Elements Cannot match to get desired Sum");
        }

        return array1;
    }


    public static List<int[]> findSum(int[] array, int sum) {
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            findSumRecursive(array, i, sum, new ArrayList<>(), result);
        }

        return result;
    }

    private static void findSumRecursive(int[] array, int currentIndex, int remainingSum, List<Integer> currentList, List<int[]> result) {
        if (remainingSum == 0) {
            result.add(currentList.stream().mapToInt(Integer::intValue).toArray());
            return;
        }

        if (currentIndex >= array.length || remainingSum < 0) {
            return;
        }

        // Include current element
        currentList.add(currentIndex);
        findSumRecursive(array, currentIndex + 1, remainingSum - array[currentIndex], currentList, result);
        currentList.remove(currentList.size() - 1);

        // Exclude current element
        findSumRecursive(array, currentIndex + 1, remainingSum, currentList, result);
    }

}
