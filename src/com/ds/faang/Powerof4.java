package com.ds.faang;

public class Powerof4 {

    /*
    Given an integer n, return true if it is a power of four otherwise return false.
     */
    public static void main(String[] args) {
        System.out.println(powerof4(64));
        System.out.println(poweroof4(64));
    }

    public static boolean powerof4(int n) {
        if (n <= 0)
            return false;
        double numerator = Math.log(n);
        double denominator = Math.log(4);

        double result = numerator / denominator;

        double floor = Math.floor(result);

        if ( floor == result)
            return true;
        else
            return false;
    }

    public static int poweroof4(int n) {
        if (n <= 0)
            return -1;

        double result = Math.log(n) / Math.log(4);

        if (Math.floor(result) == result)
            return (int)result;
        else
            return -1;
    }
}
