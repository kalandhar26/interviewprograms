package com.ds.Arrays;

public class SubarraySumLessThanK {

    public static void main(String[] args) {
        int[] array = {1,2,3,4};
        int k =4;
        System.out.println(NoOfSubArraysSumlessThanK(array,k));

    }

    public static int NoOfSubArraysSumlessThanK(int[] array, int k){

        int count=0;
        int addition=0;
        int left=0;

        for(int right=0;right<array.length;right++){
            addition = addition+array[right];

            while(addition >=k){
                addition = addition-array[left];
                left++;
            }

            count = count+right-left+1;
        }
        return count;
    }
}
