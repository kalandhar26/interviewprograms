package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UniqueAndDuplicateIntegers {

    public static void main(String[] args) {


        // Input type 1
        List<Integer> inputList = Arrays.asList(1, 2, 3, 2, 2, 4, 5, 3, 6, 7, 8, 7, 9);

        processList(inputList);


        // Input type 2
        Integer[] inputArray = {1, 2, 3, 2, 2, 4, 5, 3, 6, 7, 8, 7, 9};
        int [] inputArray1 = {1,1,2,2,3,0,3};

        findSingleUniqueNumber(inputArray1);

        // Remove Duplicates and Print them
        List<Integer> uniqueElements = inputList.stream()
                .distinct().toList();

        System.out.println("Remove Duplicates elements: " + uniqueElements);

        // Finding duplicates and Print them
        Map<Integer, Long> elementCountMap = inputList.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        List<Integer> duplicateElements = elementCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println("Only Duplicate elements: " + duplicateElements);

        // Print only unique numbers
        List<Integer> uniqueNumbers = inputList.stream()
                .filter(number -> elementCountMap.getOrDefault(number, 0L) == 1)
                .toList();

        System.out.println("Only Unique Numbers: " + uniqueNumbers);
    }

    public static void processList(List<Integer> inputList) {

        // print Only duplicates values
        System.out.println("======Only Duplicate Values========");
        List<String> duplicateValues = inputList.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).map(String::valueOf).toList();

        duplicateValues.forEach(System.out::println);
        // print Only Unique values
        System.out.println("======Only Unique Values========");
        inputList.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() == 1).map(Map.Entry::getKey).map(String::valueOf).forEach(System.out::println);
        // Remove duplicates and print rest
        System.out.println("======Remove Duplicates (Print Duplicate + Unique Values)========");
        inputList.stream().distinct().map(String::valueOf).forEach(System.out::println);

    }

    public static  void findSingleUniqueNumber(int[] array){
        int unique=0;

        for(int x : array){
            unique ^= x;
        }

        System.out.println("===>"+unique);

    }

}
