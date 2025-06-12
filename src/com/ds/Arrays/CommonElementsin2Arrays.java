package com.ds.Arrays;

import java.util.*;

public class CommonElementsin2Arrays {

    public static void main(String[] args) {
        int[] array1 = {1, 4, 2, 5, 9, 3,4};
        int[] array2 = {1, 7, 4, 7};

        System.out.println(Arrays.toString(commonArrays(array1, array2)));
    }

    public static int[] commonArrays(int[] array1, int[] array2) {

        Set<Integer> set = new HashSet<>();

        for (int i : array1) {
            set.add(i);
        }
        List<Integer> commonelements = new ArrayList<>();
        for (int i : array2) {
            if (set.contains(i)) {
                commonelements.add(i);
            }
        }
        int[] result = new int[commonelements.size()];
        for(int i=0;i< result.length;i++){
            result[i] = commonelements.get(i);
        }

        return result;
    }
}
