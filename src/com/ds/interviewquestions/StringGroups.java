package com.ds.interviewquestions;

import java.util.*;
import java.util.stream.Collectors;

public class StringGroups {

    public static void main(String[] args) {
        String[] array = {"PAT", "TAP", "STUDENT", "STUDENTS", "LENS", "LAP"};
        printGroupedString(array);

    }

    public static void printGroupedString(String[] array) {
        Map<String, List<String>> groups = Arrays.stream(array).collect(
                Collectors.groupingBy(
                        word -> {

                            Set<Character> chars = new TreeSet<>();
                            for (char c : word.toCharArray()) {
                                chars.add(Character.toUpperCase(c));
                            }
                            return chars.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining());
                        }, Collectors.toList()));

        groups.values().stream().filter(group -> !group.isEmpty()).forEach(group -> {
            if (group.size() > 1) {
                System.out.println(String.join(" ", group));
            } else {
                System.out.println(group.getFirst());
            }
        });
    }
}
