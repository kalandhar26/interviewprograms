package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RemoveEmptyStringsFromArray {

    public static void main(String[] args) {

        String[] array = {"ABC", "DFG", "ABCCG", "", "NJK", "KJL", "", "JIO"};

        for (String e : Arrays.stream(array).filter(str -> !str.isEmpty()).toArray(String[]::new)) {
            System.out.println(e);
        }

        // Reverse Elements in Desc Order
        Integer[] array2 = {6,5,4,3,2,1,6,7};

        Integer[] integers = Arrays.stream(array2).sorted(Comparator.reverseOrder()).toArray(Integer[]::new);

        for (Integer i : integers)
            System.out.print(" "+i);

        int length = array2.length;

        // Normal Code
      IntStream.range(0, length - 1).flatMap(i -> IntStream.range(0, length - i - 1).filter(j -> array2[j] < array2[j + 1]).peek(j -> {
            int temp = array2[j];
            array2[j] = array2[j + 1];
            array2[j + 1] = temp;
        })).count();

      for(int i : array2)
          System.out.print(" "+i);


    }
}
