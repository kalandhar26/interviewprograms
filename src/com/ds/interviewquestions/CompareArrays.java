package com.ds.interviewquestions;

import java.util.Arrays;

public class CompareArrays {

    public static void main(String[] args) {
        int[] array1 = {1, 2, 4, 6};
        int[] array2 = {5, 2, 4, 1};
        if (compareArrays(array1, array2)) {
            System.out.println("Arrays are Equal");
        } else {
            System.out.println("UnEqual Arrays");
        }

    }

    public static boolean compareArrays(int[] array1, int[] array2) {

        if (array1.length != array2.length)
            return false;

        Arrays.sort(array1);
        Arrays.sort(array2);

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i])
                return false;
        }

        return true;
    }
}
