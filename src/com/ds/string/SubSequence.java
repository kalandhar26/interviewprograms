package com.ds.string;

import java.util.Objects;

public class SubSequence {

    public static void main(String[] args) {

        System.out.println(isSubsequenceExists("abc","abcdefg"));

    }

    public static boolean isSubsequenceExists(String input1, String input2){
        if(input1.isEmpty()) return true;
        if(input2.isEmpty()) return false;
        int i=0;
        int j=0;
        while (i < input1.length() && j < input2.length()){
            if(input1.charAt(i) == input2.charAt(j)){
                i++;
            }
            j++;
        }

        return i == input1.length();
    }
}
