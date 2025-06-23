package com.ds.Arrays;

import java.util.Arrays;

public class AlternateSmallBig {

    public static void main(String[] args) {
        int[] array = {2,3,4,5,1,6,7,9};
   //     int[] output = maxVankay(array);
        int[] array1 = {1,2,1,4,1,2,1,2,4,2};
        System.out.println(printAlternateMinMax(array1));
    }
    public static int[] maxVankay(int[] array){
        int n = array.length-1;
        int[] result = new int[n];
        Arrays.sort(array);

        int j=0;
        for(int i=0;i<n/2;i=i++){
            result[j]= array[i];
            j=j+2;
        }

        for(int i =n-1;i>0;i=i-2){
            result[i]= array[i];
        }

        return result;
    }


    public static int printAlternateMinMax(int[] array){
        int n =array.length;
        int output=0;
        for(int i=0;i<n;i++){
            int[] result = new int[3];
            int count=0;
            for(int j=i;j<i+3 && j<n ;j++){
                result[count]=array[j];
                count++;
            }

            if(count==3 && result[0]+result[2]==result[1]){
                output++;
            }
        }

        return output;
    }


}
