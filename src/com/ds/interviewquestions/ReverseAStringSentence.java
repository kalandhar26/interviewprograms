package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReverseAStringSentence {

    public static void main(String[] args) {

        String input ="India is my country";

        // reverse it in such a way along with spaces.
        StringBuilder reverse = new StringBuilder(input).reverse();
        System.out.println(reverse);

        // reverse words wise
        String reverseSentence = Arrays.stream(input.split(" ")).reduce("", (currentWord, result) ->  result+ " " + currentWord).trim();
        System.out.println(reverseSentence);

        // reverse words wise   
        String str[] = input.split(" ");
        String result ="";
        for(int i = str.length-1;i>=0;i--){
            result += str[i]+" ";
        }

        System.out.println(result);

        // 2nd Question
        // Given a List [1,2,3,4,5] and output is [1,4,9,16,25]
        List<Integer> list = Arrays.asList(1,2,3,4,5);

        list.stream().map(s -> s*s).collect(Collectors.toList()).forEach(System.out::println);

        // Sql -> Employee ( id, name, department, salary). Find Highest salaried employee from each department.
        /*

         */

    }
}
