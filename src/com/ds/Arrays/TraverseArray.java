package com.ds.Arrays;

public class TraverseArray {

    public static void main(String[] args) {
        int [] array = {1,4,5,6,3,2,6,7,8,9};

        int length = array.length;

        for(int i=0;i<length;i++){
            System.out.print(array[i]+" ");
        }

        for(int x : array)
            System.out.println(x);
    }
}
