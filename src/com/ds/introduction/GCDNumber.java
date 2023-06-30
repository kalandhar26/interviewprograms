package com.ds.introduction;

public class GCDNumber {
    public static void main(String[] args) {
        System.out.println(GcdofNumber1(9, 27));

    }

    private static int GcdofNumber(int a, int b) {
        int result = 0;
        if (a > b) {
            for (int i = 1; i <= b; i++) {
                if (a % i == 0 && b % i == 0) {
                    result = i;
                }
            }
        } else {
            for (int i = 1; i <= a; i++) {
                if (a % i == 0 && b % i == 0) {
                    result = i;
                }
            }
        }

        return result;
    }

    private static int GcdofNumber1(int a, int b) {

        int result = 0, min = Math.min(a, b);

        for (int i = 2; i <= min; i++) {
            if (a % i == 0 && b % i == 0) {
                result = i;
            }
        }

        return result;
    }
}
