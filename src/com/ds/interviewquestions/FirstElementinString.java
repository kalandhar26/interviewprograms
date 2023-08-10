package com.ds.interviewquestions;

public class FirstElementinString {

    public static void main(String[] args) {

        String input = "Abdf Sghj Tryu Astq";

        System.out.println(transformString(input));

           }

        public static String transformString(String input) {
        String[] words = input.split(" ");
        int wordLength = words[0].length();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < wordLength; i++) {
            for (String word : words) {
                result.append(word.charAt(i));
            }
            result.append(" ");
        }

        return result.toString();
    }
}


