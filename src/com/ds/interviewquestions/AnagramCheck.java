package com.ds.interviewquestions;

import java.util.Arrays;

public class AnagramCheck {

    public static void main(String[] args) {
        String input1 = "listten";
        String input2 ="silennt";
        boolean areAnagrams = areAnagrams(input2,input1);

        if(areAnagrams)
            System.out.println("The Strings are anagrams");
        else
            System.out.println("The Strings are not anagrams");
    }

    public static boolean areAnagrams(String str1, String str2){
        if(str1==null || str2==null||str1.length() != str2.length())
            return false;

        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();

        Arrays.sort(chars1);
        Arrays.sort(chars2);

        return Arrays.equals(chars1,chars2);


    }


}
