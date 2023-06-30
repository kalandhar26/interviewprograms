package com.ds.Arrays;

public class MinimumConsecutiveFlips {

    public static void main(String[] args) {

        int[] array ={0,0,1,1,0,0,1,1,0,1};
        msf(array);

    }


    public static void msf(int[] array){
       for(int i=1;i< array.length;i++){
           if(array[i] != array[i-1]){
               if(array[i] != array[0]){
                   System.out.print("From "+i+" to ");
               }else{
                   System.out.println(i-1+" ");
               }
           }
       }
       if(array[array.length-1] !=array[0]){
           System.out.println(array.length-1+" ");
       }
    }
}
