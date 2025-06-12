package com.ds.patterns;

public class HalfPyramid {

    public static void main(String[] args) {
        int n = 5;

        for(int i=n;i>0;i--){
            for(int j=0;j<i;j++){
                System.out.print("* ");
            }
            System.out.println();
        }

        for(int i=0;i<n;i++){
            for(int j=n;j<i;j++){
                System.out.print(" ");
            }
            System.out.println("* ");
        }
    }
}
