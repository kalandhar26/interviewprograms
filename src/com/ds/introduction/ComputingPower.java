package com.ds.introduction;

public class ComputingPower {

    public static void main(String[] args) {
        System.out.println(power1(3, 4));
    }

    // Naive Solution O(n)
    private static int power(int base, int power) {
        int result = 1;
        for (int i = 0; i < power; i++) {
            result = base * result;
        }
        return result;
    }

    // Efficient Solution

    private static  int power1(int base , int power){
        if(power==0)
            return 1;
        int temp = power1(base, power/2);
        temp = temp * temp;
        if(power%2==0)
            return temp;
        else
            return temp * base;
    }
}
