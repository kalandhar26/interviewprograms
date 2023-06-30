package com.ds.recursion;

public class FactorialOfaNumber {

    public static void main(String[] args) {
        // Initialize k=1 always
        System.out.println(factNumber(4));
    }

    private static int factNumber(int n){
        if(n==1 || n==0)
            return 1;
        return n * factNumber(n-1);
    }
}
