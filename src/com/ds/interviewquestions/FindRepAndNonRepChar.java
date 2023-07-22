package com.ds.interviewquestions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FindRepAndNonRepChar {

    public static void main(String[] args) {

        String input = "hhello world";


        // Repetative and Non Repetative Characters

        Map<Character, Long> charCount = input.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        // Repetitive
        List<Character> repetitiveCharacters = input.chars().mapToObj(c -> (char) c).filter(c -> charCount.get(c) > 1).collect(Collectors.toList());
        System.out.println(repetitiveCharacters.stream().findFirst().get());

        // Non Repetitive
        List<Character> NonRepetitiveCharacters = input.chars().mapToObj(c -> (char) c).filter(c -> charCount.get(c) == 1).collect(Collectors.toList());
        System.out.println(NonRepetitiveCharacters.stream().findFirst().get());

        // Another Way first Repeatative

        Character character = input.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).findFirst().get();

        System.out.println(character);


        // first Non Repeatative

        Character character1 = input.chars().mapToObj(c -> (char) c).filter(c -> charCount.get(c) == 1).findFirst().get();
        System.out.println(character1);
    }


}
