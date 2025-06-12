package com.ds.interviewquestions;

import java.util.Arrays;

public class ArrayEvenOrOdd {

    public static void main(String[] args) {
        int[] array = {1,7,8,9,0,6,3,4,5};
        Arrays.stream(array).filter(number -> number % 2 !=0).forEach(System.out::println);
    }
}
