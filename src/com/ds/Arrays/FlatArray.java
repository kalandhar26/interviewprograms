package com.ds.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlatArray {

    public static void main(String[] args) throws Exception {
        Object[] array = { 1, 2, new Object[]{ 3, 4, new Object[]{ 5 }, 6, 7 }, 8, 9, 10 };

        Integer[] flattenedArray = flatten(array);

        System.out.println(Arrays.toString(flattenedArray));
    }

    public static Integer[] flatten(Object[] inputArray) {
        List<Integer> resultList = new ArrayList<>();
        flattenArray(inputArray, resultList);
        return resultList.toArray(new Integer[0]);
    }

    private static void flattenArray(Object[] inputArray, List<Integer> resultList) {
        for (Object item : inputArray) {
            if (item instanceof Integer integer) {
                resultList.add(integer);
            } else if (item instanceof Object[] innerArray) {
                flattenArray(innerArray, resultList);
            }
        }
    }
}
