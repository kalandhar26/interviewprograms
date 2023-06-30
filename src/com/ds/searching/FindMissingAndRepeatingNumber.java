package com.ds.searching;

import java.util.Arrays;
import java.util.stream.IntStream;

public class FindMissingAndRepeatingNumber {

    public static void main(String[] args) {

        int[] array = {2, 3, 2, 1, 5,2,6,7,8,8};

        MissingAndRepeatingNumber(array);

    }

    public static void MissingAndRepeatingNumber(int[] array) {
        Arrays.sort(array);

        for (int i = 0; i < array.length - 1; i++) {
            if (i + 1 != array[i]) {
                System.out.println("MissingNumber " +(i+1));
            } else if (array[i] == array[i + 1]) {
                System.out.println("Repeating Number " + array[i]);
                i++;
            }
        }
    }
}
