package com.ds.recursion;

public class PrintNumbers {

    public static void main(String[] args) {

        printNumbersAsc(6);

    }

    private static int printNumbersDesc(int n) {

        if (n == 1) {
            System.out.println(n);
            return n;
        }
        System.out.println(n);
        return printNumbersDesc(n - 1);
    }

    private static int printNumbersAsc(int n) {

        if (n == 0) {
            return 0;
        }
        printNumbersAsc(n - 1);
        System.out.println(n);
        return n;
    }
}
