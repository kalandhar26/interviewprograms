package com.ds.patterns;

public class HallowSquareorRectangle {
    public static void main(String[] args) {
        int rows = 5, columns=4;
        printShape(rows,columns);
    }
    
    public static void printShape(int rows,int columns){
        for(int i=1;i<=rows;i++){
            for(int j=1;j<=columns;j++){
                if(i==1 || rows==i || columns==j || j==1){
                    System.out.print("*");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }
}
