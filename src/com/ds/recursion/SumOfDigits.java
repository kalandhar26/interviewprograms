package com.ds.recursion;

public class SumOfDigits {

    public static void main(String[] args) {

        System.out.println(sumOfDigits1(253));

    }

    // Naive Solution
    private static int sumOfDigits(int n) {
        int result = 0;

        while (n > 0) {
            result = result + n % 10;
            n = n / 10;
        }
        return result;
    }

    // Recursion
    private static int sumOfDigits1(int n) {
        if (n == 0)  // (n <= 9) return n
            return 0;
        else
            return sumOfDigits1(n / 10) + n % 10;
    }

}
