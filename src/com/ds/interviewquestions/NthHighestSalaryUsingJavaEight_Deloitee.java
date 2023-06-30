package com.ds.interviewquestions;

import java.util.*;
import java.util.stream.Collectors;

public class NthHighestSalaryUsingJavaEight_Deloitee {

    public static void main(String[] args) {

        Integer[] array = {7, 9, 8, 7, 10, 16, 18, 19, 25};

        Arrays.stream(array).sorted().distinct().collect(Collectors.collectingAndThen(Collectors.toList(), result -> {
            Collections.reverse(result);
            return result;
        })).stream().skip(1).limit(1).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println(nthHighestNumber(array,90));


    }

    public static Integer nthHighestNumber(Integer[] array, int n) {
        Integer first = Arrays.stream(array).distinct().sorted(Comparator.reverseOrder()).skip(n - 1).findFirst().orElse(null);
        return first;
    }


}
