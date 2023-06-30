package com.ds.introduction;

public class PalindromeNumber {

    public static void main(String[] args) {
        System.out.println(isPalindrome(112));
    }

    private static int isPalindrome( int n){
        int result= 0, temp=n;
        while(temp>0){
            result = result*10 + (temp%10);
            temp = temp/10;
        }

        if(result==n){
            System.out.println(n+" PALINDROME "+result);
        }else {
            System.out.println(n+" NOT PALINDROME "+result);
        }
        return result;
    }
}
