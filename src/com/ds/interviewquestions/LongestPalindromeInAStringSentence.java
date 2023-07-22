package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LongestPalindromeInAStringSentence {

    public static void main(String[] args) {

        String input = "My name is Baba , lkjhgfdsaasdfghjkl is jkllkj gynmnyg uiopoiu";
        String[] s = input.split(" ");
        List<String> list = Arrays.asList(s);
        List<String> sortedList = list.stream().sorted(Comparator.comparingDouble(e -> e.length())).collect(Collectors.collectingAndThen(Collectors.toList(),resultList -> {
            Collections.reverse(resultList);
            return  resultList;
        }));

        for(String e : sortedList){
            String result ="";
            for(int i=0;i<e.length();i++){
                result = e.charAt(i)+result;
            }
            if(e.equalsIgnoreCase(result)) {
                System.out.println(e + " is a longest palindrome");
                break;
            }
        }

        // finding duplicates in String
       list.stream().filter(dup -> list.stream().filter(dup1 -> dup.equalsIgnoreCase(dup1)).count() > 1).distinct().collect(Collectors.toList()).forEach(System.out::println);
    }
}
