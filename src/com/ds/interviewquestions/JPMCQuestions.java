package com.ds.interviewquestions;

import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JPMCQuestions {

    public static void main(String[] args) {
        String input = "subscribetoInterviewmania";

        // find How many time each character exists

        input.chars().mapToObj(ch -> Character.toLowerCase((char) ch)).collect(Collectors.groupingBy(Object::toString, Collectors.counting()))
                .forEach((k, v) -> {
                    System.out.println(k + " -> " + v);
                });

        // find Non Repetative (1) element and Repetative ( !=1)

        Character value1 = input.chars().mapToObj(ch -> Character.toLowerCase((char) ch)).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new ,Collectors.counting()))
                .entrySet().stream().filter(value -> value.getValue() == 1).findFirst().get().getKey();

        System.out.println(value1);




    }
}
