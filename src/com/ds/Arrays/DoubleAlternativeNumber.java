package com.ds.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class DoubleAlternativeNumber {

    public static void main(String[] args) {
        int[] array = {2,5,9,11,24,36};
        List<Integer> list = new ArrayList<>(Arrays.asList(2,5,9,11,23,36));

        int[] array1 = IntStream.range(0, array.length).filter(i -> i % 2 == 0).map(i -> array[i] *2).toArray();
        List<Integer> list1 = IntStream.range(0, list.size()).filter(i -> i % 2 == 0).map(i -> list.get(i) *2).boxed().toList();
        for(int x: array1) {
            System.out.println(x);
        }
    }
}
