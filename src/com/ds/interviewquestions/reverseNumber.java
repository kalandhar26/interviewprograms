package com.ds.interviewquestions;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class reverseNumber {

    public static void main(String[] args) {
        int number = 123845;

        int reverseNumber = 0;

        while (number != 0) {
            int lastDigit = number % 10;
            reverseNumber = reverseNumber * 10 + lastDigit;
            number = number / 10;
        }

        System.out.println(reverseNumber);

        // Reverse a number
        int collect = Stream.of(String.valueOf(number).split(""))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.reverse(list);
                    return list.stream().reduce(0, (result, currentDigit) -> result * 10 + currentDigit);
                }));


        System.out.println(collect);

        // Reverse a String

        String input = "BabaKalandhar";
        String reverseString = Stream.of(input).map(str -> new StringBuilder(str)).collect(Collectors.joining());
        System.out.println(reverseString);

    }


}
