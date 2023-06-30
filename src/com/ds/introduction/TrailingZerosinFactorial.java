package com.ds.introduction;

public class TrailingZerosinFactorial {

    public static void main(String[] args) {
        System.out.println(TrailingZeros1(100));
        System.out.println(TrailingZeros(100));
    }

    // Normal way
    private static int TrailingZeros(int n){
        int result=1, count=0;
        while(n>0){
            result = result *n;
            n--;
        }
        while(result%10==0){
            count++;
            result = result/10;
        }
        return  count;
    }

    // efficient way
    private static int TrailingZeros1(int n){
        int result=0;

        for(int i=5;i<=n;i=i*5)
            result = result + n/i;
        return  result;
    }
}
