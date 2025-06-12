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
        System.out.println("+++++++++++++");
        List<int[]> list1 = getSubArrays1(original, splitSize);
        list1.forEach(splitArray -> System.out.println(Arrays.toString(splitArray)));
        System.out.println("+++++++++++++");
        List<int[]> list2 = getCircularSubArrays(original, splitSize);
        list2.forEach(splitArray -> System.out.println(Arrays.toString(splitArray)));
        System.out.println("+++++++++++++");
        List<int[]> list3 = getAllSubArrays(original);
        list3.forEach(splitArray -> System.out.println(Arrays.toString(splitArray)));
    }

    public static List<int[]> getSubArrays(int[] array, int splitSize) {

        if (array == null || splitSize <= 0 || splitSize > array.length) {
            throw new IllegalArgumentException("Invalid array or subarray length");
        }
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

    public static List<int[]> getSubArrays1(int[] array, int splitSize) {

        if (array == null || splitSize <= 0 || splitSize > array.length) {
            throw new IllegalArgumentException("Invalid array or subarray length");
        }

        List<int[]> resultList = new ArrayList<>();

        for (int i = 0; i < array.length - splitSize; i++) {

            int[] subArray = new int[splitSize];

            for (int j = 0; j < splitSize; j++) {
                subArray[j] = array[i + j];
            }

            resultList.add(subArray);
        }

        return resultList;

    }

    public static List<int[]> getCircularSubArrays(int[] array, int splitSize) {

        if (array == null || splitSize <= 0 || splitSize > array.length) {
            throw new IllegalArgumentException("Invalid array or subarray length");
        }

        List<int[]> resultList = new ArrayList<>();
        int n = array.length;

        for (int i = 0; i < n; i++) {

            int[] subArray = new int[splitSize];

            for (int j = 0; j < splitSize; j++) {
                subArray[j] = array[(i + j) % n];
            }

            resultList.add(subArray);
        }

        return resultList;

    }

    public static List<int[]> getAllSubArrays(int[] array) {

        if (array == null) {
            throw new IllegalArgumentException("Invalid array or subarray length");
        }
        List<int[]> resultList = new ArrayList<>();

        int n = array.length;

        int totalNumberOfSubArrays = n * (n+1)/2;
        System.out.println("Total SubArrays: "+totalNumberOfSubArrays);

        for(int start=0;start<n;start++){
            for(int end=start;end<n;end++){
                int subArraySize = end-start+1;
                int[] subArray = new int[subArraySize];

                for(int begin=0;begin<subArraySize;begin++){
                    subArray[begin]=array[begin+start];
                }
                resultList.add(subArray);
            }
        }

        return resultList;

    }


}
