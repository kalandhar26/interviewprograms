package com.ds.interviewquestions;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UniqueAndDuplicateInString {

    public static void main(String[] args) {

        String inputString = "aabbccdeeeffghiii";

        // find Duplicates and Print Them
        String findDupliacteAndPrint = inputString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .map(String::valueOf)
                .collect(Collectors.joining());

        // Remove duplicate and print String
        String removeDupliacteAndPrint =inputString.chars()
                .distinct()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .collect(Collectors.joining());

        // find Only Unique elements
        String findUniqueAndPrint =inputString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .map(String::valueOf)
                .collect(Collectors.joining());

        System.out.println(findDupliacteAndPrint);
        System.out.println(removeDupliacteAndPrint);
        System.out.println(findUniqueAndPrint);

        // old way

        Set<Character> unique = new HashSet<>();
        Set<Character> duplicates = new HashSet<>();

        for(int i = 0; i < inputString.length(); i++){
            Character c = inputString.charAt(i);
            if(!unique.contains(c)) {
                unique.add(c);
            }
            else{
                duplicates.add(c);
            }
        }

        System.out.println(unique);
        System.out.println(duplicates);

    }
}



