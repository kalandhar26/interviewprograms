package com.ds.introduction;

public class FactorialOfaNumber {


    public static void main(String[] args) {
        System.out.println(factorialOfaNumber(6));
        System.out.println(factorialofaNumber1(6));
        System.out.println(recursiveFactorial(6));
    }

    // while loop
    private static int factorialOfaNumber(int n){

        int result=1;

        while(n>0){
            result = result *n;
            n--;
        }
        return result;
    }

    // for loop

    private static int factorialofaNumber1(int n){
        int result=1;

        for(int i=2;i<=n;i++){
            result = result*i;
        }
        return result;
    }

    // recursive function
    private static int recursiveFactorial(int n){
        if(n==0)
            return 1;
        return recursiveFactorial(n-1)*n;
    }
}
