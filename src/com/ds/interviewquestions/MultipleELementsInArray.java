package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultipleELementsInArray {

    public static void main(String[] args) {

        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        Arrays.stream(array).mapToObj(e -> e * 5).collect(Collectors.toList()).forEach(System.out::println);

        // Multiply all elements in Array
        int reduce = Arrays.stream(array).reduce((a, b) -> {
            return a * b;
        }).getAsInt();

        System.out.println("Multiplication of All Elements "+reduce);

        // Add All Elements in array
        System.out.println("Sum of All Elements "+Arrays.stream(array).reduce((a, b) -> a + b).getAsInt());


    }
}
