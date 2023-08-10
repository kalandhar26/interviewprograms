package com.ds.interviewquestions;

public class NumberToWord {

    public static void main(String[] args) {

        System.out.println(digitConversion(9678));

    }


    public static String digitConversion(Integer number){
        String[] words = {
                "Zero", "One", "Two", "Three", "Four",
                "Five", "Six", "Seven", "Eight", "Nine"
        };

        String[] tensWords = {
                "", "", "Twenty", "Thirty", "Forty",
                "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
        };

        String[] teensWords = {
                "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen",
                "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        };

        if (number == 0) {
            return words[0];
        }

        String word="";

        if (number >= 100000) {
            word += words[number / 100000] + "Hundred";
            number %= 100000;

            if (number > 0) {
                word += "And";
            }
        }

        if (number >= 1000) {
            word += words[number / 1000] + "Thousand";
            number %= 1000;

            if (number > 0) {
                word += "";
            }
        }

        if (number >= 100) {
            word += words[number / 100] + "Hundred";
            number %= 100;

            if (number > 0) {
                word += "And";
            }
        }

        if (number >= 20) {
            word += tensWords[number / 10];
            number %= 10;
        } else if (number >= 10) {
            word += teensWords[number - 10];
            number = 0;
        }

        if (number > 0) {
            word += words[number];
        }

        return word;
    }
}
