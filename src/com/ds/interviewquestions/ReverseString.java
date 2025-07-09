package com.ds.interviewquestions;

import java.util.stream.Collectors;

public class ReverseString {

    public static void main(String[] args) {

        String originalString = "HelloWorld";
        String result="";

        // Direct Streams
        System.out.println(
                originalString.chars().collect(
                StringBuilder::new,
                (sb,c)-> sb.insert(0,(char)c),
                StringBuilder::append)
                .toString());

        // Normal Java
        for(int i=0;i<originalString.length();i++){
            result = originalString.charAt(i)+result;
        }

        System.out.println(result);

        // Using Streams
        String result1 = originalString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    StringBuilder reversed = new StringBuilder();
                    for (int i = list.size() - 1; i >= 0; i--) {
                        reversed.append(list.get(i));
                    }
                    return reversed.toString();
                }));

        System.out.println(result1);

    }

}
