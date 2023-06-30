package com.ds.hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class IntersectionOftwoArrays {

    public static void main(String[] args) {

        int[] array1 = {5, 6, 1, 2, 6, 8, 9, 2, 3, 4, 1};
        int[] array2 = {1, 5, 6, 7, 8, 9, 2};

        System.out.println(intersectionOfArrays(array1, array2));

        System.out.println(intersectOfArrays2(array1, array2));

        Integer[] inter = inter(array1, array2);

        for (int x : inter)
            System.out.println(x);
    }

    // Naive
    public static int intersectionOfArrays(int[] array1, int[] array2) {

        int result = 0;

        // This loops traverse element by element of array1
        for (int i = 0; i < array1.length; i++) {
            boolean flag = false;
            // This loop check if it found the same element backside
            for (int j = 0; j < i; j++) {
                if (array1[i] == array1[j]) {
                    // if element found it set  flag to true
                    flag = true;
                    break;
                }
            }
            // we are skipping the duplicate element by below condition
            if (flag == true) {
                continue;
            }
            // Now this loop checking it found any common elements in both arrays.
            for (int x = 0; x < array2.length; x++) {
                if (array1[i] == array2[x]) {
                    result++;
                    break;
                }
            }
        }

        return result;
    }

    // Efficient solution

    public static int intersectOfArrays2(int[] array1, int[] array2) {
        int result = 0;

        HashSet<Integer> set = new HashSet<>();

        // This loop adds all element of first array into set ( where duplicates are not allowed
        for (int x : array1)
            set.add(x);
        // This loop check if set contains any element of array2
        for (int x : array2) {
            if (set.contains(x)) {
                // if element is found it set the value as 1 and removes the element from set.
                result++;
                set.remove(x);
            }
        }
        return result;
    }

    public static Integer[] inter(int[] nums1, int[] nums2) {

        HashSet<Integer> set = new HashSet<>();

        for (int x : nums1) {
            set.add(x);
        }

        List<Integer> list = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < nums2.length; i++) {
            if (set.contains(nums2[i])) {
                list.add(nums2[i]);
                set.remove(nums2[i]);
            }
        }

        return list.toArray(new Integer[0]);
    }
}
