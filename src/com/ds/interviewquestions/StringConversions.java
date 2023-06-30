package com.ds.interviewquestions;

import java.util.HashMap;
import java.util.Map;

public class StringConversions {

    public static void main(String[] args) {
        convertStringNumericToCharacters("aaabbbcdd");
        System.out.println();
        convertStringoCharactersToNumeric("a2b4c6d8");
    }

    public static void convertStringNumericToCharacters(String input) {

        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < input.length(); i++) {
            Character ch = input.charAt(i);
            if (map.containsKey(ch) && Character.isAlphabetic(ch)) {
                int value = map.get(ch);
                map.put(ch, value + 1);
            } else {
                map.put(ch, 1);
            }
        }

        map.forEach((k, v) -> {
            System.out.print(k + "" + v);
        });
    }

    public static void convertStringoCharactersToNumeric(String input) {

        for(int i =0;i<input.length();i++){
            if(Character.isDigit(input.charAt(i))){
                int value = Character.getNumericValue(input.charAt(i));
                for(int j=1;j<value;j++)
                    System.out.print(input.charAt(i-1));
            }else{
                System.out.print(input.charAt(i));
            }
        }

    }
}
