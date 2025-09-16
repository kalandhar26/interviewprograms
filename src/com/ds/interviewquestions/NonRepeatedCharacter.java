package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NonRepeatedCharacter {

    public static void main(String[] args) {
        String input = "Thisisisjavaprogram";
        System.out.println(input.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())).entrySet().stream().filter(entry -> entry.getValue() == 1).map(Map.Entry::getKey).findFirst().orElse(null));

        String  inp = "is that the string is it correct or not";

        String[] array = inp.split(" ");

        Arrays.stream(array).distinct().collect(
                Collectors.groupingBy(
                        word -> String.valueOf(word.charAt(0)), Collectors.toList()
                )).forEach((k,v)-> System.out.println(k+" ->"+v));

        List<String> words = Arrays.asList("apple", "2banana", "apple", "5cherry", "banana", "77apple");

        words.stream().filter( word -> Character.isAlphabetic(word.charAt(0))).forEach(System.out::println);

        List<String> names = Arrays.asList("AA", "BB", "AA", "CC","CC", "BB", "DD");

        names.stream().collect(Collectors.groupingBy(word->word,LinkedHashMap::new, Collectors.counting())).forEach((k,v)-> System.out.println(k+" -> "+v));

    }

}
