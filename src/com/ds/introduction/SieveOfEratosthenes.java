package com.ds.introduction;

public class SieveOfEratosthenes {

    public static void main(String[] args) {
        primeNumbers(23);
    }

    private static void primeNumbers(int n){
        for(int i=2;i<=n;i++){
            if(isPrime(i)){
                System.out.println(i);
            }
        }
    }

    private static boolean isPrime(int n){
        int count=0;
        for(int i=2;i<n;i++){
            if(n%i==0)
                count++;
        }
        if(count>0)
            return false;
        else
            return true;
    }
}


