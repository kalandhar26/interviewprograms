package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountEachWordInASentenceIgnoreCase {

    public static void main(String[] args) {
        String strSentence = " HI EPAM bYe EPAM goodbye EPAM welcome ePAM Hi There epAM BYE bye EPaM";

        String[] s = strSentence.split(" ");

        Map<String, Long> map = Arrays.stream(s).collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));

        map.entrySet().stream().filter(value -> value.getValue() > 1).forEach(System.out::println);
    }
}
