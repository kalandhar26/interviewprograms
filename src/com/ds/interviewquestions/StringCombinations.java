package com.ds.interviewquestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StringCombinations {

    public static void main(String[] args) {

        String input1 = "a2b3g5h4";

        for (int i = 0; i < input1.length(); i++) {

            if (!Character.isAlphabetic(input1.charAt(i))) {
                int x = Character.getNumericValue(input1.charAt(i));
                for (int j = 1; j < x; j++) {
                    System.out.print(input1.charAt(i - 1));
                }
            } else {
                System.out.print(input1.charAt(i));
            }
        }
        System.out.println();
        String input2 = "aabbbggggghhhh";
        Map<Character, Integer> map = new HashMap<>();
        Integer value = 0;
        for (int i = 0; i < input2.length(); i++) {
            if (map.containsKey(input2.charAt(i))) {
                value = value + 1;
                map.put(input2.charAt(i), value);
            } else {
                value = 1;
                map.put(input2.charAt(i), value);
            }
        }

        map.forEach((k, v) -> {
            System.out.print(k + "" + v);
        });

        String input3 = "India is my country I love my wife";

        String[] inputList = input3.split(" ");

        String output3 = Arrays.stream(inputList).reduce("", (word, result) -> result + " " + word).
        trim();

        System.out.println(output3);
        String result="";
        for(int i = inputList.length-1;i>=0;i--){
            result = result+" "+inputList[i];
        }

        System.out.println(result.trim());



    }
}
