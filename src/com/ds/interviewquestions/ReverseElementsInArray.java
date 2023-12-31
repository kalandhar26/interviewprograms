package com.ds.interviewquestions;

public class ReverseElementsInArray {

    public static void main(String[] args) {

        int[] array = {1,4,3,2,5,6};

        int[] result = reverseElementsInArray2(array);


        for(int x : result)
            System.out.println(x);
    }

    public static int[] reverseElementsInArray1(int[] array){
        int n = array.length;

        for(int i =0;i<n;i++){
            if( i!=n){
                int temp = array[i];
                array[i]=array[n-1];
                array[n-1]= temp;
                n--;
            }
        }

        return array;
    }

    public static int[] reverseElementsInArray2(int[] array){
        int n = array.length;

        for(int i =0;i<n/2;i++){
                int temp = array[i];
                array[i]=array[n-i-1];
                array[n-i-1]= temp;
        }

        return array;
    }
}
