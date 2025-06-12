package com.ds.interviewquestions;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class RomanToInt {

    public static void main(String[] args) {
        String roman = "XI"; // Example input
        int integerValue = romanToInt(roman);
        System.out.println("The integer value of " + roman + " is: " + integerValue);

    }

    public static int romanToInt(String s) {
        int result = 0;

        // Create the map to store Roman numeral values
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        // Iterate through the string
        for (int i = 0; i < s.length(); i++) {
            // Check if the next character exists and if the current value is less than the next value
            if (i < s.length() - 1 && romanMap.get(s.charAt(i)) < romanMap.get(s.charAt(i + 1))) {
                result -= romanMap.get(s.charAt(i)); // Subtract if the current value is less
            } else {
                result += romanMap.get(s.charAt(i)); // Otherwise, add the value
            }
        }

        return result;
    }
    }
