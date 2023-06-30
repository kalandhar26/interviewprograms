package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AddArrayElements {

    public static void main(String[] args) {
        int[] array = {1, 2, 34, 5, 6, 7, 8};

        int[] array2 = {1, 2, 5, 6,8};

        System.out.println(Arrays.stream(array).sum());

        commonElementsInArray(array,array2);

    }

    public static void commonElementsInArray(int[] array1, int[] array2) {

        Set<Integer> set = new HashSet<Integer>();

        for (int i = 0; i < array2.length; i++) {
            for (int j = 0; j < array1.length; j++) {
                if (array2[i] == array1[j]) {
                    set.add(array2[i]);
                }
            }
        }

        for (Integer i : set) {
            System.out.println(i);
        }

    }
}
