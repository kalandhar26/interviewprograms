package com.ds.interviewquestions;

public class ReverseVowels {

    public static void main(String[] args) {

        String input = "IceCream";

        System.out.println(convertCharArray(input));

    }

    public static String convertCharArray(String input) {
        char[] array = input.toCharArray();
        String vowels = "aeiouAEIOU";
        int start = 0;
        int end = input.length() - 1;

        while (start < end) {
            while (start < end && vowels.indexOf(array[start]) == -1)
                start++;
            while (start < end && vowels.indexOf(array[end]) == -1)
                end--;

            char temp = array[start];
            array[start] = array[end];
            array[end] = temp;

            start++;
            end--;
        }

        return new String(array);
    }
}
