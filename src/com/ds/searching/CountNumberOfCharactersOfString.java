package com.ds.searching;

import java.util.HashMap;
import java.util.Map;

public class CountNumberOfCharactersOfString {

    public static void main(String[] args) {

        String input = "aabbccaabbcc";

        Map<Character, Integer> map = new HashMap<>();
        // String Combination a4b4c4
        input.chars().mapToObj(c -> (char) c).forEach(ch -> map.put(ch, map.getOrDefault(ch, 0) + 1));

        // String Combination a4b4c4
        int result = 0;
        for (int i = 0; i < input.length(); i++) {
            char key = input.charAt(i);
            if (map.containsKey(key)) {
                Integer integer = map.get(input.charAt(i));
                result = integer + 1;
                map.put(input.charAt(i), result);
            } else {
                result = 1;
                map.put(input.charAt(i), result);
            }
        }


        map.forEach((k, v) -> {
            System.out.print(k + String.valueOf(v));
        });

        String consecutiveByCount = getConsecutiveByCount(input);
        System.out.println(consecutiveByCount);

    }

    // String Combination a2b2c2a2b2c2
    public static String getConsecutiveByCount(String input) {

        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder compressed = new StringBuilder();
        char currentChar = input.charAt(0);
        int count = 1;

        for (int i = 1; i < input.length(); i++) {
            char nextChar = input.charAt(i);
            if (currentChar == nextChar) {
                count++;
            } else {
                compressed.append(currentChar).append(count);
                currentChar = nextChar;
                count = 1;
            }
        }

        // Append the last character and its count
        compressed.append(currentChar).append(count);

        return compressed.toString();
    }
}
