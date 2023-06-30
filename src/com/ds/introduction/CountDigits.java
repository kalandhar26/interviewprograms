package com.ds.introduction;

public class CountDigits {

    public static void main(String[] args) {

      System.out.println(countDigits(2348));
    }

    private static int countDigits(int n){
        int result=0;
        while(n>0){
            n= n/10;
            result++;
        }
        return result;
    }
}
