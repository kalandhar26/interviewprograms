package com.ds.introduction;

public class SumOf_N_numbers {

    public static void main(String[] args) {

        System.out.println(sumOfNnumbers1(10));
        System.out.println(sumOfNnumbers2(10));
        System.out.println(sumOfNnumbers3(10));

    }

    static int sumOfNnumbers1(int n) {
        return n * (n + 1) / 2;

        /*
        In the above expression we are doing
        1. Multiplication
        2. Addition
        3. Division
        Any Operation will take constant time irrespective of input values. Hence
        Time Taken : C1
         */
    }

    static int sumOfNnumbers2(int n) {
        int sum = 0; // Constant
        for (int i = 1; i <= n; i++) {
            sum = sum + i; // This line executes n times
        }
        return sum; // Constant
        /*
        Time taken : C2 n + C3
         */
    }

    static int sumOfNnumbers3(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            // below loop executes n times
            for (int j = 1; j <= i; j++) {
                sum++; // It executes n^2 times
            }
        }
        return sum;
        /*
        Time Taken: C4 n^2 + C5 n + c6
         */
    }

}
