package com.ds.Arrays;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FindDuplicates {

    // Given an Array ["Baba","Saddu","Bava"] // a is common all 3 words

    public static void main(String[] args) {

        String[] array = {"Baba", "Java", "Lava", "iaft", "ooa"};

        Set<Character> uniqueCharacters = new HashSet<>();
        Set<Character> duplicateCharacters = new HashSet<>();
        Set<Character> seenCharacters = new HashSet<>();

        for (String str : array) {
            for (char ch : str.toLowerCase().toCharArray()) {
                if (!seenCharacters.add(ch)) {
                    duplicateCharacters.add(ch);
                } else {
                    uniqueCharacters.add(ch);
                }
            }
        }

        uniqueCharacters.removeAll(duplicateCharacters);

        System.out.println("Duplicate characters: " + duplicateCharacters);
        System.out.println("Unique characters: " + uniqueCharacters);

    }
}
