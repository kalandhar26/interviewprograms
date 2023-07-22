package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UniqueAndDuplicateIntegers {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 2, 4, 5, 3, 6, 7, 8, 7, 9);

        // Finding unique elements
        List<Integer> uniqueElements = numbers.stream()
                .distinct()
                .toList();

        System.out.println("Unique elements: " + uniqueElements);

        // Finding duplicate elements
        Map<Integer, Long> elementCountMap = numbers.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        List<Integer> duplicateElements = elementCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println("Duplicate elements: " + duplicateElements);
    }
}
