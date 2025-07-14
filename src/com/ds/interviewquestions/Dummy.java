package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Dummy {

    public static void main(String[] args) {
        int[] array = {1, 1, 2, 3, 4, 2, 3, 4, 5};
        Arrays.stream(array).boxed().collect(Collectors.groupingBy(n -> n, Collectors.counting())).entrySet().stream().filter(entry -> entry.getValue() == 1).map(Map.Entry::getKey).mapToInt(Integer::valueOf).forEach(System.out::println);
    }
}
