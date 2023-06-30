package com.ds.recursion;

public class Palindrome {

    public static void main(String[] args) {

        System.out.println(palindromerec("ABCDFEDCBA",0,9));

    }

    // Naive Solution
    private static boolean palindrome(String input) {

        String result = "";
        int length = input.length() - 1;

        for (int i = length; i >= 0; i--) {
            result = result + input.charAt(i);
        }
        System.out.println(result);

        if (input.equals(result))
            return true;
        else
            return false;
    }

    // Recursive Solution

    private static boolean palindromerec(String input, int start, int end) {

        if(start >= end)
            return true;

       return (input.charAt(start)==input.charAt(end)) && palindromerec(input, start+1,end-1);

    }
}
