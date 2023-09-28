package com.java;

public class GetSmallestNumber {

    public static void main(String[] args) {
        int n = 1234;
        String str = String.valueOf(n);
        char[] digits = str.toCharArray();

        char smallestDigit = '9'; // Initialize to a higher value

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] > smallestDigit && digits[i] < '9') {
                smallestDigit = digits[i];
                break; // Exit the loop once the smallest digit is found
            }
        }

        StringBuilder smallestNumber = new StringBuilder();
        smallestNumber.append(smallestDigit);

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != smallestDigit) {
                smallestNumber.append(digits[i]);
            }
        }

        System.out.println(smallestNumber.toString());
    }
}
