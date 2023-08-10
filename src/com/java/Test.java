package com.java;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        String input = "babakalandhar"; // baklndhr

        String collect = input.chars().mapToObj(c -> (char) c).distinct().map(String::valueOf).collect(Collectors.joining());

        System.out.println(collect);
        Map<Character,Integer> map = new LinkedHashMap<>();
        int value=0;
        for(int i=0;i<input.length();i++){
            if(map.containsKey(input.charAt(i))){
               continue;
            }else {
                map.put(input.charAt(i),value);
                value++;
            }
        }

        map.forEach((k,v)-> System.out.print(k));
    }
}
