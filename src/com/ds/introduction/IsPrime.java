package com.ds.introduction;

public class IsPrime {
    public static void main(String[] args) {

        System.out.println(isPrime2(101));

    }

    // Naive Solution
    private static Boolean isPrime(int n) {
        if (n == 1)
            return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Efficient Solution
    private static Boolean isPrime1(int n) {
        if (n == 1)
            return false;
        for (int i = 2; i*i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Most Efficient
    private static Boolean isPrime2(int n) {
        if (n == 1) return false;
        if(n==2 || n==3) return  true;
        if(n%2==0 || n%3==0) return  false;

        for (int i = 5; i*i < n; i=i+6) {
            if (n % i == 0 || n%(i+2)==0) {
                return false;
            }
        }
        return true;
    }

}
