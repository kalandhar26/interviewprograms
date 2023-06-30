package com.ds.interviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class findElementStartingwithOne {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1,10,20,11,14,34,41,31,13);
        findElementsStartingwithOnee(list);

    }

    public static void findElementsStartingwithOnee(List<Integer> integerList){
        integerList.stream().map(s -> s+"").filter( s -> s.startsWith("1")).forEach(System.out::println);
    }
}
