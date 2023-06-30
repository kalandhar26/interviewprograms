package com.ds.hashing;

import java.util.HashSet;

public class UnioinOfTwoArrays {

    public static void main(String[] args) {

        int[] array1 = {1, 1, 1, 1, 2};
        int[] array2 = {1, 1, 1, 1, 1};

        System.out.println(unioinTwoArrays(array1, array2));
        System.out.println(naiveUnioinOfTwoArrays(array1, array2));

    }

    // Naive Solution
    public static int naiveUnioinOfTwoArrays(int[] array1, int[] array2) {

        int m = array1.length, n = array2.length;

        int[] array = new int[m + n];

        // place All elements in one Array
        for (int i = 0; i < m; i++) {
            array[i] = array1[i];
        }

        for (int i = 0; i < n; i++) {
            array[i+m] = array2[i];
        }

        // ================ Get distinct element ====================

        int count = 0;

        for (int i = 0; i < (m+n); i++) {
            boolean flag = false;
            for (int j = 0; j < i; j++) {
                if (array[i] == array[j]) {
                    flag = true;
                    break;
                }
            }

            if (flag == false)
                count++;
        }

        return  count;
    }


    // Efficient Solution
    public static int unioinTwoArrays(int[] array1, int[] array2) {

        HashSet<Integer> set = new HashSet<>();

        for (int x : array1) {
            set.add(x);
        }

        for (int x : array2) {
            set.add(x);
        }

        return set.size();
    }
}
