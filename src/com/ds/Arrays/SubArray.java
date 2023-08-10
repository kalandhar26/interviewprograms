package com.ds.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubArray {

    public static void main(String[] args) {

        int[] original = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int splitSize = 3;
        /* expected Output [0, 1, 2] [3, 4, 5] [6, 7, 8] [9] */
        List<int[]> list = getSubArrays(original, splitSize);
        list.forEach(splitArray -> System.out.println(Arrays.toString(splitArray)));

    }

    public static List<int[]> getSubArrays(int[] array, int splitSize) {

        List<int[]> resultList = new ArrayList<>();

        for (int i = 0; i < array.length; i += splitSize) {
            int subArrayLength = Math.min(splitSize, array.length - i);
            int[] subArray = new int[subArrayLength];

            for (int j = 0; j < subArrayLength; j++) {
                subArray[j] = array[i + j];
            }

            resultList.add(subArray);
        }

        return resultList;

    }

}
