package com.ds.introduction;

public class DivisorsofNumber {

    public static void main(String[] args) {
        divisorsofNumber(7);
        divisorsofNumbers(15);
        divisorsofNumbers1(15);
    }

    // Naive Solution
    private static void divisorsofNumber(int n){
        for(int i=1;i<=n;i++){
            if(n%i==0){
                System.out.println(i);
            }
        }
    }

    // Effiecient Solution without sort order

    private static void divisorsofNumbers(int n){
        for(int i=1;i*i<=n;i++){
            if(n%i==0){
                System.out.println(i);
                if( i != n/i)
                    System.out.println(n/i);
            }
        }
    }

    // Effiecient Solution without sort order

    private static void divisorsofNumbers1(int n){
        int i;
        for(i=1;i*i<=n;i++){
            if(n%i==0)
                System.out.println(i);
        }

        for(;i>=1;i--){
            if(n%i==0)
                System.out.println(n/i);
        }
    }

}
