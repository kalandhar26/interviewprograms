package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TechMQuestions {

    public static void main(String[] args) {

        String input = "I am a fool. aio babakalandhar.";

        String cleanInput = input.replaceAll("[]\\s\\p{Punct}]", "");

        // find vowels in the String

        int count = 0;
        for (int i = 0; i < cleanInput.length(); i++) {
            Character c = cleanInput.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                System.out.println(c);
                count++;
            }
        }

        System.out.println(count);

        // using Streams
        long countVowels = cleanInput.chars().filter((value) -> {
            return value == 'a' || value == 'e' || value == 'i' || value == 'o' || value == 'u';
        }).count();

        long count1 = cleanInput.chars().mapToObj(Character::toLowerCase).filter(ch -> "aeiou".contains(Character.toString(ch))).count();


        System.out.println(countVowels + " " + count1);

        // find duplicates

        System.out.println("========= duplicates =============");
        String output = "";

        for (int i = 0; i < cleanInput.length(); i++) {
            Character c = cleanInput.charAt(i);

            if (!output.contains(c.toString())) {
                output += c;
                continue;
            } else {
                System.out.println(c);
            }

        }


        // find duplicates using Streams
        System.out.println("==== dup str ========");
        cleanInput.chars().mapToObj(ch -> (char) ch).filter(dup ->
                cleanInput.chars().filter(dup1 -> dup == dup1).count() > 1).distinct().forEach(System.out::println);

        cleanInput.chars().mapToObj(ch -> (char) ch)
                // This line collects the char amd Map char with Count
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()))
                // This like filter only duplicate value
                .entrySet().stream().filter(entry -> entry.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                // This line print the Map
                .forEach((k, v) -> {
                    System.out.println(k + " " + v);
                });

        //

        cleanInput.chars().mapToObj(ch -> (char) ch).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue()>1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .forEach((k, v) -> {
                    System.out.println(k + " --> " + v);
                });


    }
}
