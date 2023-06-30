package com.ds.introduction;

public class LCMofNumber {

    public static void main(String[] args) {

        System.out.println(efficientLCM(2, 8));
    }

    private static int lcDOfNumber(int a, int b) {
        int min = Math.min(a, b), result = 0;

        for (int i = 2; i <= min; i++) {
            if (a % i == 0 && b % i == 0) {
                result = i;
                break;
            }
        }

        return result;
    }

    // Naive Solution
    private static int lcMOfNumber(int a, int b) {
        int min = Math.max(a, b), result = 0;

        for (int i = 1; i <= min; i++) {
            int x = min * i;

            if (x % a == 0 && x % b == 0) {
                result = x;
                break;
            }

        }

        return result;
    }

    // Efficient Solution a*b = GCD(a,b) * LCM(a,b)

    private static int efficientLCM(int a, int b) {
        return (a*b)/GCD(a,b);
    }

    private static int GCD(int a, int b) {
        if (b == 0)
            return a;
        return GCD(b, a % b);
    }
}
