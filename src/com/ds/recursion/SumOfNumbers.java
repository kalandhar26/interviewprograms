package com.ds.recursion;

public class SumOfNumbers {

    public static void main(String[] args) {
        System.out.println(sumOfNaturalNumbers(5));
    }

    private static int sumOfNaturalNumbers(int n){
        if(n==1)
            return 1;
        return n+sumOfNaturalNumbers(n-1);
    }
}
