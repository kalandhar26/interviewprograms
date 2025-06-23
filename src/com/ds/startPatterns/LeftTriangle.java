package com.ds.startPatterns;

public class LeftTriangle {
    public static void main(String[] args) {
        printLeftTriangle(5);
        printRightTriangle(5);
    }

    public static void printLeftTriangle(int n){
        for(int i=0;i<n;i++){
            for(int j=0;j<=i;j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    public static void printRightTriangle(int n){
        for(int i=n;i>0;i--){
            for(int j=i;j>0;j--){
                System.out.print("* ");
            }
            System.out.println(" ");
        }
    }
}
