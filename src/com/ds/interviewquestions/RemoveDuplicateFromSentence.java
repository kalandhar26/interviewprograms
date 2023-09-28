package com.ds.interviewquestions;

import java.util.HashSet;
import java.util.Set;

public class RemoveDuplicateFromSentence {

    public static void main(String[] args) {

        String s = "I am at amsterdam";
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        Set<Character> set = new HashSet<>();

        for (String x : words) {
            for (int i = 0; i < x.length(); i++) {
                Character current = x.charAt(i);
                if (set.add(current))
                    sb.append(current);
            }
            sb.append(" ");

        }
        System.out.println(sb);
    }

}
