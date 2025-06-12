package com.ds.interviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UniqueAndDuplicateIntegers {

    public static void main(String[] args) {


        // Input type 1
        List<Integer> inputList = Arrays.asList(1, 2, 3, 2,2, 4, 5, 3, 6, 7, 8, 7, 9);

        // Input type 2
        Integer[] inputArray = {1, 2, 3, 2,2, 4, 5, 3, 6, 7, 8, 7, 9};

        // Remove Duplicates and Print them
        List<Integer> uniqueElements = inputList.stream()
                .distinct().toList();

        System.out.println("Unique elements: " + uniqueElements);

        // Finding duplicates and Print them
        Map<Integer, Long> elementCountMap = inputList.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        List<Integer> duplicateElements = elementCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println("Duplicate elements: " + duplicateElements);

        // Print only unique numbers
        List<Integer> uniqueNumbers = inputList.stream()
                .filter(number -> elementCountMap.getOrDefault(number, 0L) == 1)
                .toList();

        System.out.println("Only Unique Numbers: " + uniqueNumbers);
    }

    public static void processList(List<Integer> inputList){

       // print Only duplicates values

        // print Only Unique values

        // Remove duplicates and print restYesPl


    }

}
